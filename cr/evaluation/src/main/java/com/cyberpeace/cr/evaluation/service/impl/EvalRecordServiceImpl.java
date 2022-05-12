package com.cyberpeace.cr.evaluation.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordRsp;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalRecordMapper;
import com.cyberpeace.cr.evaluation.service.*;
import com.cyberpeace.cr.evaluation.service.context.EvalRecordVariableContext;
import com.cyberpeace.cr.evaluation.service.context.EvalSystemIndexesContext;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 获取实例对象, 检查返回值有效性
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
        Map<String, EvalIndexRef> fullIndexesMap = fullIndexes.stream().collect(Collectors.toMap(EvalIndexRef::getResourceId, evalIndexRef -> evalIndexRef));

        Map<String, EvalVariableRef> fullVariablesMap = evalVariableRefService.lambdaQuery().eq(
                EvalVariableRef::getRecordId, recordRsp.getResourceId()
        ).list().stream().collect(Collectors.toMap(EvalVariableRef::getResourceId, evalVariableRef -> evalVariableRef));
        fullIndexes.forEach(evalIndexRef -> {
            if (evalIndexRef.getLevel().equals(EvalIndex.Level.FIRST.getCode())) {
                recordRsp.getIndexes().add(generateNextIndexes(evalIndexRef, fullIndexesMap, fullVariablesMap));
            }
        });
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
        requestData.getIndexes().forEach(evalRecordIndexContext -> {
            thirdIndexesTypeMap.put(evalRecordIndexContext.getResourceId(), evalRecordIndexContext.getType());
            if (evalRecordIndexContext.getType().equals(EvalIndexRef.Type.SYSTEM.getCode())) {
                evalRecordIndexContext.getVariables().forEach(
                        evalRecordVariableContext -> evalRecordVariableContextMap.put(evalRecordVariableContext.getVariableId(), evalRecordVariableContext)
                );
            }
        });
        List<EvalCollection> evalCollections = evalCollectionService.lambdaQuery().in(
                EvalCollection::getResourceId, evalRecordVariableContextMap.keySet()
        ).list();
        if (evalCollections.size() != evalRecordVariableContextMap.keySet().size()) {
            throw new BusinessException();
        }

        // 生成变量项数据（五级）
        List<EvalVariableRef> variableRefs = Lists.newArrayList();
        Map<String, String> variableIdsMap = Maps.newHashMap();
        evalCollections.forEach(evalCollection -> {
            EvalVariableRef evalVariableRef = new EvalVariableRef();
            evalVariableRef.setResourceId(IDGeneratorUtil.getID());
            evalVariableRef.setRecordId(evalRecord.getResourceId());
            evalVariableRef.setName(evalCollection.getName());
            evalVariableRef.setOriginVariableId(evalCollection.getResourceId());
            evalVariableRef.setProgress(EvalVariableRef.Progress.NOT_START.getCode());

            EvalRecordVariableContext evalRecordVariableContext = evalRecordVariableContextMap.get(evalCollection.getResourceId());
            evalVariableRef.setProbeId(evalRecordVariableContext.getProbeId());
            evalVariableRef.setDeviceId(evalRecordVariableContext.getDeviceId());

            variableRefs.add(evalVariableRef);
            variableIdsMap.put(evalCollection.getResourceId(), evalVariableRef.getResourceId());
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
        Map<String, EvalIndicator> thirdRelationMap = evalIndicatorService.lambdaQuery().in(
                EvalIndicator::getResourceId, evalIndicatorIds
        ).list().stream().collect(Collectors.toMap(EvalIndicator::getResourceId, evalIndicator -> evalIndicator));

        // 生成聚合项数据（四级）
        List<EvalIndexRef> indexRefs = Lists.newArrayList();
        Map<String, String> evalIndexIdsMap = Maps.newHashMap();
        // todo 实际查询到变量项数量校验
        evalAggregationService.lambdaQuery().in(EvalAggregation::getEvalIndicatorId, evalIndicatorIds).list().forEach(evalAggregation -> {
            EvalIndexRef evalIndexRef = new EvalIndexRef();
            evalIndexRef.setResourceId(IDGeneratorUtil.getID());
            evalIndexRef.setRecordId(evalRecord.getResourceId());
            evalIndexRef.setName(evalAggregation.getName());
            evalIndexRef.setType(EvalIndexRef.Type.SYSTEM.getCode());
            evalIndexRef.setLevel(EvalIndexRef.Level.AGGREGATION.getCode());
            evalIndexRef.setAlgorithmId(evalAggregation.getAggregatingAlgorithmId());
            evalIndexRef.setNextIds(JsonUtil.toJsonString(JsonUtil.toJsonObjectArray(
                    evalAggregation.getCollectionIds(), String.class
            ).stream().map(variableIdsMap::get).collect(Collectors.toList())));

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
            evalIndexRef.setAlgorithmId(evalIndex.getAggregatingAlgorithmId());

            String nextIds;
            if (!evalIndex.getLevel().equals(EvalIndex.Level.THIRD.getCode())) {
                nextIds = evalIndex.getChildrenIds();
            } else {
                EvalIndicator evalIndicator = thirdRelationMap.get(evalIndex.getEvalIndicatorId());
                if (evalIndicator == null) {
                    throw new BusinessException();
                }
                nextIds = evalIndicator.getAggregationIds();
            }
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
            }
        });

        return get(evalRecord.getResourceId());
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
            evalVariableRef.setResourceId(evalRecordVariableContext.getVariableId());
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
        evalSystemIndexesContext.setNextLevel(Lists.newArrayList());
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
                    evalSystemIndexesContext.getNextLevel().add(BeanUtils.copy(evalVariableRef, EvalSystemIndexesContext.class));
                    variablesMap.add(variableId);
                });
            } else {
                evalSystemIndexesContext.getNextLevel().add(generateNextIndexes(evalIndexRef1, fullIndexesMap, fullVariablesMap, isFull));
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
    public void startRecord(String recordId) {

    }

    @Override
    public void startVariables(List<String> variableIds) {

    }
}
