package com.cyberpeace.cr.evaluation.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.HtmlTemplateEnum;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.crbase.service.context.TrainingReport;
import com.cyberpeace.cr.evaluation.controller.bo.EvalTaskBo;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.*;
import com.cyberpeace.cr.evaluation.service.*;
import com.cyberpeace.cr.evaluation.service.context.EvalRecordSpecialistContext;
import com.cyberpeace.cr.evaluation.service.context.EvalRecordVariableContext;
import com.cyberpeace.cr.evaluation.service.context.EvalSystemIndexesContext;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomSceneMapper;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.mp.MpCollectionEvent;
import com.cyberpeace.integratation.model.scene.CollectionEventPageContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.impl.RemoteServerEnvService;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.averagingInt;

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
    private RemoteSceneService remoteSceneService;

    @Autowired
    private EvalProbeDataService evalProbeDataService;

    @Autowired
    private EvalAlgorithmRefService evalAlgorithmRefService;

    @Autowired
    private EvalTaskMapper evalTaskMapper;

    @Autowired
    private EvalTaskStepService evalTaskStepService;

    @Autowired
    @Lazy
    private EvalTemplateService evalTemplateService;

    @Autowired
    private LocalSceneService localSceneService;

    @Autowired
    private EvalVariableRefProbeDataService evalVariableRefProbeDataService;

    @Autowired
    private CrConstantsService crConstantsService;

    @Autowired
    private EvalVariableRefMapper evalVariableRefMapper;

    @Autowired
    private RemoteServerEnvService remoteServerEnvService;

    @Autowired
    private RbRoomSceneMapper rbRoomSceneMapper;

    @Autowired
    private EvalRecordMapper evalRecordMapper;

    @Autowired
    private EvalResultMapper evalResultMapper;

    @Autowired
    private CrEvalTaskSubmitMapper crEvalTaskSubmitMapper;

    @Autowired
    private EvalRoleScoreService evalRoleScoreService;

    private static final long onceProbeDataSize = 1000L;  // 10/100/1000

    @Override
    public void isExist(EvalRecordAddReq requestData) {
        LambdaQueryChainWrapper<EvalRecord> querySet = lambdaQuery().eq(EvalRecord::getTaskId, requestData.getTaskId()).eq(EvalRecord::getName, requestData.getName());

        if (requestData.getResourceId() != null) {
            querySet = querySet.ne(EvalRecord::getResourceId, requestData.getResourceId());
        }

        if (SqlHelper.retBool(querySet.count())) {
            throw new BusinessException(ErrorCode.NAME_DUPLICATE);
        }
    }

    /**
     * 获取测评任务实例
     *
     * @param taskId 任务id(roomId)
     * @return EvalTask
     */
    private EvalTask getTask(String taskId) {
        EvalTask evalTask = evalTaskMapper.selectById(taskId);

        // 校验任务是否存在
        if (evalTask == null) {
            throw new BusinessException(CrBusinessErrorCode.EVAL_TASK_NOT_EXIST);
        }

        // 校验用户权限
        if (!Objects.equals(evalTask.getCreateUserId(), UserInfoContext.getUserContext().getUserId())) {
            throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
        }

        return evalTask;
    }

    @Override
    public EvalRecord getEntity(String resourceId) {
        // 非法请求
        EvalRecord evalRecord = this.getBaseMapper().selectById(resourceId);
        if (evalRecord == null) {
            throw new BusinessException("评估未找到");
        }
        if (!evalRecord.getStatus().equals(EvalRecord.Status.NOT_START.getCode())) {
            throw new BusinessException("评估已启动");
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
        recordRsp.setGradeList(JSON.parseArray(evalRecord.getGrade(), Integer.class));
        recordRsp.setIndexes(Lists.newArrayList());
        List<EvalIndexRef> fullIndexes = evalIndexRefService.lambdaQuery().eq(EvalIndexRef::getRecordId, recordRsp.getResourceId()).list();
        fullIndexes.sort(Comparator.comparing(EvalIndexRef::getLevel));

        Map<String, EvalIndexRef> fullIndexesMap = fullIndexes.stream().collect(Collectors.toMap(EvalIndexRef::getResourceId, evalIndexRef -> evalIndexRef));

        Map<String, EvalVariableRef> fullVariablesMap = evalVariableRefService.lambdaQuery().eq(EvalVariableRef::getRecordId, recordRsp.getResourceId()).list().stream().collect(Collectors.toMap(EvalVariableRef::getResourceId, evalVariableRef -> evalVariableRef));

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
            evalRecord.setType(EvalRecord.Type.ADD.getCode());
            evalRecord.setGrade(JSON.toJSONString(requestData.getGradeList()));
        }

        // 获取"系统评分"指标项下的变量项节点、探针信息
        Map<String, EvalRecordVariableContext> evalRecordVariableContextMap = Maps.newHashMap();

        // 获取"三级指标"评分类型
        Map<String, Integer> fifthSpecialistMap = Maps.newHashMap();
        requestData.getIndexes().forEach(evalRecordIndexContext -> {
            if (evalRecordIndexContext.getType().equals(EvalIndexRef.Type.SPECIALIST.getCode())) {
                fifthSpecialistMap.put(evalRecordIndexContext.getResourceId(), evalRecordIndexContext.getSpecialist().getValue());
            }
            evalRecordIndexContext.getVariables().forEach(evalRecordVariableContext -> evalRecordVariableContextMap.put(evalRecordVariableContext.getResourceId(), evalRecordVariableContext));
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
        // 三级指标关联表
        List<EvalIndicator> thirdRelationList = evalIndicatorService.lambdaQuery().in(EvalIndicator::getResourceId, evalIndicatorIds).list();
        // 四级指标
        List<EvalAggregation> evalAggregations = evalAggregationService.lambdaQuery().in(EvalAggregation::getEvalIndicatorId, evalIndicatorIds).list();
        // 五级指标
        List<EvalCollection> evalCollections = !evalRecordVariableContextMap.isEmpty() ? evalCollectionService.lambdaQuery().in(EvalCollection::getResourceId, evalRecordVariableContextMap.keySet()).list() : Lists.newArrayList();

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
        usingAlgorithmIds.addAll(thirdRelationList.stream().map(EvalIndicator::getAggregatingAlgorithmId).collect(Collectors.toList()));

        // 查询使用算法生成Map
        Map<String, EvalAlgorithm> evalAlgorithmMap = evalAlgorithmService.lambdaQuery().in(EvalAlgorithm::getResourceId, usingAlgorithmIds).list().stream().collect(Collectors.toMap(EvalAlgorithm::getResourceId, evalAlgorithm -> evalAlgorithm));
        Map<String, EvalAlgorithmRef> evalAlgorithmRefMap = Maps.newHashMap();

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
            EvalAlgorithmRef newFormatAlgorithm = generateAlgorithmRef(evalCollection.getFormatAlgorithmId(), evalAlgorithmMap, evalAlgorithmRefMap);
            evalVariableRef.setFormatAlgorithmId(newFormatAlgorithm.getResourceId());
            evalVariableRef.setFormatParams(evalCollection.getFormatAlgorithmParams());

            // 匹配算法
            EvalAlgorithmRef newMatchAlgorithm = generateAlgorithmRef(evalCollection.getDetectionAlgorithmId(), evalAlgorithmMap, evalAlgorithmRefMap);
            evalVariableRef.setMatchAlgorithmId(newMatchAlgorithm.getResourceId());
            evalVariableRef.setMatchParams(evalCollection.getDetectionAlgorithmParams());

            // 评分算法
            EvalAlgorithmRef newMarkAlgorithm = generateAlgorithmRef(evalCollection.getScoreAlgorithmId(), evalAlgorithmMap, evalAlgorithmRefMap);
            evalVariableRef.setMarkAlgorithmId(newMarkAlgorithm.getResourceId());
            evalVariableRef.setMarkParams(evalCollection.getScoreAlgorithmParams());

            // 设置使用节点、探针id
            EvalRecordVariableContext evalRecordVariableContext = evalRecordVariableContextMap.get(evalCollection.getResourceId());
            evalVariableRef.setProbeId(evalRecordVariableContext.getProbeId());
            evalVariableRef.setDeviceId(evalRecordVariableContext.getDeviceId());

            // 设置5级变量项的评分方式
            if(evalRecordVariableContextMap.containsKey(evalCollection.getResourceId())){
                evalVariableRef.setScore(evalRecordVariableContext.getWeight());
            }

            variableRefs.add(evalVariableRef);
            variableIdsMap.put(evalCollection.getResourceId(), evalVariableRef.getResourceId());
        });

        Map<String, EvalIndicator> thirdRelationMap = thirdRelationList.stream().collect(Collectors.toMap(EvalIndicator::getResourceId, evalIndicator -> evalIndicator));

        // 生成聚合项数据（四级）
        List<EvalIndexRef> indexRefs = Lists.newArrayList();
        Map<String, String> evalIndexIdsMap = Maps.newHashMap();

        evalAggregations.forEach(evalAggregation -> {
            EvalIndexRef evalIndexRef = new EvalIndexRef();
            evalIndexRef.setResourceId(IDGeneratorUtil.getID());
            evalIndexRef.setRecordId(evalRecord.getResourceId());
            evalIndexRef.setName(evalAggregation.getName());
            evalIndexRef.setType(EvalIndexRef.Type.SYSTEM.getCode());
            evalIndexRef.setLevel(EvalIndexRef.Level.AGGREGATION.getCode());
            evalIndexRef.setProgress(EvalIndexRef.Progress.NOT_START.getCode());

            List<String> nextIds = Lists.newArrayList();
            JsonUtil.toJsonObjectArray(evalAggregation.getCollectionIds(), String.class).forEach(item -> {
                if (variableIdsMap.containsKey(item)) {
                    nextIds.add(variableIdsMap.get(item));
                }
            });
            evalIndexRef.setNextIds(JsonUtil.toJsonString(nextIds));

            EvalAlgorithmRef evalAlgorithmRef = generateAlgorithmRef(evalAggregation.getAggregatingAlgorithmId(), evalAlgorithmMap, evalAlgorithmRefMap);
            evalIndexRef.setAlgorithmParams(evalAggregation.getAggregatingAlgorithmParams());
            evalIndexRef.setAlgorithmId(evalAlgorithmRef.getResourceId());

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
            evalIndexRef.setType(EvalIndexRef.Type.SYSTEM.getCode());
            evalIndexRef.setLevel(evalIndex.getLevel());
            evalIndexRef.setProgress(EvalIndexRef.Progress.NOT_START.getCode());

            // 设置下一级、备份算法信息
            String nextIds;
            EvalAlgorithmRef evalAlgorithmRef;
            if (!evalIndex.getLevel().equals(EvalIndex.Level.THIRD.getCode())) {
                evalIndexRef.setAlgorithmParams(evalIndex.getAggregatingAlgorithmParams());

                nextIds = evalIndex.getChildrenIds();
                evalAlgorithmRef = generateAlgorithmRef(evalIndex.getAggregatingAlgorithmId(), evalAlgorithmMap, evalAlgorithmRefMap);
            } else {
                EvalIndicator evalIndicator = thirdRelationMap.get(evalIndex.getEvalIndicatorId());
                if (evalIndicator == null) {
                    throw new BusinessException(CrBusinessErrorCode.THIRD_INDEX_IS_FAILED, evalIndex.getName());
                }

                nextIds = evalIndicator.getAggregationIds();
                evalAlgorithmRef = generateAlgorithmRef(evalIndicator.getAggregatingAlgorithmId(), evalAlgorithmMap, evalAlgorithmRefMap);

                // 专家评分, 设置默认分值
                evalIndexRef.setSpecialistType(evalIndicator.getDatatype());
                evalIndexRef.setSpecialistAttr(evalIndicator.getAttribute());
                evalIndexRef.setSpecialistAttrValue(evalIndicator.getAttributeValue());
            }

            evalIndexRef.setAlgorithmId(evalAlgorithmRef.getResourceId());

            evalIndexRef.setNextIds(JsonUtil.toJsonString(JsonUtil.toJsonObjectArray(nextIds, String.class).stream().map(evalIndexIdsMap::get).collect(Collectors.toList())));

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
                if (!evalAlgorithmRefMap.values().isEmpty()) {
                    evalAlgorithmRefService.saveBatch(evalAlgorithmRefMap.values());
                }
            }
        });

        return get(evalRecord.getResourceId());
    }

    private EvalAlgorithmRef generateAlgorithmRef(String algorithmId, Map<String, EvalAlgorithm> originAlgorithmMap, Map<String, EvalAlgorithmRef> evalAlgorithmRefMap) {
        // 已拷贝算法, 不再重复拷贝, evalAlgorithmRefMap获取返回
        if (evalAlgorithmRefMap.containsKey(algorithmId)) {
            return evalAlgorithmRefMap.get(algorithmId);
        }

        EvalAlgorithm originAlgorithm = originAlgorithmMap.get(algorithmId);
        EvalAlgorithmRef newAlgorithm = new EvalAlgorithmRef();
        org.springframework.beans.BeanUtils.copyProperties(originAlgorithm, newAlgorithm, "id");
        newAlgorithm.setResourceId(IDGeneratorUtil.getID());

        // 拷贝过一次算法, 放入evalAlgorithmRefMap
        evalAlgorithmRefMap.put(algorithmId, newAlgorithm);
        return newAlgorithm;
    }

    @Override
    public EvalRecordRsp update(EvalRecordAddReq requestData) {
        EvalRecord originEvalRecord = getEntity(requestData.getResourceId());

        EvalRecord newEvalRecord = BeanUtils.copy(originEvalRecord, EvalRecord.class);
        BeanUtils.copyProperties(requestData, newEvalRecord);
        newEvalRecord.setGrade(JSON.toJSONString(requestData.getGradeList()));
        // 指标体系ID被更新情况, 调用this.create, 由create方法进行指标体系重建
        if (!originEvalRecord.getSystemId().equals(newEvalRecord.getSystemId())) {
            return create(requestData);
        }

        Map<String, EvalIndexRef> evalIndexRefMap = evalIndexRefService.lambdaQuery().eq(EvalIndexRef::getRecordId, newEvalRecord.getResourceId()).eq(EvalIndexRef::getLevel, EvalIndex.Level.THIRD.getCode()).list().stream().collect(Collectors.toMap(EvalIndexRef::getResourceId, evalIndexRef -> evalIndexRef));
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
                    evalIndexRef.setScore(evalIndexRefService.thirdSpecialist(evalIndexRef.getSpecialistType(), evalIndexRef.getSpecialistAttr(), evalIndexRef.getSpecialistAttrValue(), evalIndexRef.getSpecialistValue()));
                    evalIndexRef.setProgress(EvalIndexRef.Progress.END.getCode());
                } else {
                    evalIndexRef.setProgress(EvalIndexRef.Progress.NOT_START.getCode());
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
                    evalVariableRef.setScore(evalRecordVariableContext.getWeight());
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

    private EvalSystemIndexesContext generateNextIndexes(EvalIndexRef evalIndexRef, Map<String, EvalIndexRef> fullIndexesMap, Map<String, EvalVariableRef> fullVariablesMap, Boolean isFull) {
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
                    EvalSystemIndexesContext copy = BeanUtils.copy(evalVariableRef, EvalSystemIndexesContext.class);
                    copy.setWeight(evalVariableRef.getScore());
                    evalSystemIndexesContext.getChildren().add(copy);
                    variablesMap.add(variableId);
                });
            } else {
                evalSystemIndexesContext.getChildren().add(generateNextIndexes(evalIndexRef1, fullIndexesMap, fullVariablesMap, isFull));
            }
        });

        return evalSystemIndexesContext;
    }

    private EvalSystemIndexesContext generateNextIndexes(EvalIndexRef evalIndexRef, Map<String, EvalIndexRef> fullIndexesMap, Map<String, EvalVariableRef> fullVariablesMap) {

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

    @Override
    public Boolean deleteRecord(List<String> taskIds) {
        if (taskIds == null || taskIds.isEmpty()) {
            return false;
        }
        List<String> recordIds = lambdaQuery().in(EvalRecord::getTaskId, taskIds).list().stream().map(EvalRecord::getResourceId).collect(Collectors.toList());
        if (recordIds.isEmpty()) {
            return false;
        }

        return delete(recordIds);
    }

    private void deleteRelation(List<String> recordIds) {
        // todo 算法, 同步数据, 计算结果一并删除
        evalIndexRefService.removeByIds(evalIndexRefService.lambdaQuery().in(EvalIndexRef::getRecordId, recordIds).list().stream().map(EvalIndexRef::getResourceId).collect(Collectors.toList()));

        evalVariableRefService.removeByIds(evalVariableRefService.lambdaQuery().in(EvalVariableRef::getRecordId, recordIds).list().stream().map(EvalVariableRef::getResourceId).collect(Collectors.toList()));
    }

    @Override
//    @Async
    public void recordRun(EvalRecord evalRecord, Boolean isRestart, String language) {
        CommonContext.setLanguage(language);
        EvalLog.info(evalRecord, String.format("Range Test evaluation %s %s.", evalRecord.getName(), isRestart ? "RESTART" : "START"));
        if (StringUtils.isEmpty(evalRecord.getMessage())) {
            evalRecord.setMessage("");
        }

        // 需要计算变量项id集合
        EvalLog.info(evalRecord, "collect wait to calculate variables id...");
        Set<String> variableIds = evalIndexRefService.systemEvalVariableIds(evalRecord.getResourceId());
        EvalLog.info(evalRecord, String.format("collect wait to calculate variables id completed: %s", variableIds.toString()));

        String mpSceneId = getMpSceneId(evalRecord.getTemplateId());

        // 循环同步采集数据
        EvalLog.info(evalRecord, "sync remote server [MP] probe data start...");
        syncProbeData(evalRecord, variableIds, mpSceneId);
        EvalLog.info(evalRecord, "sync remote server [MP] probe data completed.");

        // 执行变量项
        EvalLog.info(evalRecord, "calculate variables start...");
//        startVariables(evalRecord, mpSceneId, variableIds);
        EvalLog.info(evalRecord, "calculate variables completed.");

        // 执行指标项
        EvalLog.info(evalRecord, "calculate indexes start...");
        startIndexes(evalRecord);
        EvalLog.info(evalRecord, "calculate indexes completed.");

        // TODO复制当前的角色任务得分情况
        EvalLog.info(evalRecord, "copy task score start...");
        copyScore(evalRecord);
        EvalLog.info(evalRecord, "copy task score completed.");

        EvalLog.info(
                evalRecord,
                String.format("END, result: %s, cost: %s", evalRecord.getScore(), evalRecord.getEvalEndTime().getTime() - evalRecord.getEvalTime().getTime())
        );
    }

    private void copyScore(EvalRecord record) {

        List<EvalTaskBo> tasks = crEvalTaskSubmitMapper.getByRoomId(record.getTemplateId());
//        int flag = 1;

//        addExtraScore(tasks, flag);
        Map<Integer, List<EvalTaskBo>> group = StreamApiUtils.groupBy(tasks, EvalTaskBo::getRoleGroup);
        List<EvalRoleScore> addList = new ArrayList<>();
        addRoleScores(record, group, addList, CrRole.RbRoleGroup.BLUE.getCode());
        addRoleScores(record, group, addList, CrRole.RbRoleGroup.RED.getCode());
        evalRoleScoreService.saveBatch(addList);

    }

    @Override
    public void addExtraScore(List<EvalTaskBo> tasks, int flag) {
        for(EvalTaskBo tmp: tasks){
            Integer score = tmp.getScore();
            if(flag == 1){
                BigDecimal rankScore = BigDecimal.valueOf(tmp.getMissionScore() * 0.2)
                        .setScale(0, BigDecimal.ROUND_HALF_UP);
                tmp.setScore(score + Integer.parseInt(rankScore.toString()));
                flag += 1;
            } else if(flag == 2){
                BigDecimal rankScore = BigDecimal.valueOf(tmp.getMissionScore() * 0.1)
                        .setScale(0, BigDecimal.ROUND_HALF_UP);
                tmp.setScore(score + Integer.parseInt(rankScore.toString()));
                flag += 1;
            } else if(flag == 3){
                BigDecimal rankScore = BigDecimal.valueOf(tmp.getMissionScore() * 0.05)
                        .setScale(0, BigDecimal.ROUND_HALF_UP);
                tmp.setScore(score + Integer.parseInt(rankScore.toString()));
                flag += 1;
            }
        }
    }

    private void addRoleScores(EvalRecord record, Map<Integer, List<EvalTaskBo>> group, List<EvalRoleScore> addList, Integer role) {
        List<EvalTaskBo> blueTasks = group.get(role);
        Map<String, List<EvalTaskBo>> blueRolesTask = StreamApiUtils.groupBy(blueTasks, EvalTaskBo::getRoleId);
        for(Map.Entry<String, List<EvalTaskBo>> tmp:blueRolesTask.entrySet()){
            String roleId = tmp.getKey();
            List<EvalTaskBo> value = tmp.getValue();
            int sum = value.stream().mapToInt(EvalTaskBo::getScore).sum();
            EvalRoleScore evalRoleScore = new EvalRoleScore();
            evalRoleScore.setRecord(record.getResourceId());
            evalRoleScore.setRoleId(roleId);
            evalRoleScore.setScore(sum);
            evalRoleScore.setRoleGroup(role);
            addList.add(evalRoleScore);
        }
    }


    @Async
    @Override
    public void reRun(EvalRecordAddReq requestData, EvalRecord evalRecord, Integer originalStatus, Integer finalScore, Integer score, Integer indexScore) {
        try{

            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // 处理异常
        }
        EvalResult evalResult = new EvalResult();
        evalResult.setRecord(requestData.getResourceId());
        evalResult.setWeight(score);
        evalResult.setIndexScore(indexScore);
        evalResult.setFinalScore(finalScore);
        evalResult.setType(EvalResult.Type.SYSTEM_RUN.getCode());

        evalRecord.setIndexScore(requestData.getIndexScore());
        evalRecord.setStatus(originalStatus);
        evalRecord.setFinalScore(finalScore);
        evalRecord.setIndexScore(indexScore);
        evalRecord.setScore(score);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                evalRecordMapper.updateById(evalRecord);
                evalResultMapper.insert(evalResult);
            }
        });
    }

    private void startVariables(EvalRecord evalRecord, String mpSceneId, Set<String> variableIds) {
//        if (variableIds.isEmpty()) {
//            EvalLog.info(evalRecord, "variables id is empty, continue.");
//            return;
//        }
        List<EvalVariableRef> byRecordId = evalVariableRefMapper.getByRecordId(evalRecord.getResourceId());
        List<String> column = StreamApiUtils.getColumn(byRecordId, EvalVariableRef::getResourceId);
        List<EvalVariableRef> evalVariableRefList = evalVariableRefService.lambdaQuery().in(EvalVariableRef::getResourceId, column).ne(EvalVariableRef::getProgress, EvalVariableRef.Progress.NOT_START.getCode()).ne(EvalVariableRef::getProgress, EvalVariableRef.Progress.END.getCode()).list();

        // 运行中变量项为空, 退出执行下一步
        if (evalVariableRefList.isEmpty()) {
            EvalLog.info(evalRecord, "variables has been calculated, continue.");
            return;
        }

        List<String> usingAlgorithmIds = Lists.newArrayList();
        evalVariableRefList.forEach(evalVariableRef -> {
            usingAlgorithmIds.add(evalVariableRef.getFormatAlgorithmId());
            usingAlgorithmIds.add(evalVariableRef.getMatchAlgorithmId());
            usingAlgorithmIds.add(evalVariableRef.getMarkAlgorithmId());
        });
        Map<String, EvalAlgorithmRef> evalAlgorithmRefMap = evalAlgorithmRefService.lambdaQuery().in(EvalAlgorithmRef::getResourceId, usingAlgorithmIds).list().stream().collect(Collectors.toMap(EvalAlgorithmRef::getResourceId, evalAlgorithmRef -> evalAlgorithmRef));

        evalVariableRefList.forEach(evalVariableRef -> {
            // 采集变量项算法依次执行
            EvalLog.info(evalRecord, evalVariableRef, "start calculate...");
            String result = evalVariableRefService.startVariableAlgorithm(evalVariableRef, evalAlgorithmRefMap, evalRecord, mpSceneId);

            // 非null返回值为采集变量项运行失败
            if (!StringUtils.isEmpty(result)) {
                evalVariableRef.setScore(0);
                evalVariableRef.setProgress(EvalVariableRef.Progress.EXCEPTION.getCode());
                evalRecord.setMessage(evalRecord.getMessage() + result + "\n");
                transactionTemplate.execute(status -> {
                    evalVariableRefService.updateById(evalVariableRef);
                    return updateById(evalRecord);
                });
            }
        });

    }

    private void startIndexes(EvalRecord evalRecord) {

        // 体系下指标项
        List<EvalIndexRef> evalIndexRefs = evalIndexRefService.lambdaQuery().eq(EvalIndexRef::getRecordId, evalRecord.getResourceId()).list();
        // 由"level"字段倒序循环计算, 确保指标项子节点"score"一定不为null
        evalIndexRefs.sort(Comparator.comparing(EvalIndexRef::getLevel).reversed());
        Map<String, EvalIndexRef> evalIndexRefMap = evalIndexRefs.stream().collect(Collectors.toMap(EvalIndexRef::getResourceId, evalIndexRef -> evalIndexRef));

        // 指标项使用的算法
        List<String> usingAlgorithmIds = evalIndexRefs.stream().map(EvalIndexRef::getAlgorithmId).collect(Collectors.toList());
        Map<String, EvalAlgorithmRef> evalAlgorithmRefMap = evalAlgorithmRefService.lambdaQuery().in(EvalAlgorithmRef::getResourceId, usingAlgorithmIds).list().stream().collect(Collectors.toMap(EvalAlgorithmRef::getResourceId, evalAlgorithmRef -> evalAlgorithmRef));

        // 四级指标使用的变量项
        List<EvalVariableRef> evalVariableRefList = evalVariableRefService.lambdaQuery().eq(EvalVariableRef::getRecordId, evalRecord.getResourceId()).list();
        Double avg = evalVariableRefList.stream().collect(averagingInt(EvalVariableRef::getScore));

        Map<String, EvalVariableRef> evalVariableRefMap = evalVariableRefList.stream().collect(Collectors.toMap(EvalVariableRef::getResourceId, evalVariableRef -> evalVariableRef));

        evalIndexRefs.forEach(evalIndexRef -> {
            EvalAlgorithmRef evalAlgorithmRef = evalAlgorithmRefMap.get(evalIndexRef.getAlgorithmId());
            // 仅执行系统评分类型, 专家评分拥有默认分值不需执行
            if (evalIndexRef.getType().equals(EvalIndexRef.Type.SYSTEM.getCode())) {
                EvalLog.info(evalRecord, evalIndexRef, "system evaluation type index start...");
                evalIndexRefService.startIndex(evalIndexRef, evalAlgorithmRef, evalIndexRefMap, evalVariableRefMap, evalRecord);
            } else {
                EvalLog.info(evalRecord, evalIndexRef, "specialist evaluation type index, continue.");
            }
        });

        evalRecord.setStatus(StringUtils.isEmpty(evalRecord.getMessage()) ? EvalRecord.Status.END.getCode() : EvalRecord.Status.EXCEPTION.getCode());

        if (avg > 0) {
            evalRecord.setFinalScore((int) (Math.round(avg * evalRecord.getIndexScore()) / 100));
            evalRecord.setScore((int) (Math.round(avg)));
        } else {
            evalRecord.setFinalScore(null == evalRecord.getScore() ? 0 : Math.round(evalRecord.getScore() * evalRecord.getIndexScore()) / 100);
            evalRecord.setScore(evalIndexRefs.get(evalIndexRefs.size() - 1).getScore());
        }
        evalRecord.setEvalEndTime(new Date());
        transactionTemplate.execute(status -> {
            evalIndexRefService.updateBatchById(evalIndexRefs);
            return this.getBaseMapper().updateById(evalRecord);
        });
    }

    private void syncProbeData(EvalRecord evalRecord, Set<String> variableIds, String mpSceneId) {
//        if (variableIds.isEmpty()) {
//            EvalLog.info(evalRecord, "variables id is empty, continue.");
//            return;
//        }

        // 获取需要同步数据的变量项
        List<EvalVariableRef> byRecordId = evalVariableRefMapper.getByRecordId(evalRecord.getResourceId());
        List<String> column = StreamApiUtils.getColumn(byRecordId, EvalVariableRef::getResourceId);
        List<EvalVariableRef> evalVariableRefs = evalVariableRefService.lambdaQuery().in(EvalVariableRef::getResourceId, column).in(EvalVariableRef::getProgress, Arrays.asList(EvalVariableRef.Progress.NOT_START.getCode(), EvalVariableRef.Progress.START.getCode())).list();

        // 没有需要同步数据变量项, 跳过
        if (evalVariableRefs.isEmpty()) {
            EvalLog.info(evalRecord, "variables probe data has been sync, continue.");
            return;
        }

        // 终端节点IP <nodeId, nodeIp>
        Map<String, String> ipMap = nodeIpMap(mpSceneId);

        // 循环变量项, invoke mp, 同步采集数据到bc数据库
        evalVariableRefs.forEach(evalVariableRef -> {
            // 变量项开始执行
            if (evalVariableRef.getProgress().equals(EvalVariableRef.Progress.NOT_START.getCode())) {
                EvalLog.info(evalRecord, evalVariableRef, "probe data sync start.");
                evalVariableRef.setProgress(EvalVariableRef.Progress.START.getCode());
                evalVariableRefService.updateById(evalVariableRef);
            }

            EvalLog.info(evalRecord, evalVariableRef, String.format("used nodeId %s probeId %s.", evalVariableRef.getDeviceId(), evalVariableRef.getProbeId()));

            // 变量项使用同一探针, 数据已经被同步, 跳过
            CollectionEventPageContext collectionEventPageContext = new CollectionEventPageContext();
            collectionEventPageContext.setId(mpSceneId);
            collectionEventPageContext.setNodeIds(evalVariableRef.getDeviceId());
            collectionEventPageContext.setProbeId(evalVariableRef.getProbeId());
            collectionEventPageContext.setRequestId(IDGeneratorUtil.getID());

            // 查询当前已同步数据量, 生成页码
            int probeDataCount = evalProbeDataService.lambdaQuery().eq(EvalProbeData::getSceneId, mpSceneId).eq(EvalProbeData::getDeviceId, evalVariableRef.getDeviceId()).eq(EvalProbeData::getProbeId, evalVariableRef.getProbeId()).count();
            if (!SqlHelper.retBool(probeDataCount)) {
                collectionEventPageContext.setPage(1L);
            } else {
                int scale = -(Integer.toString(Math.toIntExact(onceProbeDataSize)).length() - 1);
                // 数据量向上取整, 生成当前页码
                int count = new BigDecimal(probeDataCount).setScale(scale, BigDecimal.ROUND_UP).intValue();
                collectionEventPageContext.setPage((count / onceProbeDataSize) + 1);
            }
            collectionEventPageContext.setPageSize(onceProbeDataSize);

            try {
                exeCollectionEvent(evalRecord, evalVariableRef, collectionEventPageContext, ipMap, true);

                evalVariableRef.setProgress(EvalVariableRef.Progress.FORMATTING.getCode());
                EvalLog.info(evalRecord, evalVariableRef, "sync data completed.");
            } catch (RemoteServiceException e) {
                EvalLog.error(evalRecord, evalVariableRef, "invoke mp probe data api Exception.");
                // 采集数据报错, 采集变量项中止
                String msg = String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.GET_PROBE_DATA_FAILED), evalVariableRef.getName());
                evalRecord.setMessage(evalRecord.getMessage() + msg + "\n");
                updateById(evalRecord);

                evalVariableRef.setProgress(EvalVariableRef.Progress.END.getCode());
                evalVariableRef.setScore(0);
            }
            evalVariableRefService.updateById(evalVariableRef);

        });
    }

    private void exeCollectionEvent(EvalRecord evalRecord, EvalVariableRef evalVariableRef, CollectionEventPageContext collectionEventPageContext, Map<String, String> ipMap, Boolean isFirstCall) {

        List<EvalProbeData> evalProbeDataList = Lists.newArrayList();

        EvalLog.info(evalRecord, evalVariableRef, String.format("params: %s", collectionEventPageContext));

        List<MpCollectionEvent> collectionEventList = Lists.newArrayList();
        // 第一次、非迭代调用, 并且分页不为第一页. 视作数据同步过程中断
        if (isFirstCall && !collectionEventPageContext.getPage().equals(1L)) {
            // es需要循环数次跳过已同步数据
            if (evalVariableRefService.ES_PROBES.contains(collectionEventPageContext.getProbeId())) {
                EvalLog.info(evalRecord, evalVariableRef, "synced data skipping...");
                Long currentPage = collectionEventPageContext.getPage();
                collectionEventPageContext.setPage(1L);  // es同requestId第一次查询生成快照时传入page需要为1, 否则报错
                for (int i = 0; i < currentPage; i++) {
                    collectionEventList = remoteSceneService.getCollectionEvent(collectionEventPageContext);
                }
                collectionEventPageContext.setPage(currentPage);
                // 非es查询的探针类型, 根据分页入参`page`、`per_page`获取数据
            } else {
                collectionEventList = remoteSceneService.getCollectionEvent(collectionEventPageContext);
            }
        } else {
            collectionEventList = remoteSceneService.getCollectionEvent(collectionEventPageContext);
        }

        collectionEventList.forEach(mpCollectionEvent -> {
            EvalProbeData probeDataItem = new EvalProbeData();
            probeDataItem.setContent(mpCollectionEvent.getContent());
            probeDataItem.setSceneId(collectionEventPageContext.getId());
            probeDataItem.setDeviceId(mpCollectionEvent.getNodeId());
            probeDataItem.setDeviceIp(ipMap.get(mpCollectionEvent.getNodeId()));
            probeDataItem.setProbeId(mpCollectionEvent.getProbeId());

            JSONObject content = JsonUtil.toJsonObject(probeDataItem.getContent(), JSONObject.class);
            if (content == null) {
                EvalLog.error(evalRecord, evalVariableRef, "collect data field [content] is null.");
                throw new RemoteServiceException();
            }
            // 读取探针采集时间
            if (evalVariableRefService.BASE.contains(mpCollectionEvent.getProbeId())) {
                JSONObject scanAttr = content.getJSONObject("scan");
                probeDataItem.setCollectionTime(scanAttr.getTimestamp("time"));
            } else if (mpCollectionEvent.getProbeId().equals("011")) {
                probeDataItem.setCollectionTime(content.getTimestamp("updateTime"));
            } else {
                probeDataItem.setCollectionTime(content.getTimestamp("time"));
            }

            evalProbeDataList.add(probeDataItem);
        });
        // 数据不为空, 保存
        if (!evalProbeDataList.isEmpty()) {
            evalProbeDataService.saveBatch(evalProbeDataList);
            long size = (collectionEventPageContext.getPage() - 1) * onceProbeDataSize + collectionEventList.size();
            EvalLog.info(evalRecord, evalVariableRef, String.format("sync data size %s ...", size));
        }
        // 实际获取数据与每页数据量大小一致, 视数据未获取完成. 页码+1, 迭代执行
        if (collectionEventPageContext.getPageSize() == collectionEventList.size()) {
            collectionEventPageContext.setPage(collectionEventPageContext.getPage() + 1);
            exeCollectionEvent(evalRecord, evalVariableRef, collectionEventPageContext, ipMap, false);
        }
    }

    /**
     * 获取场景下节点ip
     *
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
        String mpSceneId = getMpSceneId(roomId);

        List<EvalRoomPairRsp> results = Lists.newArrayList();

        JSONObject jsonObject = remoteSceneService.getById(mpSceneId, UserInfoContext.getUserContext().getUserId());
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

    private String getMpSceneId(String roomId) {
        EvalTemplate evalTemplate = evalTemplateService.getById(roomId);
        if(null == evalTemplate){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return rbRoomSceneMapper.getByRbId(evalTemplate.getRedVsBlueId());
    }

    @Override
    public List<EvalRoomPairRsp> getProbes(String roomId, String nodeId) {
        String mpSceneId = getMpSceneId(roomId);

        List<EvalRoomPairRsp> results = Lists.newArrayList();

        JSONObject jsonObject = remoteSceneService.getById(mpSceneId, UserInfoContext.getUserContext().getUserId());
        JSONObject cpCollection = jsonObject.getJSONObject("scene").getJSONObject("plugins").getJSONObject("cpcollection");
        if (cpCollection == null) {
            return results;
        }
        JSONObject nodesProbe = cpCollection.getJSONObject("elements");
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
        String mpSceneId = getMpSceneId(roomId);

        JSONObject jsonObject = remoteSceneService.getById(mpSceneId, UserInfoContext.getUserContext().getUserId());
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
        String mpSceneId = getMpSceneId(roomId);

        JSONObject jsonObject = remoteSceneService.getById(mpSceneId, UserInfoContext.getUserContext().getUserId());

        JSONArray nodes = jsonObject.getJSONObject("scene").getJSONObject("topology").getJSONArray("elements");
        JSONObject cpCollection = jsonObject.getJSONObject("scene").getJSONObject("plugins").getJSONObject("cpcollection");
        JSONObject nodesProbe;
        if (cpCollection == null) {
            nodesProbe = new JSONObject();
        } else {
            nodesProbe = cpCollection.getJSONObject("elements");
        }

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
        EvalRecord evalRecord = getById(recordId);
        BeanUtils.copyProperties(evalRecord, result);
        result.setGradeList(JSON.parseArray(evalRecord.getGrade(),Integer.class));
        List<EvalIndexRef> evalIndexRefs = evalIndexRefService.lambdaQuery().eq(EvalIndexRef::getRecordId, recordId).list();
        evalIndexRefs.sort(Comparator.comparing(EvalIndexRef::getLevel));

        Map<String, EvalIndexRef> evalIndexRefMap = evalIndexRefs.stream().collect(Collectors.toMap(EvalIndexRef::getResourceId, evalIndexRef -> evalIndexRef));
        Map<String, EvalVariableRef> evalVariableRefMap = evalVariableRefService.lambdaQuery().eq(EvalVariableRef::getRecordId, recordId).list().stream().collect(Collectors.toMap(EvalVariableRef::getResourceId, evalVariableRef -> evalVariableRef));

        result.setIndexes(Lists.newArrayList());
        for (EvalIndexRef evalIndexRef : evalIndexRefs) {
            if (evalIndexRef.getLevel().equals(EvalIndex.Level.FIRST.getCode())) {
                result.getIndexes().add(generateNextIndexes(evalIndexRef, evalIndexRefMap, evalVariableRefMap, true));
            } else {
                break;
            }
        }
        if (!StringUtils.isEmpty(result.getMessage())) {
            List<String> msgNow = Lists.newArrayList();
            List<String> msgList = Arrays.asList(result.getMessage().split("\n"));
            for (int i = 0; i < msgList.size(); i++) {
                msgNow.add(String.format("%s. %s", i + 1, msgList.get(i)));
            }
            result.setMessage(StringUtils.join(msgNow, "\n"));
        }

        List<Integer> gradeList = JSON.parseArray(evalRecord.getGrade(), Integer.class);
        Integer gradeScore = gradeList.stream().reduce(0, Integer::sum);
        String gradeSr = gradeScore >= 20 ? "高" : gradeScore >= 15 ? "中" : "低";
        result.setGradeStr(gradeSr);
        return result;
    }

    @Override
    public List<EvalRoomIntPairRsp> recordStatusEnum() {

        return crConstantsService.getByType(CrConstantsConfig.ConstantsType.EVAL_STATUS.getCode()).stream().map(item -> {
            EvalRoomIntPairRsp result = new EvalRoomIntPairRsp();
            result.setLabel(item.selectI18nName());
            result.setId(item.getCode());
            return result;
        }).collect(Collectors.toList());
    }

    @Override
    public String dumpEvalRecordPath(String resourceId, String taskId) {
        String destPdfPath;
        JSONObject data = getEvalRecordJsonObject(resourceId, taskId);
        String destHtmlPath = String.format("%s%s-%s.html",
                AttachmentPath.EVAL_HTML_TEMPLATE.getDesc(), resourceId,
                UserInfoContext.getUserContext().getUserId());
        try {
            HtmlToPdfUtil.getHtmlTemplateInstance(data, HtmlTemplateEnum.EVAL, destHtmlPath);
            destPdfPath = destHtmlPath.substring(0, destHtmlPath.lastIndexOf("html"))
                    + "pdf";
            HtmlToPdfUtil.convert(destHtmlPath, destPdfPath);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(!new File(destHtmlPath).delete()){
                log.error("failed to delete file:{}", destHtmlPath);
            }
        }
        return destPdfPath;
    }

    private JSONObject getEvalRecordJsonObject(String resourceId, String taskId) {
        JSONObject data = new JSONObject();

        EvalRecord evalRecord = getById(resourceId);
        String templateId = evalTaskMapper.getTemplateIdByTaskId(evalRecord.getTaskId());
        EvalTemplate template = evalTemplateService.getById(templateId);
        EvalTask evalTask = evalTaskMapper.selectById(taskId);

        if (StringUtils.isEmpty(evalRecord.getTaskId()) || StringUtils.isEmpty(templateId)) {
            throw new BusinessException(CrBusinessErrorCode.TASK_NOT_EXIST_ERROR);
        }
        data.put("title", getTitle(template.getName()));
        JSONObject topoData = localSceneService.getTopoData(template.getTopoId());
        String topoLogo = topoData.getString("logo");
        if (StringUtils.isEmpty(topoLogo)) {
            throw new BusinessException(CrBusinessErrorCode.LOGO_NOT_EXIST);
        }
        data.put("topoLogo", remoteServerEnvService.getFileServerHost() + topoLogo);
        //设置测试基本信息
        data.put("taskName", evalTask.getTaskName());
        data.put("name", evalRecord.getName());
        data.put("templateName", template.getName());
        data.put("recordDescription", evalRecord.getDescription());
        data.put("templateDescription",template.getDescription());
        data.put("score", evalRecord.getScore());
        data.put("endTime", evalRecord.getEndTime());
        data.put("evalTime", evalRecord.getEvalTime());
        data.put("startTime", evalRecord.getStartTime());
        data.put("enableRecording", changeRecordStatus(template.getEnableRecording()));
        List<CrConstantsConfig> constants = crConstantsService.getByType(4);
        List<CodeNameRsp> rsp = StreamApiUtils.map(constants,
                constant -> new CodeNameRsp(constant.getCode(), constant.selectI18nName()));
        if (CollectionUtils.isNotEmpty(rsp)) {
            for (CodeNameRsp codeNameRsp : rsp) {
                if (codeNameRsp.getCode().equals(template.getCategoryCode())) {
                    data.put("categoryCode", codeNameRsp.getName());
                }
            }
        }
        //测试步骤
        data.put("steps", evalTaskStepService.getTaskSteps(taskId));
        //指标体系结构和评估结果数据
        EvalRecordResultRsp result = result(resourceId);
        List<EvalSystemIndexesContext> indexes = result.getIndexes();
        if (CollectionUtils.isNotEmpty(indexes)) {
            String variableId = indexes.get(0).getResourceId();
            data.put("evalSystem", parseGraph(result, variableId));
        }
        List<EvalVariableRef> EvalVariableRefList = evalVariableRefMapper.getByRecordId(resourceId);
        List<EvalCollectionContextRsp> evalCollection = getEvalCollection(EvalVariableRefList);
        //变量项算法
        data.put("evalCollection", evalCollection);
        List<EvalVariableGraphListRsp> evalVariableGraphListRspList = new ArrayList<>();
        List<String> variableIdByRecordId = evalVariableRefMapper.getVariableIdByRecordId(resourceId);
        for (String variableId : variableIdByRecordId) {
            evalVariableGraphListRspList.add(changeGraph(variableId));
        }
        //折线图
        data.put("evalCollectionLine", evalVariableGraphListRspList);
        //添加词条
        data.put("locale", getTemplateI18n());
        return data;
    }

    private List<EvalCollectionContextRsp> getEvalCollection(List<EvalVariableRef> EvalVariableRefList) {
        List<EvalCollectionContextRsp> evalCollectionContextList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(EvalVariableRefList)) {
            evalCollectionContextList = EvalVariableRefList.stream().map(EvalVariableRef -> {
                EvalCollectionContextRsp evalCollectionContextRsp = new EvalCollectionContextRsp();
                evalCollectionContextRsp.setResourceId(EvalVariableRef.getResourceId());
                evalCollectionContextRsp.setName(EvalVariableRef.getName());
                evalCollectionContextRsp.setDetectionAlgorithmId(EvalVariableRef.getMatchAlgorithmId());
                evalCollectionContextRsp.setFormatAlgorithmId(EvalVariableRef.getFormatAlgorithmId());
                evalCollectionContextRsp.setScoreAlgorithmId(EvalVariableRef.getMarkAlgorithmId());
                evalCollectionContextRsp.setDetectionAlgorithmName(evalAlgorithmRefService.getById(EvalVariableRef.getMatchAlgorithmId()).getName());
                evalCollectionContextRsp.setFormatAlgorithmName(evalAlgorithmRefService.getById(EvalVariableRef.getFormatAlgorithmId()).getName());
                evalCollectionContextRsp.setScoreAlgorithmName(evalAlgorithmRefService.getById(EvalVariableRef.getMarkAlgorithmId()).getName());
                return evalCollectionContextRsp;
            }).collect(Collectors.toList());
        }
        return evalCollectionContextList;
    }

    private JSONObject getTemplateI18n() {
        JSONObject jsonObject = new JSONObject();

        for (TrainingReport.TemplateI18n enumData : TrainingReport.TemplateI18n.values()) {
            jsonObject.put(enumData.name(), enumData.getMsg());
        }

        return jsonObject;
    }

    private String getTitle(String name) {

        if (!CommonContext.isChinese()) {
            return "Cyber Exercise Report of " + name;
        } else {
            return name + "评估报告";
        }
    }

    private EvalRecordResultIndexRsp parseGraph(EvalRecordResultRsp result, String variableId){
        List<EvalSystemIndexesContext> indexes = result.getIndexes();
        EvalRecordResultIndexRsp evalRecordResultIndexRsp = new EvalRecordResultIndexRsp();
        if (CollectionUtils.isNotEmpty(indexes)) {
            evalRecordResultIndexRsp = evalIndexRefService.resultIndex(variableId);
            getOtherResult(evalRecordResultIndexRsp);
            setParentId(evalRecordResultIndexRsp);
        }
        return  evalRecordResultIndexRsp;
    }

    private void getOtherResult(EvalRecordResultIndexRsp evalRecordResultIndexRsp){
        List<EvalRecordResultIndexRsp> children = evalRecordResultIndexRsp.getChildren();
        if (CollectionUtils.isNotEmpty(children)) {
            for (EvalRecordResultIndexRsp child : children) {
                if (child.getLevel().equals(EvalIndex.Level.THIRD.getCode())) {
                    EvalRecordResultIndexRsp childEvalRecordResultIndexRsp = evalIndexRefService.resultIndex(child.getResourceId());
                    child.setChildren(childEvalRecordResultIndexRsp.getChildren());
                }else {
                    getOtherResult(child);
                }
            }
        }
    }

    private void setParentId(EvalRecordResultIndexRsp evalRecordResultIndexRsp) {
        if (StringUtils.isNotEmpty(evalRecordResultIndexRsp.getNextIds())) {
            List<EvalRecordResultIndexRsp> children = evalRecordResultIndexRsp.getChildren();
            String resourceId = evalRecordResultIndexRsp.getResourceId();
            String algorithmId = evalRecordResultIndexRsp.getAlgorithmId();
            if (StringUtils.isNotEmpty(algorithmId)) {
                EvalAlgorithm evalAlgorithm = evalAlgorithmService.getById(algorithmId);
                if (evalAlgorithm != null) {
                    evalRecordResultIndexRsp.setAlgorithmName(evalAlgorithm.getName());
                }
            }
            for (EvalRecordResultIndexRsp evalRecordResultIndex : children) {
                evalRecordResultIndex.setParentId(resourceId);
                setParentId(evalRecordResultIndex);
            }
        }
    }

    private EvalVariableGraphListRsp changeGraph(String variableId){
        EvalVariableGraphListRsp result = new EvalVariableGraphListRsp();
        result.setTime(Lists.newArrayList());
        result.setValue(Lists.newArrayList());
        result.setIsMatched(Lists.newArrayList());

        List<EvalVariableRefProbeData> evalVariableRefProbeDataList = evalVariableRefProbeDataService.lambdaQuery()
                .eq(EvalVariableRefProbeData::getVariableId, variableId).list();
        evalVariableRefProbeDataList.sort(Comparator.comparing(EvalVariableRefProbeData::getTime));
        result.setVariableName(evalVariableRefService.getById(variableId).getName());
        evalVariableRefProbeDataList.forEach(item -> {
            result.setVariableId(item.getVariableId());
            result.getTime().add(item.getTime());
            // todo 仅判断一次
            if (StringUtils.isNumeric(item.getFormattedValue())) {
                result.getValue().add(Integer.parseInt(item.getFormattedValue()));
                result.getIsMatched().add(item.getStatus().equals(EvalVariableRefProbeData.Status.MATCHED.getCode()));
            } else {
                boolean isMatched = item.getStatus().equals(EvalVariableRefProbeData.Status.MATCHED.getCode());
                result.getValue().add(isMatched ? 1 : 0);
                result.getIsMatched().add(isMatched);
            }
        });
        return result;
    }
    private String changeRecordStatus(Boolean enableRecording){
        if (CommonContext.isChinese()) {
            if (enableRecording){
                return CommonConstant.RIGHT;
            }else {
                return CommonConstant.NO;
            }
        }else {
            return String.valueOf(enableRecording);
        }
    }
}
