package com.cyberpeace.cr.evaluation.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordResultRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoomIntPairRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoomPairRsp;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalRecordMapper;
import com.cyberpeace.cr.evaluation.service.*;
import com.cyberpeace.cr.evaluation.service.context.EvalRecordVariableContext;
import com.cyberpeace.cr.evaluation.service.context.EvalSystemIndexesContext;
import com.cyberpeace.integratation.model.evaluation.AlgorithmContext;
import com.cyberpeace.integratation.model.scene.CollectionEventPageContext;
import com.cyberpeace.integratation.service.RemotePythonService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EvalRecordServiceImpl extends ServiceImpl<EvalRecordMapper, EvalRecord> implements EvalRecordService {
    @Autowired
    private EvalIndexRefService evalIndexRefService;

    @Autowired
    private EvalVariableRefService evalVariableRefService;

    /**
     * 指标项（一、二、三级）
     */
    @Autowired
    private EvalIndexService evalIndexService;

    /**
     * 聚合项（四级）
     */
    @Autowired
    private EvalAggregationService evalAggregationService;

    /**
     * 变量项（五级）
     */
    @Autowired
    private EvalCollectionService evalCollectionService;

    /**
     * 三级指标关联关系
     */
    @Autowired
    private EvalIndicatorService evalIndicatorService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private EvalAlgorithmService evalAlgorithmService;

    @Autowired
    private EvalTaskService evalTaskService;

    @Autowired
    private RemoteSceneService remoteSceneService;

    @Autowired
    private EvalProbeDataService evalProbeDataService;

    @Autowired
    private EvalAlgorithmRefService evalAlgorithmRefService;

    @Autowired
    private EvalVariableRefProbeDataService evalVariableRefProbeDataService;

    @Autowired
    private RemotePythonService remotePythonService;

    /**
     * 获取实例对象, 检查返回值有效性
     *
     * @param resourceId 评估resourceId
     * @return {@link EvalRecord}
     */
    private EvalRecord getEntity(String resourceId) {
        EvalRecord evalRecord = this.getBaseMapper().selectById(resourceId);
        if (evalRecord == null) {
            throw new BusinessException();
        }
        if (!evalRecord.getStatus().equals(EvalRecord.Status.NOT_START.getCode())) {
            throw new BusinessException();
        }
        return evalRecord;
    }

    @Override
    public ListRsp<EvalRecord> pageList(Page page, String roomId, Integer status, String pattern) {
        return PageUtil.getListResult(this.getBaseMapper().pageList(page, roomId, status, pattern));
    }

    @Override
    public EvalRecordRsp get(String recordId) {
        EvalRecord evalRecord = getEntity(recordId);

        EvalRecordRsp recordRsp = BeanUtils.copy(evalRecord, EvalRecordRsp.class);
        recordRsp.setIndexes(Lists.newArrayList());
        List<EvalIndexRef> fullIndexes = evalIndexRefService.lambdaQuery().eq(EvalIndexRef::getRecordId, recordRsp.getResourceId()).list();
        fullIndexes.sort(Comparator.comparing(EvalIndexRef::getLevel));

        Map<String, EvalIndexRef> fullIndexesMap = fullIndexes.stream().collect(Collectors.toMap(EvalIndexRef::getResourceId, evalIndexRef -> evalIndexRef));

        Map<String, EvalVariableRef> fullVariablesMap = evalVariableRefService.lambdaQuery().eq(
                EvalVariableRef::getRecordId, recordRsp.getResourceId()
        ).list().stream().collect(Collectors.toMap(EvalVariableRef::getResourceId, evalVariableRef -> evalVariableRef));

        for (EvalIndexRef evalIndexRef : fullIndexes) {
            if (evalIndexRef.getLevel().equals(EvalIndex.Level.FIRST.getCode())) {
                recordRsp.getIndexes().add(generateNextIndexes(evalIndexRef, fullIndexesMap, fullVariablesMap));
            } else {
                break;
            }
        }
        return recordRsp;
    }

    @Override
    public EvalRecordRsp create(EvalRecordAddReq requestData) {
        // 评估对象构建
        EvalRecord evalRecord = BeanUtils.copy(requestData, EvalRecord.class);
        // 当evalRecord.resourceId不为空时, 视为更新
        boolean isUpdate = !StringUtils.isEmpty(evalRecord.getResourceId());
        if (!isUpdate) {
            evalRecord.setResourceId(IDGeneratorUtil.getID());
            evalRecord.setStatus(EvalRecord.Status.NOT_START.getCode());
        }

        // 获取"系统评分"指标项下的变量项节点、探针信息
        Map<String, EvalRecordVariableContext> evalRecordVariableContextMap = Maps.newHashMap();
        // 获取"三级指标"评分类型
        Map<String, Integer> thirdIndexesTypeMap = Maps.newHashMap();
        Map<String, Integer> thirdSpecialistMap = Maps.newHashMap();
        requestData.getIndexes().forEach(evalRecordIndexContext -> {
            thirdIndexesTypeMap.put(evalRecordIndexContext.getResourceId(), evalRecordIndexContext.getType());
            if (evalRecordIndexContext.getType().equals(EvalIndexRef.Type.SYSTEM.getCode())) {
                evalRecordIndexContext.getVariables().forEach(
                        evalRecordVariableContext -> evalRecordVariableContextMap.put(evalRecordVariableContext.getResourceId(), evalRecordVariableContext)
                );
            } else if (evalRecordIndexContext.getType().equals(EvalIndexRef.Type.SPECIALIST.getCode())) {
                thirdSpecialistMap.put(evalRecordIndexContext.getResourceId(), evalRecordIndexContext.getSpecialist().getValue());
            }
        });

        // 获取一、二、三级指标
        List<EvalIndex> evalIndexList = evalIndexService.lambdaQuery().eq(EvalIndex::getEvalSystemId, requestData.getSystemId()).list();
        // 获取三级指标关联关系字段
        Set<String> evalIndicatorIds = Sets.newHashSet();
        evalIndexList.forEach(item -> {
            if (item.getLevel().equals(EvalIndex.Level.THIRD.getCode())) {
                evalIndicatorIds.add(item.getEvalIndicatorId());
            }
        });
        // 四级指标
        List<EvalAggregation> evalAggregations = evalAggregationService.lambdaQuery().in(EvalAggregation::getEvalIndicatorId, evalIndicatorIds).list();
        // 五级指标
        List<EvalCollection> evalCollections = evalCollectionService.lambdaQuery().in(
                EvalCollection::getResourceId, evalRecordVariableContextMap.keySet()
        ).list();

        // todo 暂时取消
//        if (evalCollections.size() != evalRecordVariableContextMap.keySet().size()) {
//            throw new BusinessException();
//        }

        // 使用指标体系内的全部算法
        Set<String> usingAlgorithmIds = Sets.newHashSet();
        evalCollections.forEach(evalCollection -> {
            usingAlgorithmIds.add(evalCollection.getFormatAlgorithmId());
            usingAlgorithmIds.add(evalCollection.getDetectionAlgorithmId());
            usingAlgorithmIds.add(evalCollection.getScoreAlgorithmId());
        });
        usingAlgorithmIds.addAll(evalAggregations.stream().map(EvalAggregation::getAggregatingAlgorithmId).collect(Collectors.toList()));
        usingAlgorithmIds.addAll(evalIndexList.stream().map(EvalIndex::getAggregatingAlgorithmId).collect(Collectors.toList()));
        Map<String, EvalAlgorithm> evalAlgorithmMap = evalAlgorithmService.lambdaQuery().in(
                EvalAlgorithm::getResourceId, usingAlgorithmIds
        ).list().stream().collect(Collectors.toMap(EvalAlgorithm::getResourceId, evalAlgorithm -> evalAlgorithm));

        List<EvalAlgorithmRef> evalAlgorithmRefList = Lists.newArrayList();
        // 生成变量项数据（五级）
        List<EvalVariableRef> variableRefs = Lists.newArrayList();
        Map<String, String> variableIdsMap = Maps.newHashMap();
        evalCollections.forEach(evalCollection -> {
            EvalVariableRef evalVariableRef = new EvalVariableRef();
            evalVariableRef.setResourceId(IDGeneratorUtil.getID());
            evalVariableRef.setRecordId(evalRecord.getResourceId());
            evalVariableRef.setName(evalCollection.getName());
            evalVariableRef.setProgress(EvalVariableRef.Progress.NOT_START.getCode());

            // 构建变量项新的算法数据
            EvalAlgorithmRef newFormatAlgorithm = generateAlgorithmRef(evalCollection.getFormatAlgorithmId(), evalAlgorithmMap);
            evalVariableRef.setFormatAlgorithmId(newFormatAlgorithm.getResourceId());
            evalVariableRef.setFormatParams(evalCollection.getFormatAlgorithmParams());
            evalAlgorithmRefList.add(newFormatAlgorithm);

            EvalAlgorithmRef newMatchAlgorithm = generateAlgorithmRef(evalCollection.getDetectionAlgorithmId(), evalAlgorithmMap);
            evalVariableRef.setMatchAlgorithmId(newMatchAlgorithm.getResourceId());
            evalVariableRef.setMatchParams(evalCollection.getDetectionAlgorithmParams());
            evalAlgorithmRefList.add(newMatchAlgorithm);

            EvalAlgorithmRef newMarkAlgorithm = generateAlgorithmRef(evalCollection.getScoreAlgorithmId(), evalAlgorithmMap);
            evalVariableRef.setMarkAlgorithmId(newMarkAlgorithm.getResourceId());
            evalVariableRef.setMarkParams(evalCollection.getScoreAlgorithmParams());
            evalAlgorithmRefList.add(newMarkAlgorithm);


            EvalRecordVariableContext evalRecordVariableContext = evalRecordVariableContextMap.get(evalCollection.getResourceId());
            evalVariableRef.setProbeId(evalRecordVariableContext.getProbeId());
            evalVariableRef.setDeviceId(evalRecordVariableContext.getDeviceId());

            variableRefs.add(evalVariableRef);
            variableIdsMap.put(evalCollection.getResourceId(), evalVariableRef.getResourceId());
        });

        Map<String, EvalIndicator> thirdRelationMap = evalIndicatorService.lambdaQuery().in(
                EvalIndicator::getResourceId, evalIndicatorIds
        ).list().stream().collect(Collectors.toMap(EvalIndicator::getResourceId, evalIndicator -> evalIndicator));

        // 生成聚合项数据（四级）
        List<EvalIndexRef> indexRefs = Lists.newArrayList();
        Map<String, String> evalIndexIdsMap = Maps.newHashMap();
        // todo 实际查询到变量项数量校验
        evalAggregations.forEach(evalAggregation -> {
            EvalIndexRef evalIndexRef = new EvalIndexRef();
            evalIndexRef.setResourceId(IDGeneratorUtil.getID());
            evalIndexRef.setRecordId(evalRecord.getResourceId());
            evalIndexRef.setName(evalAggregation.getName());
            evalIndexRef.setType(EvalIndexRef.Type.SYSTEM.getCode());
            evalIndexRef.setLevel(EvalIndexRef.Level.AGGREGATION.getCode());

            evalIndexRef.setNextIds(JsonUtil.toJsonString(JsonUtil.toJsonObjectArray(
                    evalAggregation.getCollectionIds(), String.class
            ).stream().map(variableIdsMap::get).collect(Collectors.toList())));

            EvalAlgorithmRef evalAlgorithmRef = generateAlgorithmRef(evalAggregation.getAggregatingAlgorithmId(), evalAlgorithmMap);
            evalIndexRef.setAlgorithmParams(evalAggregation.getAggregatingAlgorithmParams());
            evalIndexRef.setAlgorithmId(evalAlgorithmRef.getResourceId());
            evalAlgorithmRefList.add(evalAlgorithmRef);

            indexRefs.add(evalIndexRef);
            evalIndexIdsMap.put(evalAggregation.getResourceId(), evalIndexRef.getResourceId());
        });

        // 前三级指标按照"level"降序排序
        evalIndexList = evalIndexList.stream().sorted(Comparator.comparing(EvalIndex::getLevel).reversed()).collect(Collectors.toList());

        // 生成一、二、三级指标
        evalIndexList.forEach(evalIndex -> {
            EvalIndexRef evalIndexRef = new EvalIndexRef();
            evalIndexRef.setResourceId(IDGeneratorUtil.getID());
            evalIndexRef.setRecordId(evalRecord.getResourceId());
            evalIndexRef.setName(evalIndex.getName());
            evalIndexRef.setType(thirdIndexesTypeMap.getOrDefault(evalIndex.getResourceId(), EvalIndexRef.Type.SYSTEM.getCode()));
            evalIndexRef.setLevel(evalIndex.getLevel());

//            EvalAlgorithmRef evalAlgorithmRef = generateAlgorithmRef(evalIndex.getAggregatingAlgorithmId(), evalAlgorithmMap);

            String nextIds;
            EvalAlgorithmRef evalAlgorithmRef;
            if (!evalIndex.getLevel().equals(EvalIndex.Level.THIRD.getCode())) {
                evalIndexRef.setAlgorithmParams(evalIndex.getAggregatingAlgorithmParams());

                nextIds = evalIndex.getChildrenIds();
                evalAlgorithmRef = generateAlgorithmRef(evalIndex.getAggregatingAlgorithmId(), evalAlgorithmMap);
            } else {
                EvalIndicator evalIndicator = thirdRelationMap.get(evalIndex.getEvalIndicatorId());
                // todo 暂时取消
//                if (evalIndicator == null) {
//                    throw new BusinessException();
//                }
                nextIds = evalIndicator.getAggregationIds();
                evalAlgorithmRef = generateAlgorithmRef(evalIndicator.getAggregatingAlgorithmId(), evalAlgorithmMap);

                // 专家评分, 设置默认分值
                evalIndexRef.setScore(evalIndexRefService.thirdSpecialist(evalIndicator, thirdSpecialistMap.get(evalIndex.getResourceId())));
            }

            evalIndexRef.setAlgorithmId(evalAlgorithmRef.getResourceId());
            evalAlgorithmRefList.add(evalAlgorithmRef);

            evalIndexRef.setNextIds(JsonUtil.toJsonString(JsonUtil.toJsonObjectArray(
                    nextIds, String.class
            ).stream().map(evalIndexIdsMap::get).collect(Collectors.toList())));

            indexRefs.add(evalIndexRef);
            evalIndexIdsMap.put(evalIndex.getResourceId(), evalIndexRef.getResourceId());
        });

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                if (isUpdate) {
                    updateById(evalRecord);
                    deleteRelation(Collections.singletonList(evalRecord.getResourceId()));
                } else {
                    save(evalRecord);
                }

                if (!indexRefs.isEmpty()) {
                    evalIndexRefService.saveBatch(indexRefs);
                }
                if (!variableRefs.isEmpty()) {
                    evalVariableRefService.saveBatch(variableRefs);
                }
                if (!evalAlgorithmRefList.isEmpty()) {
                    evalAlgorithmRefService.saveBatch(evalAlgorithmRefList);
                }
            }
        });

        return get(evalRecord.getResourceId());
    }

    private EvalAlgorithmRef generateAlgorithmRef(String algorithmId, Map<String, EvalAlgorithm> originAlgorithmMap) {
        EvalAlgorithm originAlgorithm = originAlgorithmMap.get(algorithmId);
        EvalAlgorithmRef newAlgorithm = new EvalAlgorithmRef();
        org.springframework.beans.BeanUtils.copyProperties(originAlgorithm, newAlgorithm, "id");
        newAlgorithm.setResourceId(IDGeneratorUtil.getID());
        return newAlgorithm;
    }

    @Override
    public EvalRecordRsp update(EvalRecordAddReq requestData) {
        EvalRecord originEvalRecord = getEntity(requestData.getResourceId());

        EvalRecord newEvalRecord = BeanUtils.copy(originEvalRecord, EvalRecord.class);
        BeanUtils.copyProperties(requestData, newEvalRecord);

        // 指标体系ID被更新情况, 调用this.create, 由create方法进行指标体系重建
        if (!originEvalRecord.getSystemId().equals(newEvalRecord.getSystemId())) {
            return create(requestData);
        }

        List<EvalVariableRef> evalVariableRefs = Lists.newArrayList();
        requestData.getIndexes().forEach(evalRecordIndexContext -> evalRecordIndexContext.getVariables().forEach(evalRecordVariableContext -> {
            EvalVariableRef evalVariableRef = new EvalVariableRef();
            BeanUtils.copyProperties(evalRecordVariableContext, evalVariableRef);
            evalVariableRef.setResourceId(evalRecordVariableContext.getResourceId());
            evalVariableRef.setDeviceId(evalRecordVariableContext.getDeviceId());
            evalVariableRef.setProbeId(evalRecordVariableContext.getProbeId());
            evalVariableRefs.add(evalVariableRef);
        }));

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                evalVariableRefService.updateBatchById(evalVariableRefs);
                updateById(newEvalRecord);
            }
        });

        return get(newEvalRecord.getResourceId());
    }

    private EvalSystemIndexesContext generateNextIndexes(EvalIndexRef evalIndexRef,
                                                         Map<String, EvalIndexRef> fullIndexesMap,
                                                         Map<String, EvalVariableRef> fullVariablesMap,
                                                         Boolean isFull) {
        EvalSystemIndexesContext evalSystemIndexesContext = BeanUtils.copy(evalIndexRef, EvalSystemIndexesContext.class);
        evalSystemIndexesContext.setChildren(Lists.newArrayList());
        List<String> nextIds = JsonUtil.toJsonObjectArray(evalIndexRef.getNextIds(), String.class);

        // 同三级指标项下变量项过滤
        Set<String> variablesMap = Sets.newHashSet();
        nextIds.forEach(nextId -> {
            EvalIndexRef evalIndexRef1 = fullIndexesMap.get(nextId);
            // 如果"ifFull"为真，跳过第四级直接由"EvalVariableRef"获取第五级
            Integer condition = isFull ? EvalIndexRef.Level.AGGREGATION.getCode() : EvalIndex.Level.THIRD.getCode();
            if (evalIndexRef.getLevel().equals(condition)) {
                List<String> variableIds;
                if (isFull) {
                    variableIds = JsonUtil.toJsonObjectArray(evalIndexRef.getNextIds(), String.class);
                } else {
                    variableIds = JsonUtil.toJsonObjectArray(evalIndexRef1.getNextIds(), String.class);
                }
                variableIds.forEach(variableId -> {
                    if (variablesMap.contains(variableId)) {
                        return;
                    }
                    EvalVariableRef evalVariableRef = fullVariablesMap.get(variableId);
                    evalSystemIndexesContext.getChildren().add(BeanUtils.copy(evalVariableRef, EvalSystemIndexesContext.class));
                    variablesMap.add(variableId);
                });
            } else {
                evalSystemIndexesContext.getChildren().add(generateNextIndexes(evalIndexRef1, fullIndexesMap, fullVariablesMap, isFull));
            }
        });

        return evalSystemIndexesContext;
    }

    private EvalSystemIndexesContext generateNextIndexes(EvalIndexRef evalIndexRef,
                                                         Map<String, EvalIndexRef> fullIndexesMap,
                                                         Map<String, EvalVariableRef> fullVariablesMap) {

        return generateNextIndexes(evalIndexRef, fullIndexesMap, fullVariablesMap, false);
    }

    @Override
    public Boolean delete(List<String> recordIds) {
        return transactionTemplate.execute(status -> {
            boolean isDeleted = removeByIds(recordIds);
            if (isDeleted) {
                deleteRelation(recordIds);
            }
            return isDeleted;
        });
    }

    private void deleteRelation(List<String> recordIds) {
        evalIndexRefService.removeByIds(evalIndexRefService.lambdaQuery().in(
                EvalIndexRef::getRecordId, recordIds
        ).list().stream().map(EvalIndexRef::getResourceId).collect(Collectors.toList()));

        evalVariableRefService.removeByIds(evalVariableRefService.lambdaQuery().in(
                EvalVariableRef::getRecordId, recordIds
        ).list().stream().map(EvalVariableRef::getResourceId).collect(Collectors.toList()));
    }

    @Override
    public Boolean startRecord(String recordId) {
        // 评估任务对象
        EvalRecord evalRecord = getEntity(recordId);
        // 评估实例对象
        EvalTask evalTask = evalTaskService.getTask(evalRecord.getTaskId());
        if (evalTask == null) {
            throw new BusinessException();
        }

        recordRun(evalRecord, evalTask);

        evalRecord.setStatus(EvalRecord.Status.RUNNING.getCode());
        return updateById(evalRecord);
    }

    @Async
    public void recordRun(EvalRecord evalRecord, EvalTask evalTask) {
        // todo 确保可以重复运行（进行中评估任务）, 遗弃`progress_key`, 已运行完成（指标/变量）项过滤

        // 循环同步采集数据
        syncProbeData(evalRecord, evalTask);
        // 执行变量项
        startVariables(evalRecord, evalTask);
        // 执行指标项
        startIndexes(evalRecord, evalTask);

        evalRecord.setStatus(EvalRecord.Status.END.getCode());
        this.getBaseMapper().updateById(evalRecord);
    }

    private void startVariables(EvalRecord evalRecord, EvalTask evalTask) {

        List<EvalVariableRef> evalVariableRefList = evalVariableRefService.lambdaQuery()
                .eq(EvalVariableRef::getRecordId, evalRecord.getResourceId())
                .ne(EvalVariableRef::getProgress, EvalVariableRef.Progress.NOT_START.getCode())
                .ne(EvalVariableRef::getProgress, EvalVariableRef.Progress.END.getCode())
                .list();

        List<String> usingAlgorithmIds = Lists.newArrayList();
        evalVariableRefList.forEach(evalVariableRef -> {
            usingAlgorithmIds.add(evalVariableRef.getFormatAlgorithmId());
            usingAlgorithmIds.add(evalVariableRef.getMatchAlgorithmId());
            usingAlgorithmIds.add(evalVariableRef.getMarkAlgorithmId());
        });
        Map<String, EvalAlgorithmRef> evalAlgorithmRefMap = evalAlgorithmRefService.lambdaQuery()
                .in(EvalAlgorithmRef::getResourceId, usingAlgorithmIds).list()
                .stream().collect(Collectors.toMap(EvalAlgorithmRef::getResourceId, evalAlgorithmRef -> evalAlgorithmRef));

        evalVariableRefList.forEach(evalVariableRef -> {

            // 获取变量项使用格式化算法
            EvalAlgorithmRef formatAlgorithmRef = evalAlgorithmRefMap.get(evalVariableRef.getFormatAlgorithmId());
            evalVariableRefService.startFormat(evalVariableRef, formatAlgorithmRef, evalRecord, evalTask);

            // 获取变量项模型检测算法（匹配）算法
            EvalAlgorithmRef matchAlgorithmRef = evalAlgorithmRefMap.get(evalVariableRef.getMatchAlgorithmId());
            evalVariableRefService.startMatch(evalVariableRef, matchAlgorithmRef, evalRecord, evalTask);

            // 获取变量项评分算法
            EvalAlgorithmRef markAlgorithmRef = evalAlgorithmRefMap.get(evalVariableRef.getMarkAlgorithmId());
            evalVariableRefService.startMark(evalVariableRef, markAlgorithmRef, evalRecord, evalTask);

        });

    }

    private void startIndexes(EvalRecord evalRecord, EvalTask evalTask) {

        // 体系下指标项
        List<EvalIndexRef> evalIndexRefs = evalIndexRefService.lambdaQuery().eq(EvalIndexRef::getRecordId, evalRecord.getResourceId()).list();
        evalIndexRefs.sort(Comparator.comparing(EvalIndexRef::getLevel).reversed());
        Map<String, EvalIndexRef> evalIndexRefMap = evalIndexRefs.stream().collect(Collectors.toMap(EvalIndexRef::getResourceId, evalIndexRef -> evalIndexRef));

        // 指标项使用的算法
        List<String> usingAlgorithmIds = evalIndexRefs.stream().map(EvalIndexRef::getAlgorithmId).collect(Collectors.toList());
        Map<String, EvalAlgorithmRef> evalAlgorithmRefMap = evalAlgorithmRefService.lambdaQuery()
                .in(EvalAlgorithmRef::getResourceId, usingAlgorithmIds).list()
                .stream().collect(Collectors.toMap(EvalAlgorithmRef::getResourceId, evalAlgorithmRef -> evalAlgorithmRef));

        // 四级指标使用的变量项
        Map<String, EvalVariableRef> evalVariableRefMap = evalVariableRefService.lambdaQuery()
                .eq(EvalVariableRef::getRecordId, evalRecord.getResourceId())
                .list()
                .stream().collect(Collectors.toMap(EvalVariableRef::getResourceId, evalVariableRef -> evalVariableRef));

        evalIndexRefs.forEach(evalIndexRef -> {
            EvalAlgorithmRef evalAlgorithmRef = evalAlgorithmRefMap.get(evalIndexRef.getAlgorithmId());
            evalIndexRefService.startIndex(evalIndexRef, evalAlgorithmRef, evalIndexRefMap, evalVariableRefMap);
        });
    }

    private void syncProbeData(EvalRecord evalRecord, EvalTask evalTask) {

        // 循环变量项/探针id, invoke mp, 同步数据到bc数据库, 每同步一次（重复数据去重）, 更新变量项progress -> start  todo 类型为系统评估
        // todo 数据同步验证/ 避免重复
        List<EvalVariableRef> evalVariableRefs = evalVariableRefService.lambdaQuery()
                .eq(EvalVariableRef::getRecordId, evalRecord.getResourceId())
                .in(EvalVariableRef::getProgress, Arrays.asList(EvalVariableRef.Progress.NOT_START.getCode(), EvalVariableRef.Progress.START.getCode()))
                .list();

        evalVariableRefs.forEach(evalVariableRef -> {
            // 变量项开始运算
            evalVariableRef.setProgress(EvalVariableRef.Progress.START.getCode());
            evalVariableRefService.updateById(evalVariableRef);

            List<EvalProbeData> evalProbeDataList = Lists.newArrayList();

            // 当前探针数据是否已经被同步
            boolean isExist = SqlHelper.retBool(evalProbeDataService.lambdaQuery()
                    .eq(EvalProbeData::getSceneId, evalTask.getMpSceneId())
                    .eq(EvalProbeData::getNodeId, evalVariableRef.getDeviceId())
                    .eq(EvalProbeData::getProbeId, evalVariableRef.getProbeId())
                    .last("limit 1")
                    .count());

            // 变量项使用同一探针, 数据已经被同步, 跳过
            if (!isExist) {
                CollectionEventPageContext collectionEventPageContext = new CollectionEventPageContext();
                // todo 无视时间范围, 全部同步
                collectionEventPageContext.setSceneId(evalTask.getMpSceneId());
                collectionEventPageContext.setNodeIds(Collections.singletonList(evalVariableRef.getDeviceId()));
                collectionEventPageContext.setProbeId(Collections.singletonList(evalVariableRef.getProbeId()));
                collectionEventPageContext.setStartTime(evalRecord.getStartTime());
                collectionEventPageContext.setEndTime(evalRecord.getEndTime());
                // todo 暂未分页调用, 待调整
                collectionEventPageContext.setPageSize((long) (10 * 10000));

                remoteSceneService.getCollectionEvent(collectionEventPageContext).forEach(
                        mpCollectionEvent -> evalProbeDataList.add(BeanUtils.copy(mpCollectionEvent, EvalProbeData.class))
                );
            }

            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    if (!evalProbeDataList.isEmpty()) {
                        evalProbeDataService.saveBatch(evalProbeDataList, 100);
                    }
                    evalVariableRef.setProgress(EvalVariableRef.Progress.FORMATTING.getCode());
                    evalVariableRefService.updateById(evalVariableRef);
                }
            });
        });
    }

    private AlgorithmContext generateContext(Integer type, String id) {
        AlgorithmContext algorithmContext = new AlgorithmContext();
        algorithmContext.setId(id);
        algorithmContext.setType(type);

        return algorithmContext;
    }

    @Override
    public List<EvalRoomPairRsp> getNodes(String roomId) {
        EvalTask evalTask = evalTaskService.getTask(roomId);
        if (evalTask == null) {
            throw new BusinessException();
        }

        List<EvalRoomPairRsp> results = Lists.newArrayList();

        JSONObject jsonObject = remoteSceneService.getById(evalTask.getMpSceneId(), UserInfoContext.getUserContext().getUserId());
        JSONArray nodesProbe = jsonObject.getJSONObject("scene").getJSONObject("topology").getJSONArray("elements");
        nodesProbe.forEach(item -> {
            JSONObject nodeItem = (JSONObject) item;

            EvalRoomPairRsp result = new EvalRoomPairRsp();
            result.setId(nodeItem.getString("id"));
            result.setLabel(nodeItem.getString("name"));
            results.add(result);
        });

        return results;
    }

    @Override
    public List<EvalRoomPairRsp> getProbes(String roomId, String nodeId) {
        EvalTask evalTask = evalTaskService.getTask(roomId);
        if (evalTask == null) {
            throw new BusinessException();
        }

        List<EvalRoomPairRsp> results = Lists.newArrayList();

        JSONObject jsonObject = remoteSceneService.getById(evalTask.getMpSceneId(), UserInfoContext.getUserContext().getUserId());
        JSONObject nodesProbe = jsonObject.getJSONObject("scene").getJSONObject("plugins").getJSONObject("cpcollection").getJSONObject("elements");
        for (Map.Entry<String, Object> entry : nodesProbe.entrySet()) {
            if (!entry.getKey().equals(nodeId)) {
                continue;
            }

            JSONObject itemData = (JSONObject) entry.getValue();
            JSONArray probesData = itemData.getJSONArray("probes");

            probesData.forEach(item -> {
                JSONObject probeItem = (JSONObject) item;

                EvalRoomPairRsp result = new EvalRoomPairRsp();
                result.setId(probeItem.getString("id"));
                result.setLabel(probeItem.getString("name"));
                results.add(result);
            });
            break;
        }

        return results;
    }

    @Override
    public EvalRecordResultRsp result(String recordId) {
        EvalRecordResultRsp result = new EvalRecordResultRsp();
        BeanUtils.copyProperties(getEntity(recordId), result);

        List<EvalIndexRef> evalIndexRefs = evalIndexRefService.lambdaQuery().eq(EvalIndexRef::getRecordId, recordId).list();
        evalIndexRefs.sort(Comparator.comparing(EvalIndexRef::getLevel));

        Map<String, EvalIndexRef> evalIndexRefMap = evalIndexRefs.stream().collect(Collectors.toMap(EvalIndexRef::getResourceId, evalIndexRef -> evalIndexRef));
        Map<String, EvalVariableRef> evalVariableRefMap = evalVariableRefService.lambdaQuery()
                .eq(EvalVariableRef::getRecordId, recordId)
                .list().stream().collect(Collectors.toMap(EvalVariableRef::getResourceId, evalVariableRef -> evalVariableRef));

        result.setIndexes(Lists.newArrayList());
        for (EvalIndexRef evalIndexRef : evalIndexRefs) {
            if (evalIndexRef.getLevel().equals(EvalIndex.Level.FIRST.getCode())) {
                result.getIndexes().add(generateNextIndexes(evalIndexRef, evalIndexRefMap, evalVariableRefMap, true));
            } else {
                break;
            }
        }

        return result;
    }

    @Override
    public List<EvalRoomIntPairRsp> recordStatusEnum() {

        return Arrays.stream(EvalRecord.Status.values()).map(item -> {
            EvalRoomIntPairRsp result = new EvalRoomIntPairRsp();
            result.setLabel(item.getDesc());
            result.setId(item.getCode());
            return result;
        }).collect(Collectors.toList());
    }
}
