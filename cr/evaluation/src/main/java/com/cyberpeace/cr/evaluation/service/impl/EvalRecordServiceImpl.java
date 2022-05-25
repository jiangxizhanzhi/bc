package com.cyberpeace.cr.evaluation.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalRecordMapper;
import com.cyberpeace.cr.evaluation.service.*;
import com.cyberpeace.cr.evaluation.service.context.EvalRecordSpecialistContext;
import com.cyberpeace.cr.evaluation.service.context.EvalRecordVariableContext;
import com.cyberpeace.cr.evaluation.service.context.EvalSystemIndexesContext;
import com.cyberpeace.integratation.model.mp.MpCollectionEvent;
import com.cyberpeace.integratation.model.scene.CollectionEventPageContext;
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

import java.math.BigDecimal;
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

    private static final long onceProbeDataSize = 100L;

    public EvalRecord getEntity(String resourceId) {
        // todo 文本调整
        EvalRecord evalRecord = this.getBaseMapper().selectById(resourceId);
        if (evalRecord == null) {
            throw new BusinessException("评估未找到");
        }
        if (!evalRecord.getStatus().equals(EvalRecord.Status.NOT_START.getCode())) {
            throw new BusinessException("评估未启动");
        }
        return evalRecord;
    }

    @Override
    public ListRsp<EvalRecordListRsp> pageList(Page page, String taskId, Integer status, String pattern) {
        return PageUtil.getListResult(this.getBaseMapper().pageList(page, taskId, status, pattern));
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
            if (evalRecordIndexContext.getType().equals(EvalIndexRef.Type.SPECIALIST.getCode())) {
                thirdSpecialistMap.put(evalRecordIndexContext.getResourceId(), evalRecordIndexContext.getSpecialist().getValue());
            }
            evalRecordIndexContext.getVariables().forEach(
                    evalRecordVariableContext -> evalRecordVariableContextMap.put(evalRecordVariableContext.getResourceId(), evalRecordVariableContext)
            );
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
        List<EvalCollection> evalCollections = !evalRecordVariableContextMap.isEmpty()
                ? evalCollectionService.lambdaQuery().in(EvalCollection::getResourceId, evalRecordVariableContextMap.keySet()).list()
                : Lists.newArrayList();

        // 请求体变量项数量与db获取体系下变量项数量一致
        if (evalCollections.size() != evalRecordVariableContextMap.keySet().size()) {
            throw new BusinessException(CrBusinessErrorCode.SYSTEM_INDEX_IS_FAILED);
        }

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

        // todo 当前算法重复拷贝， 待过滤
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

            // 构建变量项新的算法数据 - 格式化算法
            EvalAlgorithmRef newFormatAlgorithm = generateAlgorithmRef(evalCollection.getFormatAlgorithmId(), evalAlgorithmMap);
            evalVariableRef.setFormatAlgorithmId(newFormatAlgorithm.getResourceId());
            evalVariableRef.setFormatParams(evalCollection.getFormatAlgorithmParams());
            evalAlgorithmRefList.add(newFormatAlgorithm);

            // 匹配算法
            EvalAlgorithmRef newMatchAlgorithm = generateAlgorithmRef(evalCollection.getDetectionAlgorithmId(), evalAlgorithmMap);
            evalVariableRef.setMatchAlgorithmId(newMatchAlgorithm.getResourceId());
            evalVariableRef.setMatchParams(evalCollection.getDetectionAlgorithmParams());
            evalAlgorithmRefList.add(newMatchAlgorithm);

            // 评分算法
            EvalAlgorithmRef newMarkAlgorithm = generateAlgorithmRef(evalCollection.getScoreAlgorithmId(), evalAlgorithmMap);
            evalVariableRef.setMarkAlgorithmId(newMarkAlgorithm.getResourceId());
            evalVariableRef.setMarkParams(evalCollection.getScoreAlgorithmParams());
            evalAlgorithmRefList.add(newMarkAlgorithm);

            // 设置使用节点、探针id
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

            List<String> nextIds = Lists.newArrayList();
            JsonUtil.toJsonObjectArray(evalAggregation.getCollectionIds(), String.class).forEach(item -> {
                if (variableIdsMap.containsKey(item)) {
                    nextIds.add(variableIdsMap.get(item));
                }
            });
            evalIndexRef.setNextIds(JsonUtil.toJsonString(nextIds));

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

            // 设置下一级、备份算法信息
            String nextIds;
            EvalAlgorithmRef evalAlgorithmRef;
            if (!evalIndex.getLevel().equals(EvalIndex.Level.THIRD.getCode())) {
                evalIndexRef.setAlgorithmParams(evalIndex.getAggregatingAlgorithmParams());

                nextIds = evalIndex.getChildrenIds();
                evalAlgorithmRef = generateAlgorithmRef(evalIndex.getAggregatingAlgorithmId(), evalAlgorithmMap);
            } else {
                EvalIndicator evalIndicator = thirdRelationMap.get(evalIndex.getEvalIndicatorId());
                if (evalIndicator == null) {
                    throw new BusinessException(CrBusinessErrorCode.THIRD_INDEX_IS_FAILED, evalIndex.getName());
                }

                nextIds = evalIndicator.getAggregationIds();
                evalAlgorithmRef = generateAlgorithmRef(evalIndicator.getAggregatingAlgorithmId(), evalAlgorithmMap);

                // 专家评分, 设置默认分值
                evalIndexRef.setSpecialistType(evalIndicator.getDatatype());
                evalIndexRef.setSpecialistAttr(evalIndicator.getAttribute());
                evalIndexRef.setSpecialistAttrValue(evalIndicator.getAttributeValue());
                evalIndexRef.setSpecialistValue(thirdSpecialistMap.get(evalIndex.getResourceId()));
                if (evalIndexRef.getType().equals(EvalIndexRef.Type.SPECIALIST.getCode())) {
                    evalIndexRef.setScore(evalIndexRefService.thirdSpecialist(
                            evalIndexRef.getSpecialistType(),
                            evalIndexRef.getSpecialistAttr(),
                            evalIndexRef.getSpecialistAttrValue(),
                            evalIndexRef.getSpecialistValue()
                    ));
                }
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

        Map<String, EvalIndexRef> evalIndexRefMap = evalIndexRefService.lambdaQuery()
                .eq(EvalIndexRef::getRecordId, newEvalRecord.getResourceId())
                .eq(EvalIndexRef::getLevel, EvalIndex.Level.THIRD.getCode())
                .list()
                .stream().collect(Collectors.toMap(EvalIndexRef::getResourceId, evalIndexRef -> evalIndexRef));
        // 重复数据仅编辑一次
        Set<String> indexesSet = Sets.newHashSet();
        Set<String> variablesSet = Sets.newHashSet();

        List<EvalIndexRef> evalIndexRefs = Lists.newArrayList();
        List<EvalVariableRef> evalVariableRefs = Lists.newArrayList();

        requestData.getIndexes().forEach(evalRecordIndexContext -> {
            EvalIndexRef evalIndexRef = evalIndexRefMap.get(evalRecordIndexContext.getResourceId());

            if (!indexesSet.contains(evalIndexRef.getResourceId())) {
                evalIndexRef.setType(evalRecordIndexContext.getType());
                if (evalRecordIndexContext.getType().equals(EvalIndexRef.Type.SPECIALIST.getCode())) {
                    evalIndexRef.setSpecialistValue(evalRecordIndexContext.getSpecialist().getValue());
                    evalIndexRef.setScore(evalIndexRefService.thirdSpecialist(
                            evalIndexRef.getSpecialistType(),
                            evalIndexRef.getSpecialistAttr(),
                            evalIndexRef.getSpecialistAttrValue(),
                            evalIndexRef.getSpecialistValue()
                    ));
                }
                evalIndexRefs.add(evalIndexRef);
                indexesSet.add(evalIndexRef.getResourceId());
            }

            evalRecordIndexContext.getVariables().forEach(evalRecordVariableContext -> {
                if (!variablesSet.contains(evalRecordVariableContext.getResourceId())) {
                    EvalVariableRef evalVariableRef = new EvalVariableRef();
                    BeanUtils.copyProperties(evalRecordVariableContext, evalVariableRef);
                    evalVariableRef.setResourceId(evalRecordVariableContext.getResourceId());
                    evalVariableRef.setDeviceId(evalRecordVariableContext.getDeviceId());
                    evalVariableRef.setProbeId(evalRecordVariableContext.getProbeId());
                    evalVariableRefs.add(evalVariableRef);
                    variablesSet.add(evalVariableRef.getResourceId());
                }
            });
        });



        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                evalVariableRefService.updateBatchById(evalVariableRefs);
                evalIndexRefService.updateBatchById(evalIndexRefs);
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

                    evalSystemIndexesContext.setSpecialist(new EvalRecordSpecialistContext());
                    evalSystemIndexesContext.getSpecialist().setValue(evalIndexRef.getSpecialistValue());
                    evalSystemIndexesContext.getSpecialist().setType(evalIndexRef.getSpecialistType());
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
    @Async
    public void recordRun(EvalRecord evalRecord, EvalTask evalTask, Boolean isRestart) {
        EvalLog.info(evalRecord, String.format("Range Test evaluation %s %s.", evalRecord.getName(), isRestart ? "RESTART" : "START"));
        if (StringUtils.isEmpty(evalRecord.getMessage())) {
            evalRecord.setMessage("");
        }

        // 需要计算变量项id集合
        EvalLog.info(evalRecord, "collect wait to calculate variables id...");
        Set<String> variableIds = evalIndexRefService.systemEvalVariableIds(evalRecord.getResourceId());
        EvalLog.info(evalRecord, String.format("collect wait to calculate variables id completed: %s", variableIds.toString()));

        // 循环同步采集数据
        EvalLog.info(evalRecord, "sync remote server [MP] probe data...");
        syncProbeData(evalRecord, evalTask, variableIds);
        EvalLog.info(evalRecord, "sync remote server [MP] probe data completed.");

        // 执行变量项
        startVariables(evalRecord, evalTask, variableIds);
        // 执行指标项
        startIndexes(evalRecord, evalTask);

    }

    private void startVariables(EvalRecord evalRecord, EvalTask evalTask, Set<String> variableIds) {
        if (variableIds.isEmpty()) {
            return;
        }

        List<EvalVariableRef> evalVariableRefList = evalVariableRefService.lambdaQuery()
                .in(EvalVariableRef::getResourceId, variableIds)
                .ne(EvalVariableRef::getProgress, EvalVariableRef.Progress.NOT_START.getCode())
                .ne(EvalVariableRef::getProgress, EvalVariableRef.Progress.END.getCode())
                .list();

        // 运行中变量项为空, 退出执行下一步
        if (evalVariableRefList.isEmpty()) {
            return;
        }

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
            // 采集变量项算法依次执行
            String result = evalVariableRefService.startVariableAlgorithm(evalVariableRef, evalAlgorithmRefMap, evalRecord, evalTask);

            // 非null返回值为采集变量项运行失败
            if (!StringUtils.isEmpty(result)) {
                evalVariableRef.setScore(0);
                evalVariableRef.setProgress(EvalVariableRef.Progress.END.getCode());
                evalRecord.setMessage(evalRecord.getMessage() + result + "\n");
                transactionTemplate.execute(status -> {
                    evalVariableRefService.updateById(evalVariableRef);
                    return updateById(evalRecord);
                });
            }
        });

    }

    private void startIndexes(EvalRecord evalRecord, EvalTask evalTask) {

        // 体系下指标项
        List<EvalIndexRef> evalIndexRefs = evalIndexRefService.lambdaQuery().eq(EvalIndexRef::getRecordId, evalRecord.getResourceId()).list();
        // 由"level"字段倒序循环计算, 确保指标项子节点"score"一定不为null
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
            // 仅执行系统评分类型, 专家评分拥有默认分值不需执行
            if (evalIndexRef.getType().equals(EvalIndexRef.Type.SYSTEM.getCode())) {
                evalIndexRefService.startIndex(evalIndexRef, evalAlgorithmRef, evalIndexRefMap, evalVariableRefMap, evalRecord);
            }
        });

        evalRecord.setStatus(StringUtils.isEmpty(evalRecord.getMessage()) ? EvalRecord.Status.END.getCode() : EvalRecord.Status.EXCEPTION.getCode());
        evalRecord.setScore(evalIndexRefs.get(evalIndexRefs.size() - 1).getScore());
        transactionTemplate.execute(status -> {
            evalIndexRefService.updateBatchById(evalIndexRefs);
            return this.getBaseMapper().updateById(evalRecord);
        });
    }

    private void syncProbeData(EvalRecord evalRecord, EvalTask evalTask, Set<String> variableIds) {
        if (variableIds.isEmpty()) {
            EvalLog.info(evalRecord, "variables id is empty, continue.");
            return;
        }

        // 获取需要同步数据的变量项
        List<EvalVariableRef> evalVariableRefs = evalVariableRefService.lambdaQuery()
                .in(EvalVariableRef::getResourceId, variableIds)
                .in(EvalVariableRef::getProgress, Arrays.asList(EvalVariableRef.Progress.NOT_START.getCode(), EvalVariableRef.Progress.START.getCode()))
                .list();

        // 没有需要同步数据变量项, 跳过
        if (evalVariableRefs.isEmpty()) {
            EvalLog.info(evalRecord, "variables data has been sync, continue.");
            return;
        }

        // 终端节点IP <nodeId, nodeIp>
        Map<String, String> ipMap = nodeIpMap(evalTask.getMpSceneId());

        // 循环变量项/探针id(存在变量项使用相同探针场景), invoke mp, 同步数据到bc数据库
        evalVariableRefs.forEach(evalVariableRef -> {
            // 变量项开始运算
            if (evalVariableRef.getProgress().equals(EvalVariableRef.Progress.NOT_START.getCode())) {
                EvalLog.info(evalRecord, String.format("[%s] variable [%s] sync start.", evalVariableRef.getResourceId(), evalVariableRef.getName()));
                evalVariableRef.setProgress(EvalVariableRef.Progress.START.getCode());
                evalVariableRefService.updateById(evalVariableRef);
            }
            EvalLog.info(evalRecord, String.format(
                    "[%s] variable [%s] used nodeId %s probeId %s.",
                    evalVariableRef.getResourceId(),
                    evalVariableRef.getName(),
                    evalVariableRef.getDeviceId(),
                    evalVariableRef.getProbeId()
            ));

            // 当前探针数据是否已经被同步  todo 不再使用"limit", 获取数量用于动态分页, 避免大批量进行saveBatch

            // 变量项使用同一探针, 数据已经被同步, 跳过
            CollectionEventPageContext collectionEventPageContext = new CollectionEventPageContext();
            collectionEventPageContext.setId(evalTask.getMpSceneId());
            collectionEventPageContext.setNodeIds(evalVariableRef.getDeviceId());
            collectionEventPageContext.setProbeId(evalVariableRef.getProbeId());

            // 查询当前已同步数据量, 生成页码
            int probeDataCount = evalProbeDataService.lambdaQuery()
                    .eq(EvalProbeData::getSceneId, evalTask.getMpSceneId())
                    .eq(EvalProbeData::getDeviceId, evalVariableRef.getDeviceId())
                    .eq(EvalProbeData::getProbeId, evalVariableRef.getProbeId())
                    .count();
            if (!SqlHelper.retBool(probeDataCount)) {
                collectionEventPageContext.setPage(1L);
            } else {
                // 数据量百位取整, 生成当前页码
                int count = new BigDecimal(probeDataCount).setScale(-2, BigDecimal.ROUND_UP).intValue();
                collectionEventPageContext.setPage((count / onceProbeDataSize) + 1);
            }
            collectionEventPageContext.setPageSize(onceProbeDataSize);

            try {
                exeCollectionEvent(evalRecord, collectionEventPageContext, ipMap);

                evalVariableRef.setProgress(EvalVariableRef.Progress.FORMATTING.getCode());
            } catch (RemoteServiceException e) {
                // 采集数据报错, 采集变量项中止
                String msg = String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.GET_PROBE_DATA_FAILED), evalVariableRef.getName());
                evalRecord.setMessage(evalRecord.getMessage() + msg + "\n");

                evalVariableRef.setProgress(EvalVariableRef.Progress.END.getCode());
                evalVariableRef.setScore(0);
            }
            evalVariableRefService.updateById(evalVariableRef);

        });
    }

    private void exeCollectionEvent(EvalRecord evalRecord, CollectionEventPageContext collectionEventPageContext, Map<String, String> ipMap) {
        // todo evalRecord 入参等待用于添加日志

        List<EvalProbeData> evalProbeDataList = Lists.newArrayList();
        List<MpCollectionEvent> collectionEventList = remoteSceneService.getCollectionEvent(collectionEventPageContext);
        collectionEventList.forEach(mpCollectionEvent -> {
            EvalProbeData probeDataItem = new EvalProbeData();
            probeDataItem.setContent(mpCollectionEvent.getContent());
            probeDataItem.setSceneId(collectionEventPageContext.getId());
            probeDataItem.setDeviceId(mpCollectionEvent.getNodeId());
            probeDataItem.setDeviceIp(ipMap.get(mpCollectionEvent.getNodeId()));
            probeDataItem.setProbeId(mpCollectionEvent.getProbeId());

            JSONObject content = JsonUtil.toJsonObject(probeDataItem.getContent(), JSONObject.class);
            if (evalVariableRefService.BASE.contains(mpCollectionEvent.getProbeId())) {
                JSONObject scanAttr = content.getJSONObject("scan");
                if (scanAttr == null) {
                    // todo 执行错误
                } else {
                    probeDataItem.setCollectionTime(scanAttr.getTimestamp("time"));
                }
                // 暂不处理
            } else {
                probeDataItem.setCollectionTime(content.getTimestamp("time"));
            }

            evalProbeDataList.add(probeDataItem);

        });
        // 数据不为空, 保存
        if (!evalProbeDataList.isEmpty()) {
            evalProbeDataService.saveBatch(evalProbeDataList);
        }
        // 实际获取数据与每页数据量大小一致, 视数据未获取完成. 页码+1, 迭代执行
        if (collectionEventPageContext.getPageSize() == collectionEventList.size()) {
            collectionEventPageContext.setPage(collectionEventPageContext.getPage() + 1);
            exeCollectionEvent(evalRecord, collectionEventPageContext, ipMap);
        }
    }

    /**
     * 获取场景下节点ip
     * @param mpSceneId MP场景id
     * @return Map
     */
    private Map<String, String> nodeIpMap(String mpSceneId) {
        JSONObject jsonObject = remoteSceneService.getById(mpSceneId, null);
        JSONArray nodes = jsonObject.getJSONObject("scene").getJSONObject("topology").getJSONArray("elements");

        Map<String, String> results = Maps.newHashMap();
        nodes.forEach(item -> {
            JSONObject nodeItem = (JSONObject) item;

            String nodeId = nodeItem.getString("id");
            List<String> ips = Lists.newArrayList();

            nodeItem.getJSONArray("ports").forEach(port -> {
                JSONObject portInfo = (JSONObject) port;
                if (!StringUtils.isEmpty(portInfo.getString("ip"))) {
                    ips.add(portInfo.getString("ip"));
                }
            });
            results.put(nodeId, StringUtils.join(ips, ","));
        });
        return results;
    }

    @Override
    public List<EvalRoomPairRsp> getNodes(String roomId) {
        EvalTask evalTask = evalTaskService.getTask(roomId);
        if (evalTask == null) {
            throw new BusinessException();
        }

        List<EvalRoomPairRsp> results = Lists.newArrayList();

        JSONObject jsonObject = remoteSceneService.getById(evalTask.getMpSceneId(), UserInfoContext.getUserContext().getUserId());
        JSONArray nodes = jsonObject.getJSONObject("scene").getJSONObject("topology").getJSONArray("elements");
        nodes.forEach(item -> {
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

        // todo 修改响应数据结构, 以Map<node_id, List<EvalRoomPairRsp#{探针信息}>>的数据结构返回
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
    public Map<String, List<EvalRoomPairRsp>> getProbes(String roomId) {
        EvalTask evalTask = evalTaskService.getTask(roomId);

        JSONObject jsonObject = remoteSceneService.getById(evalTask.getMpSceneId(), UserInfoContext.getUserContext().getUserId());
        JSONObject nodesProbe = jsonObject.getJSONObject("scene").getJSONObject("plugins").getJSONObject("cpcollection").getJSONObject("elements");

        Map<String, List<EvalRoomPairRsp>> results = Maps.newHashMap();
        for (Map.Entry<String, Object> entry : nodesProbe.entrySet()) {

            List<EvalRoomPairRsp> probes = Lists.newArrayList();
            JSONArray probesData = ((JSONObject) entry.getValue()).getJSONArray("probes");

            probesData.forEach(item -> {
                JSONObject probeItem = (JSONObject) item;

                EvalRoomPairRsp result = new EvalRoomPairRsp();
                result.setId(probeItem.getString("id"));
                result.setLabel(probeItem.getString("name"));
                probes.add(result);
            });

            results.put(entry.getKey(), probes);
        }
        return results;
    }

    @Override
    public List<EvalRecordNodeProbeReq> getNodeProbe(String roomId) {
        EvalTask evalTask = evalTaskService.getTask(roomId);

        JSONObject jsonObject = remoteSceneService.getById(evalTask.getMpSceneId(), UserInfoContext.getUserContext().getUserId());

        JSONArray nodes = jsonObject.getJSONObject("scene").getJSONObject("topology").getJSONArray("elements");
        JSONObject nodesProbe = jsonObject.getJSONObject("scene").getJSONObject("plugins").getJSONObject("cpcollection").getJSONObject("elements");

        List<EvalRecordNodeProbeReq> results = Lists.newArrayList();
        nodes.forEach(node -> {
            JSONObject nodeItem = (JSONObject) node;

            EvalRecordNodeProbeReq nodeData = new EvalRecordNodeProbeReq();
            nodeData.setId(nodeItem.getString("id"));
            nodeData.setLabel(nodeItem.getString("name"));
            nodeData.setData(Lists.newArrayList());

            JSONObject collectionData = nodesProbe.getJSONObject(nodeData.getId());
            if (collectionData != null) {
                JSONArray probes = collectionData.getJSONArray("probes");
                probes.forEach(probe -> {
                    JSONObject probeItem = (JSONObject) probe;

                    EvalRoomPairRsp probeData = new EvalRoomPairRsp();
                    probeData.setId(probeItem.getString("id"));
                    probeData.setLabel(probeItem.getString("name"));
                    nodeData.getData().add(probeData);
                });
            }

            results.add(nodeData);
        });

        return results;
    }

    @Override
    public EvalRecordResultRsp result(String recordId) {
        EvalRecordResultRsp result = new EvalRecordResultRsp();
        BeanUtils.copyProperties(getById(recordId), result);

        List<EvalIndexRef> evalIndexRefs = evalIndexRefService.lambdaQuery().eq(EvalIndexRef::getRecordId, recordId).list();
        evalIndexRefs.sort(Comparator.comparing(EvalIndexRef::getLevel));

        Map<String, EvalIndexRef> evalIndexRefMap = evalIndexRefs.stream().collect(Collectors.toMap(EvalIndexRef::getResourceId, evalIndexRef -> evalIndexRef));
        Map<String, EvalVariableRef> evalVariableRefMap = evalVariableRefService.lambdaQuery()
                .eq(EvalVariableRef::getRecordId, recordId)
                .list()
                .stream().collect(Collectors.toMap(EvalVariableRef::getResourceId, evalVariableRef -> evalVariableRef));

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
