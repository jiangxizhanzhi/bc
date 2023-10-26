package com.cyberpeace.cr.evaluation.service.impl;


import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.HtmlTemplateEnum;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.crbase.service.context.TrainingReport;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.*;
import com.cyberpeace.cr.evaluation.service.*;
import com.cyberpeace.cr.evaluation.service.context.EvalRecordSpecialistContext;
import com.cyberpeace.cr.evaluation.service.context.EvalRecordVariableContext;
import com.cyberpeace.cr.evaluation.service.context.EvalSystemIndexesContext;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.mp.MpCollectionEvent;
import com.cyberpeace.integratation.model.scene.CollectionEventPageContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.impl.RemoteServerEnvService;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EvalRecordServiceImpl extends ServiceImpl<EvalRecordMapper, EvalRecord> implements EvalRecordService {

	/**
	 * 时间字段获取存在差异的探针
	 */
	private static final List<String> BASE = Arrays.asList("002", "003");

	/**
	 * 由es查询的探针数据, 无法使用`page`、`per_page`分页, 需要传入`requestId`、`per_page`迭代获取
	 */
	private static final List<String> ES_PROBES = Arrays.asList("006", "007", "008", "009", "010");

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

    @Autowired
    private EvalCollectionMapper evalCollectionMapper;

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
    private EvalResultMapper evalResultMapper;

    @Autowired
    private EvalRecordMapper evalRecordMapper;

    @Autowired
    private EvalIndexMapper evalIndexMapper;

    private static final long ONCE_PROBE_DATA_SIZE = 1000L;  // 10/100/1000

    private static final String PROBES = "probes"; // 探针

    private static final String TOPOLOGY = "topology"; // 拓扑

    private static final String CPCOLLECTION = "cpcollection"; // 采集

    private static final String PLUGINS = "plugins"; // 插件

    private static final String SCENE = "scene"; // 场景

    private static final String ELEMENTS = "elements"; // 元素

    @Override
    public void isExist(EvalRecordAddReq requestData) {
        LambdaQueryChainWrapper<EvalRecord> querySet = lambdaQuery().eq(EvalRecord::getTaskId, requestData.getTaskId()).eq(EvalRecord::getName, requestData.getName());

        if (requestData.getResourceId() != null) {
            querySet = querySet.ne(EvalRecord::getResourceId, requestData.getResourceId());
        }

        if (SqlHelper.retBool(querySet.count())) {
            log.error("Name duplicate! requestData = [{}]", JsonUtil.toJsonString(requestData));
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
            log.error("The evaluation task does not exist! taskId = [{}]", taskId);
            throw new BusinessException(CrBusinessErrorCode.EVAL_TASK_NOT_EXIST);
        }

        // 校验用户权限
        EvalTemplate evalTemplate = evalTemplateService.getById(evalTask.getTemplateId());
        Integer operationAuthType = evalTemplate.getOperationAuthType();
        String userId = UserInfoContext.getUserContext().getUserId();
        if (!Objects.equals(evalTask.getCreateUserId(), userId) && operationAuthType.equals(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode()) && !evalTemplate.getOperationAuthIds().contains(userId) && !evalTemplate.getCreateUserId().equals(userId)) {
            log.error("Permission denied! CreateUserId = [{}], UserId = [{}]", evalTask.getCreateUserId(), userId);
            throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
        }

        return evalTask;
    }

    @Override
    public EvalRecord getEntity(String resourceId) {
        // 非法请求
        EvalRecord evalRecord = this.getBaseMapper().selectById(resourceId);
        if (evalRecord == null) {
            log.error("Evaluation not found! resourceId = [{}]", resourceId);
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        if (!evalRecord.getStatus().equals(EvalRecord.Status.NOT_START.getCode())) {
            log.error("Evaluation has been initiated! resourceId = [{}]", resourceId);
            throw new BusinessException(CrBusinessErrorCode.EVALUATION_HAS_BEEN_INITIATED_ERROR);
        }
        return evalRecord;
    }

    @Override
    public ListRsp<EvalRecordListRsp> pageList(Page page, String taskId, Integer status, String pattern) {
        return PageUtil.getListResult(this.getBaseMapper().pageList(page, taskId, status, pattern));
    }

    @Mapper
    public interface EvalRecordToEvalRecordRsp extends BaseConvertor<EvalRecord, EvalRecordRsp> {
    }

    public static final EvalRecordToEvalRecordRsp evalRecordToEvalRecordRsp = Mappers.getMapper(EvalRecordToEvalRecordRsp.class);

    @Override
    public EvalRecordRsp get(String recordId) {
        EvalRecord evalRecord = getEntity(recordId);

        EvalRecordRsp recordRsp = evalRecordToEvalRecordRsp.from(evalRecord);
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
        Integer status = evalRecord.getStatus();
        recordRsp.setRunStatus(status >= EvalRecord.Status.END.getCode() ? EvalRecord.Status.END.getCode() : status);
        return recordRsp;
    }

    @Mapper
    public interface EvalRecordAddReqToEvalRecord extends BaseConvertor<EvalRecordAddReq, EvalRecord> {
    }

    public static final EvalRecordAddReqToEvalRecord evalRecordAddReqToEvalRecord = Mappers.getMapper(EvalRecordAddReqToEvalRecord.class);


    @Override
    public EvalRecordRsp create(EvalRecordAddReq requestData) {
        // 评估对象构建
        EvalRecord evalRecord = evalRecordAddReqToEvalRecord.from(requestData);
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
        // 4级指标 联5级id有重复时 更新5级id
        List<String> collections = new ArrayList<>();
        // 五级指标
        List<EvalCollection> evalCollections = !evalRecordVariableContextMap.isEmpty() ? evalCollectionService.lambdaQuery().in(EvalCollection::getResourceId, evalRecordVariableContextMap.keySet()).list() : Lists.newArrayList();

        evalAggregations.forEach(evalAggregation -> {
            List<String> collectionIds = JsonUtil.toJsonObjectArray(evalAggregation.getCollectionIds(), String.class);
            collectionIds.forEach(collectionId -> {
                String matrixData = evalAggregation.getMatrixData();
                if (!collections.contains(collectionId)) {
                    collections.add(collectionId);
                } else {
                    String id = IDGeneratorUtil.getID();
                    EvalCollection evalCollection = evalCollectionMapper.selectById(collectionId);
                    evalCollection.setResourceId(id);
                    evalCollections.add(evalCollection);
                    collectionIds.set(collectionIds.indexOf(collectionId), id);
                    EvalRecordVariableContext evalRecordVariableContext = evalRecordVariableContextMap.get(collectionId);
                    evalRecordVariableContextMap.put(id, evalRecordVariableContext);
                    if (StringUtils.isNotBlank(matrixData)) {
                        String currentMatrixData = matrixData.replaceAll(collectionId, id);
                        evalAggregation.setMatrixData(currentMatrixData);
                    }
                }
            });
            String currentCollectionIds = JsonUtil.arrayToString(collectionIds);
            evalAggregation.setCollectionIds(currentCollectionIds);
        });

        // 取消校验 --请求体变量项数量与db获取体系下变量项数量一致

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
            // 新增5级默认评分方式
            evalVariableRef.setType(EvalIndexRef.Type.SYSTEM.getCode());

            // 构建变量项新的算法数据 - 格式化算法
            String formatAlgorithmId = evalCollection.getFormatAlgorithmId();
            if (evalAlgorithmMap.containsKey(formatAlgorithmId)) {
                formatAlgorithmId = IDGeneratorUtil.getID();
                evalAlgorithmMap.put(formatAlgorithmId, evalAlgorithmMap.get(evalCollection.getFormatAlgorithmId()));
                evalVariableRef.setFormatAlgorithmId(formatAlgorithmId);
            }
            EvalAlgorithmRef newFormatAlgorithm = generateAlgorithmRef(formatAlgorithmId, evalAlgorithmMap, evalAlgorithmRefMap);
            evalVariableRef.setFormatAlgorithmId(newFormatAlgorithm.getResourceId());
            evalVariableRef.setFormatParams(evalCollection.getFormatAlgorithmParams());

            // 匹配算法
            String detectionAlgorithmId = evalCollection.getDetectionAlgorithmId();
            if (evalAlgorithmMap.containsKey(detectionAlgorithmId)) {
                detectionAlgorithmId = IDGeneratorUtil.getID();
                evalAlgorithmMap.put(detectionAlgorithmId, evalAlgorithmMap.get(evalCollection.getDetectionAlgorithmId()));
                evalVariableRef.setMatchAlgorithmId(detectionAlgorithmId);
            }
            EvalAlgorithmRef newMatchAlgorithm = generateAlgorithmRef(detectionAlgorithmId, evalAlgorithmMap, evalAlgorithmRefMap);
            evalVariableRef.setMatchAlgorithmId(newMatchAlgorithm.getResourceId());
            evalVariableRef.setMatchParams(evalCollection.getDetectionAlgorithmParams());

            // 评分算法
            String scoreAlgorithmId = evalCollection.getScoreAlgorithmId();
            if (evalAlgorithmMap.containsKey(scoreAlgorithmId)) {
                scoreAlgorithmId = IDGeneratorUtil.getID();
                evalAlgorithmMap.put(scoreAlgorithmId, evalAlgorithmMap.get(evalCollection.getScoreAlgorithmId()));
                evalVariableRef.setMarkAlgorithmId(scoreAlgorithmId);
            }
            EvalAlgorithmRef newMarkAlgorithm = generateAlgorithmRef(scoreAlgorithmId, evalAlgorithmMap, evalAlgorithmRefMap);
            evalVariableRef.setMarkAlgorithmId(newMarkAlgorithm.getResourceId());
            evalVariableRef.setMarkParams(evalCollection.getScoreAlgorithmParams());

            // 设置使用节点、探针id
            EvalRecordVariableContext evalRecordVariableContext = evalRecordVariableContextMap.get(evalCollection.getResourceId());
            evalVariableRef.setProbeId(evalRecordVariableContext.getProbeId());
            evalVariableRef.setDeviceId(evalRecordVariableContext.getDeviceId());

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
            String matrixData = evalAggregation.getMatrixData();
            for (String item : JsonUtil.toJsonObjectArray(evalAggregation.getCollectionIds(), String.class)) {
                if (variableIdsMap.containsKey(item)) {
                    String currentId = variableIdsMap.get(item);
                    if (StringUtils.isNotBlank(matrixData) && matrixData.contains(item)) {
                        matrixData = matrixData.replaceAll(item, currentId);
                    }
                    nextIds.add(currentId);
                }
            }
            evalIndexRef.setMatrixData(matrixData);
            evalIndexRef.setNextIds(JsonUtil.toJsonString(nextIds));

            String aggregatingAlgorithmId = evalAggregation.getAggregatingAlgorithmId();
            if (evalAlgorithmMap.containsKey(aggregatingAlgorithmId)) {
                aggregatingAlgorithmId = IDGeneratorUtil.getID();
                evalAlgorithmMap.put(aggregatingAlgorithmId, evalAlgorithmMap.get(evalAggregation.getAggregatingAlgorithmId()));
                evalAggregation.setAggregatingAlgorithmId(aggregatingAlgorithmId);
            }
            EvalAlgorithmRef evalAlgorithmRef = generateAlgorithmRef(aggregatingAlgorithmId, evalAlgorithmMap, evalAlgorithmRefMap);
            evalIndexRef.setAlgorithmParams(evalAggregation.getAggregatingAlgorithmParams());
            evalIndexRef.setAlgorithmId(evalAlgorithmRef.getResourceId());

            indexRefs.add(evalIndexRef);
            evalIndexIdsMap.put(evalAggregation.getResourceId(), evalIndexRef.getResourceId());
        });

        // 前三级指标按照"level"降序排序
        evalIndexList = evalIndexList.stream().sorted(Comparator.comparing(EvalIndex::getLevel).reversed()).collect(Collectors.toList());
        List<EvalIndex> evalIndices = evalIndexMapper.selectList(null);

        Map<String, EvalIndex> evalIndexMap = StreamApiUtils.toMap(evalIndices, EvalIndex::getResourceId, s -> s);
        // 生成一、二、三级指标
        evalIndexList.forEach(evalIndex -> {
            EvalIndexRef evalIndexRef = new EvalIndexRef();
            evalIndexRef.setResourceId(IDGeneratorUtil.getID());
            evalIndexRef.setRecordId(evalRecord.getResourceId());
            evalIndexRef.setName(evalIndex.getName());
            evalIndexRef.setType(thirdIndexesTypeMap.getOrDefault(evalIndex.getResourceId(), EvalIndexRef.Type.SYSTEM.getCode()));
            evalIndexRef.setLevel(evalIndex.getLevel());
            evalIndexRef.setProgress(EvalIndexRef.Progress.NOT_START.getCode());

            // 设置下一级、备份算法信息
            String nextIds;
            EvalAlgorithmRef evalAlgorithmRef;
            if (!evalIndex.getLevel().equals(EvalIndex.Level.THIRD.getCode())) {
                evalIndexRef.setAlgorithmParams(evalIndex.getAggregatingAlgorithmParams());

                nextIds = evalIndex.getChildrenIds();
                String aggregatingAlgorithmId = evalIndex.getAggregatingAlgorithmId();
                if (evalAlgorithmMap.containsKey(aggregatingAlgorithmId)) {
                    aggregatingAlgorithmId = IDGeneratorUtil.getID();
                    evalAlgorithmMap.put(aggregatingAlgorithmId, evalAlgorithmMap.get(evalIndex.getAggregatingAlgorithmId()));
                    evalIndexRef.setAlgorithmId(aggregatingAlgorithmId);
                }
                evalAlgorithmRef = generateAlgorithmRef(aggregatingAlgorithmId, evalAlgorithmMap, evalAlgorithmRefMap);
            } else {
                EvalIndicator evalIndicator = thirdRelationMap.get(evalIndex.getEvalIndicatorId());
                if (evalIndicator == null) {
                    log.error("The three-level index [{}] is invalid!", evalIndex.getName());
                    throw new BusinessException(CrBusinessErrorCode.THIRD_INDEX_IS_FAILED, evalIndex.getName());
                }

                nextIds = evalIndicator.getAggregationIds();
                String aggregatingAlgorithmId = evalIndicator.getAggregatingAlgorithmId();
                if (evalAlgorithmMap.containsKey(aggregatingAlgorithmId)) {
                    aggregatingAlgorithmId = IDGeneratorUtil.getID();
                    evalAlgorithmMap.put(aggregatingAlgorithmId, evalAlgorithmMap.get(evalIndicator.getAggregatingAlgorithmId()));
                    evalIndexRef.setAlgorithmId(aggregatingAlgorithmId);
                }
                evalAlgorithmRef = generateAlgorithmRef(aggregatingAlgorithmId, evalAlgorithmMap, evalAlgorithmRefMap);

                // 专家评分, 设置默认分值
                evalIndexRef.setSpecialistType(evalIndicator.getDatatype());
                evalIndexRef.setSpecialistAttr(evalIndicator.getAttribute());
                evalIndexRef.setSpecialistAttrValue(evalIndicator.getAttributeValue());
                evalIndexRef.setSpecialistValue(thirdSpecialistMap.get(evalIndex.getResourceId()));
                if (evalIndexRef.getType().equals(EvalIndexRef.Type.SPECIALIST.getCode())) {
                    evalIndexRef.setScore(evalIndexRefService.thirdSpecialist(evalIndexRef.getSpecialistType(), evalIndexRef.getSpecialistAttr(), evalIndexRef.getSpecialistAttrValue(), evalIndexRef.getSpecialistValue()));
                    evalIndexRef.setProgress(EvalIndexRef.Progress.END.getCode());
                }
            }

            evalIndexRef.setAlgorithmId(evalAlgorithmRef.getResourceId());

            evalIndexRef.setNextIds(JsonUtil.toJsonString(JsonUtil.toJsonObjectArray(nextIds, String.class).stream().map(evalIndexIdsMap::get).collect(Collectors.toList())));
            List<String> nextIdLists = JsonUtil.toJsonObjectArray(nextIds, String.class);
            String matrixData = evalIndex.getMatrixData();
            if (evalIndex.getLevel().equals(EvalIndex.Level.THIRD.getCode())) {
                matrixData = evalIndicatorService.getById(evalIndex.getEvalIndicatorId()).getMatrixData();
            }
            for (String nextId : nextIdLists) {
                String currentId = evalIndexIdsMap.get(nextId);
                if (StringUtils.isNotBlank(matrixData)) {
                    matrixData = matrixData.replaceAll(nextId, currentId);
                }
            }
            evalIndexRef.setMatrixData(matrixData);
            if (evalIndex.getLevel().equals(EvalIndex.Level.SECOND.getCode()) && StringUtils.isNotBlank(matrixData)) {
                // 三级指标的 indicator id与 三级指标 resource_id 关联
                Map<String, String> tmp = new HashMap<>();
                for (String id : nextIdLists) {
                    EvalIndex index = evalIndexMap.get(id);
                    String evalIndicatorId = index.getEvalIndicatorId();
                    tmp.put(evalIndicatorId, index.getResourceId());
                }
                JSONArray objects = JsonUtil.stringToArray(matrixData);
                // 从上面的map 中获取三级指标的indicator 对应的id 并替换成当前新生成的id
                for (int i = 0; i < objects.size(); i++) {
                    JSONObject object = objects.getJSONObject(i);
                    String key = object.getString("key");
                    String resourceId = tmp.get(key);
                    String currentId = evalIndexIdsMap.get(resourceId);
                    matrixData = matrixData.replaceAll(key, currentId);
                    evalIndexRef.setMatrixData(matrixData);
                }
            }

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

    @Mapper
    public interface EvalAlgorithmToEvalAlgorithmRef extends BaseConvertor<EvalAlgorithm, EvalAlgorithmRef> {

        @IgnoreId
        EvalAlgorithmRef from(EvalAlgorithm source);

        @IgnoreId
        EvalAlgorithm to(EvalAlgorithmRef target);

    }

    public static final EvalAlgorithmToEvalAlgorithmRef evalAlgorithmToEvalAlgorithmRef = Mappers.getMapper(EvalAlgorithmToEvalAlgorithmRef.class);


    private EvalAlgorithmRef generateAlgorithmRef(String algorithmId, Map<String, EvalAlgorithm> originAlgorithmMap, Map<String, EvalAlgorithmRef> evalAlgorithmRefMap) {
        // 取消原逻辑 【已拷贝算法, 不再重复拷贝, evalAlgorithmRefMap获取返回】


        EvalAlgorithm originAlgorithm = originAlgorithmMap.get(algorithmId);
        EvalAlgorithmRef newAlgorithm;
        newAlgorithm = evalAlgorithmToEvalAlgorithmRef.from(originAlgorithm);
        newAlgorithm.setResourceId(IDGeneratorUtil.getID());

        // 拷贝过一次算法, 放入evalAlgorithmRefMap
        evalAlgorithmRefMap.put(algorithmId, newAlgorithm);
        return newAlgorithm;
    }



    @Override
    public EvalRecordRsp update(EvalRecordAddReq requestData) {
        EvalRecord originEvalRecord = getEntity(requestData.getResourceId());

        EvalRecord newEvalRecord = evalRecordAddReqToEvalRecord.from(requestData);
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
                    evalVariableRef = evalRecordVariableContextToEvalVariableRef.from(evalRecordVariableContext);
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

    @Mapper
    public interface EvalRecordVariableContextToEvalVariableRef extends BaseConvertor<EvalRecordVariableContext, EvalVariableRef> {
    }

    public static final EvalRecordVariableContextToEvalVariableRef evalRecordVariableContextToEvalVariableRef = Mappers.getMapper(EvalRecordVariableContextToEvalVariableRef.class);

    @Mapper
    public interface EvalIndexRefToEvalSystemIndexesContext extends BaseConvertor<EvalIndexRef, EvalSystemIndexesContext> {
    }

    public static final EvalIndexRefToEvalSystemIndexesContext evalIndexRefToEvalSystemIndexesContext = Mappers.getMapper(EvalIndexRefToEvalSystemIndexesContext.class);

    @Mapper
    public interface EvalVariableRefToEvalSystemIndexesContext extends BaseConvertor<EvalVariableRef, EvalSystemIndexesContext> {
    }

    public static final EvalVariableRefToEvalSystemIndexesContext evalVariableRefToEvalSystemIndexesContext = Mappers.getMapper(EvalVariableRefToEvalSystemIndexesContext.class);

    private EvalSystemIndexesContext generateNextIndexes(EvalIndexRef evalIndexRef, Map<String, EvalIndexRef> fullIndexesMap, Map<String, EvalVariableRef> fullVariablesMap, Boolean isFull) {
        EvalSystemIndexesContext evalSystemIndexesContext = evalIndexRefToEvalSystemIndexesContext.from(evalIndexRef);
        evalSystemIndexesContext.setChildren(Lists.newArrayList());
        List<String> nextIds = JsonUtil.toJsonObjectArray(evalIndexRef.getNextIds(), String.class);

        // 同三级指标项下变量项过滤
        Set<String> variablesMap = Sets.newHashSet();
        nextIds.forEach(nextId -> {
            evalSystemIndexesContext.setSpecialist(new EvalRecordSpecialistContext());
            evalSystemIndexesContext.getSpecialist().setValue(evalIndexRef.getSpecialistValue());
            evalSystemIndexesContext.getSpecialist().setType(evalIndexRef.getSpecialistType());
            EvalIndexRef evalIndexRef1 = fullIndexesMap.get(nextId);
            // 如果"ifFull"为真，跳过第四级直接由"EvalVariableRef"获取第五级
            Integer condition = Boolean.TRUE.equals(isFull) ? EvalIndexRef.Level.AGGREGATION.getCode() : EvalIndex.Level.THIRD.getCode();
            if (evalIndexRef.getLevel().equals(condition)) {
                List<String> variableIds;
                if (Boolean.TRUE.equals(isFull)) {
                    variableIds = JsonUtil.toJsonObjectArray(evalIndexRef.getNextIds(), String.class);
                } else {
                    variableIds = JsonUtil.toJsonObjectArray(evalIndexRef1.getNextIds(), String.class);
                }
                variableIds.forEach(variableId -> {
                    if (variablesMap.contains(variableId)) {
                        return;
                    }
                    EvalVariableRef evalVariableRef = fullVariablesMap.get(variableId);
                    evalSystemIndexesContext.getChildren().add(evalVariableRefToEvalSystemIndexesContext.from(evalVariableRef));
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
        evalIndexRefService.removeByIds(evalIndexRefService.lambdaQuery().in(EvalIndexRef::getRecordId, recordIds).list().stream().map(EvalIndexRef::getResourceId).collect(Collectors.toList()));

        evalVariableRefService.removeByIds(evalVariableRefService.lambdaQuery().in(EvalVariableRef::getRecordId, recordIds).list().stream().map(EvalVariableRef::getResourceId).collect(Collectors.toList()));
    }

    @Override
    @Async
    public void recordRun(EvalRecord evalRecord, EvalTask evalTask, Boolean isRestart, String language) {
        CommonContext.setLanguage(language);
        EvalLog.info(evalRecord, String.format("Range Test evaluation %s %s.", evalRecord.getName(), Boolean.TRUE.equals(isRestart) ? "RESTART" : "START"));
        if (StringUtils.isEmpty(evalRecord.getMessage())) {
            evalRecord.setMessage("");
        }

        // 需要计算变量项id集合
        EvalLog.info(evalRecord, "collect wait to calculate variables id...");
        Set<String> variableIds = evalIndexRefService.systemEvalVariableIds(evalRecord.getResourceId());
        EvalLog.info(evalRecord, String.format("collect wait to calculate variables id completed: %s", variableIds.toString()));

        // 循环同步采集数据
        EvalLog.info(evalRecord, "sync remote server [MP] probe data start...");
        syncProbeData(evalRecord, evalTask, variableIds);
        EvalLog.info(evalRecord, "sync remote server [MP] probe data completed.");

        // 执行变量项
        EvalLog.info(evalRecord, "calculate variables start...");
        startVariables(evalRecord, evalTask, variableIds);
        EvalLog.info(evalRecord, "calculate variables completed.");

        // 执行指标项
        EvalLog.info(evalRecord, "calculate indexes start...");
        startIndexes(evalRecord);
        EvalLog.info(evalRecord, "calculate indexes completed.");

        // 添加当前执行结果到结果轮次表中
        EvalLog.info(evalRecord, "add indexes res start...");
        addResToEvalResult(evalRecord);
        EvalLog.info(evalRecord, "add indexes res completed.");

        EvalLog.info(
                evalRecord,
                String.format("END, result: %s, cost: %s", evalRecord.getScore(), evalRecord.getEvalEndTime().getTime() - evalRecord.getEvalTime().getTime())
        );
    }

    private void addResToEvalResult(EvalRecord evalRecord) {
        if (evalRecord.getStatus().equals(EvalRecord.Status.EXCEPTION.getCode())) {
            return;
        }
        Integer exeCycle = 1;
        EvalResult evalResult = new EvalResult();
        evalResult.setExeCycle(exeCycle);
        LambdaQueryWrapper<EvalResult> filter = new LambdaQueryWrapper<>();
        String recordResourceId = evalRecord.getResourceId();
        evalResult.setRecord(recordResourceId);
        filter.eq(EvalResult::getRecord, recordResourceId);
        filter.orderByDesc(EvalResult::getExeCycle);
        filter.last("limit 1");
        EvalResult evalResultQuery = evalResultMapper.selectOne(filter);
        evalResult.setExeCycle(exeCycle);
        if (null != evalResultQuery) {
            exeCycle += evalResultQuery.getExeCycle();
            evalResult.setExeCycle(exeCycle);
        }
        // 保存各index_ref id：score 1-4
        LambdaQueryWrapper<EvalIndexRef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EvalIndexRef::getRecordId, recordResourceId);
        List<EvalIndexRef> evalIndexRefs = evalIndexRefService.getBaseMapper().selectList(queryWrapper);

        // 保存variable_ref id:score 5
        LambdaQueryWrapper<EvalVariableRef> query = new LambdaQueryWrapper<>();
        query.eq(EvalVariableRef::getRecordId, evalRecord.getResourceId());
        List<EvalVariableRef> variableRefs = evalVariableRefMapper.selectList(query);

        List<EvalIndexRef> firstIndexes = evalIndexRefs.stream().filter(s -> s.getLevel().equals(EvalIndex.Level.FIRST.getCode())).collect(Collectors.toList());
        List<EvalIndexRef> secondIndexes = evalIndexRefs.stream().filter(s -> s.getLevel().equals(EvalIndex.Level.SECOND.getCode())).collect(Collectors.toList());
        List<EvalIndexRef> thirdIndexes = evalIndexRefs.stream().filter(s -> s.getLevel().equals(EvalIndex.Level.THIRD.getCode())).collect(Collectors.toList());
        List<EvalIndexRef> fourthIndexes = evalIndexRefs.stream().filter(s -> s.getLevel().equals(EvalIndexRef.Level.AGGREGATION.getCode())).collect(Collectors.toList());

        Map<String, EvalIndexRef> secondEvalIndexRefMap = StreamApiUtils.toMap(secondIndexes, EvalIndexRef::getResourceId, s -> s);
        Map<String, EvalIndexRef> thirdEvalIndexRefMap = StreamApiUtils.toMap(thirdIndexes, EvalIndexRef::getResourceId, s -> s);
        Map<String, EvalIndexRef> fourthEvalIndexRefMap = StreamApiUtils.toMap(fourthIndexes, EvalIndexRef::getResourceId, s -> s);
        Map<String, EvalVariableRef> fifthEvalIndexRefMap = StreamApiUtils.toMap(variableRefs, EvalVariableRef::getResourceId, s -> s);

        for (EvalIndexRef first : firstIndexes) {
            String nextIds = first.getNextIds();
            Integer score = first.getScore();
            if (first.getType().equals(EvalIndexRef.Type.SPECIALIST.getCode())) {
                score = first.getSpecialistValue();
            }
            evalResult.setFirstIndexRes(score);
            List<String> nextIdList = JsonUtil.toJsonObjectArray(nextIds, String.class);
            JSONObject secondObj = new JSONObject();
            JSONObject thirdObj = new JSONObject();
            JSONObject fourthObj = new JSONObject();
            JSONObject fifthObj = new JSONObject();

            for (String tmpIndex : nextIdList) {
                EvalIndexRef evalIndexRef = secondEvalIndexRefMap.get(tmpIndex);

                if (null == evalIndexRef) {
                    log.error("addResToEvalResult evalIndexRef is null, tmpIndex is [{}]", tmpIndex);
                    throw new BusinessException(CrBusinessErrorCode.SYSTEM_INDEX_IS_FAILED);
                }
                log.info("addResToEvalResult evalIndexRef tmpIndex is [{}]", tmpIndex);
                secondObj.put(evalIndexRef.getResourceId(), evalIndexRef.getScore());
                String refNextIds = evalIndexRef.getNextIds();
                List<String> nextRedIdList = JsonUtil.toJsonObjectArray(refNextIds, String.class);
                for (String tmpNextIndex : nextRedIdList) {
                    EvalIndexRef indexRef = thirdEvalIndexRefMap.get(tmpNextIndex);
                    if (null == indexRef) {
                        log.error("addResToEvalResult evalIndexRef indexRef is null, tmpNextIndex is [{}]", tmpNextIndex);
                        throw new BusinessException(CrBusinessErrorCode.SYSTEM_INDEX_IS_FAILED);
                    }
                    log.info("addResToEvalResult evalIndexRef tmpNextIndex is [{}]", tmpNextIndex);
                    Integer indexRefScore = indexRef.getScore();
                    thirdObj.put(indexRef.getResourceId(), indexRef.getType().equals(EvalIndexRef.Type.SPECIALIST.getCode()) ? indexRef.getSpecialistValue() : indexRefScore);
                    String ids = indexRef.getNextIds();

                    List<String> idList = JsonUtil.toJsonObjectArray(ids, String.class);
                    for (String currentNextId : idList) {
                        EvalIndexRef current = fourthEvalIndexRefMap.get(currentNextId);
                        if (null == current) {
                            log.error("addResToEvalResult evalIndexRef currentNextId is null, currentNextId is [{}]", currentNextId);
                            throw new BusinessException(CrBusinessErrorCode.SYSTEM_INDEX_IS_FAILED);
                        }
                        log.info("current evalIndexRef name is : [{}], id is [{}], score is [{}]", current.getName(), current.getResourceId(), current.getScore());
                        Integer type = current.getType();
                        Integer currentScore = current.getScore();
                        if (type.equals(EvalIndexRef.Type.SPECIALIST.getCode())) {
                            currentScore = current.getSpecialistValue();
                        }
                        fourthObj.put(current.getResourceId(), currentScore);

                        getFifthObj(fifthEvalIndexRefMap, fifthObj, current);
                    }
                }
            }
            evalResult.setSecondIndexRes(JsonUtil.toJsonString(secondObj));
            evalResult.setThirdIndexRes(JSON.toJSONString(thirdObj, SerializerFeature.WriteMapNullValue));
            evalResult.setFourthIndexRes(JSON.toJSONString(fourthObj, SerializerFeature.WriteMapNullValue));
            evalResult.setFifthIndexRes(JSON.toJSONString(fifthObj, SerializerFeature.WriteMapNullValue));

        }

        evalResultMapper.insert(evalResult);
    }

    private void getFifthObj(Map<String, EvalVariableRef> fifthEvalIndexRefMap, JSONObject fifthObj, EvalIndexRef current) {
        String fifthIds = current.getNextIds();
        List<String> fifthIdsList = JsonUtil.toJsonObjectArray(fifthIds, String.class);
        for (String fifthId : fifthIdsList) {
            EvalVariableRef fifthVariableRef = fifthEvalIndexRefMap.get(fifthId);
            if (null != fifthVariableRef) {
                Integer score = fifthVariableRef.getScore();
                if (fifthVariableRef.getType().equals(EvalIndexRef.Type.SPECIALIST.getCode())) {
                    score = current.getSpecialistValue();
                }
                fifthObj.put(fifthId, score);
            }
        }
    }

    private void startVariables(EvalRecord evalRecord, EvalTask evalTask, Set<String> variableIds) {
        if (variableIds.isEmpty()) {
            EvalLog.info(evalRecord, "variables id is empty, continue.");
            return;
        }

        List<EvalVariableRef> evalVariableRefList = evalVariableRefService.lambdaQuery().in(EvalVariableRef::getResourceId, variableIds).list();

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
            String result = null;
            EvalLog.info(evalRecord, evalVariableRef, "start calculate...");
            if (evalVariableRef.getType().equals(EvalIndexRef.Type.SYSTEM.getCode())) {
                result = evalVariableRefService.startVariableAlgorithm(evalVariableRef, evalAlgorithmRefMap, evalRecord, evalTask);
            }

            // 非null返回值为采集变量项运行失败
            if (!StringUtils.isEmpty(result)) {
                evalVariableRef.setScore(0);
                evalVariableRef.setProgress(EvalVariableRef.Progress.EXCEPTION.getCode());
                evalRecord.setMessage(result + "\n");
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
        Map<String, EvalVariableRef> evalVariableRefMap = evalVariableRefService.lambdaQuery().eq(EvalVariableRef::getRecordId, evalRecord.getResourceId()).list().stream().collect(Collectors.toMap(EvalVariableRef::getResourceId, evalVariableRef -> evalVariableRef));
        evalRecord.setMessage("");
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
        evalRecord.setScore(evalIndexRefs.get(evalIndexRefs.size() - 1).getScore());
        evalRecord.setEvalEndTime(new Date());
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
        List<EvalVariableRef> evalVariableRefs = evalVariableRefService.lambdaQuery().in(EvalVariableRef::getResourceId, variableIds).list();

        // 没有需要同步数据变量项, 跳过
        if (evalVariableRefs.isEmpty()) {
            EvalLog.info(evalRecord, "variables probe data has been sync, continue.");
            return;
        }
        evalVariableRefs.forEach(a -> a.setProgress(EvalVariableRef.Progress.NOT_START.getCode()));
        // 终端节点IP <nodeId, nodeIp>
        Map<String, String> ipMap = nodeIpMap(evalTask.getMpSceneId());

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
            collectionEventPageContext.setId(evalTask.getMpSceneId());
            collectionEventPageContext.setNodeIds(evalVariableRef.getDeviceId());
            collectionEventPageContext.setProbeId(evalVariableRef.getProbeId());
            collectionEventPageContext.setRequestId(IDGeneratorUtil.getID());

            // 查询当前已同步数据量, 生成页码
            int probeDataCount = evalProbeDataService.lambdaQuery().eq(EvalProbeData::getSceneId, evalTask.getMpSceneId()).eq(EvalProbeData::getDeviceId, evalVariableRef.getDeviceId()).eq(EvalProbeData::getProbeId, evalVariableRef.getProbeId()).count();
            if (!SqlHelper.retBool(probeDataCount)) {
                collectionEventPageContext.setPage(1L);
            } else {
                int scale = -(Integer.toString(Math.toIntExact(ONCE_PROBE_DATA_SIZE)).length() - 1);
                // 数据量向上取整, 生成当前页码
                int count = new BigDecimal(probeDataCount).setScale(scale, BigDecimal.ROUND_UP).intValue();
                collectionEventPageContext.setPage((count / ONCE_PROBE_DATA_SIZE) + 1);
            }
            collectionEventPageContext.setPageSize(ONCE_PROBE_DATA_SIZE);

            try {
                exeCollectionEvent(evalRecord, evalVariableRef, collectionEventPageContext, ipMap, true);

                evalVariableRef.setProgress(EvalVariableRef.Progress.FORMATTING.getCode());
                EvalLog.info(evalRecord, evalVariableRef, "sync data completed.");
            } catch (RemoteServiceException e) {
                EvalLog.error(evalRecord, evalVariableRef, "invoke mp probe data api Exception.");
                // 采集数据报错, 采集变量项中止
                String msg = String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.GET_PROBE_DATA_FAILED), evalVariableRef.getName());
                evalRecord.setMessage(msg + "\n");
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
		if (Boolean.TRUE.equals(isFirstCall) && !collectionEventPageContext.getPage().equals(1L)) {
            // es需要循环数次跳过已同步数据
			if (ES_PROBES.contains(collectionEventPageContext.getProbeId())) {
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
			if (BASE.contains(mpCollectionEvent.getProbeId())) {
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
            long size = (collectionEventPageContext.getPage() - 1) * ONCE_PROBE_DATA_SIZE + collectionEventList.size();
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
        JSONArray nodes = jsonObject.getJSONObject(SCENE).getJSONObject(TOPOLOGY).getJSONArray(ELEMENTS);

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
        EvalTask evalTask = getTask(roomId);

        List<EvalRoomPairRsp> results = Lists.newArrayList();

        JSONObject jsonObject = remoteSceneService.getById(evalTask.getMpSceneId(), UserInfoContext.getUserContext().getUserId());
        JSONArray nodes = jsonObject.getJSONObject(SCENE).getJSONObject(TOPOLOGY).getJSONArray(ELEMENTS);
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
        EvalTask evalTask = getTask(roomId);

        List<EvalRoomPairRsp> results = Lists.newArrayList();

        JSONObject jsonObject = remoteSceneService.getById(evalTask.getMpSceneId(), UserInfoContext.getUserContext().getUserId());
        JSONObject cpCollection = jsonObject.getJSONObject(SCENE).getJSONObject(PLUGINS).getJSONObject(CPCOLLECTION);
        if (cpCollection == null) {
            return results;
        }
        JSONObject nodesProbe = cpCollection.getJSONObject(ELEMENTS);
        for (Map.Entry<String, Object> entry : nodesProbe.entrySet()) {
            if (!entry.getKey().equals(nodeId)) {
                continue;
            }

            JSONObject itemData = (JSONObject) entry.getValue();
            JSONArray probesData = itemData.getJSONArray(PROBES);

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
        EvalTask evalTask = getTask(roomId);

        JSONObject jsonObject = remoteSceneService.getById(evalTask.getMpSceneId(), UserInfoContext.getUserContext().getUserId());
        JSONObject nodesProbe = jsonObject.getJSONObject(SCENE).getJSONObject(PLUGINS).getJSONObject(CPCOLLECTION).getJSONObject(ELEMENTS);

        Map<String, List<EvalRoomPairRsp>> results = Maps.newHashMap();
        for (Map.Entry<String, Object> entry : nodesProbe.entrySet()) {

            List<EvalRoomPairRsp> probes = Lists.newArrayList();
            JSONArray probesData = ((JSONObject) entry.getValue()).getJSONArray(PROBES);

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
        EvalTask evalTask = getTask(roomId);

        JSONObject jsonObject = remoteSceneService.getById(evalTask.getMpSceneId(), UserInfoContext.getUserContext().getUserId());

        JSONArray nodes = jsonObject.getJSONObject(SCENE).getJSONObject(TOPOLOGY).getJSONArray(ELEMENTS);
        JSONObject cpCollection = jsonObject.getJSONObject(SCENE).getJSONObject(PLUGINS).getJSONObject(CPCOLLECTION);
        JSONObject nodesProbe;
        if (cpCollection == null) {
            nodesProbe = new JSONObject();
        } else {
            nodesProbe = cpCollection.getJSONObject(ELEMENTS);
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
                JSONArray probes = collectionData.getJSONArray(PROBES);
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

    @Mapper
    public interface EvalRecordToEvalRecordResultRsp extends BaseConvertor<EvalRecord, EvalRecordResultRsp> {
    }

    public static final EvalRecordToEvalRecordResultRsp evalRecordToEvalRecordResultRsp = Mappers.getMapper(EvalRecordToEvalRecordResultRsp.class);

    @Override
    public EvalRecordResultRsp result(String recordId) {

        EvalRecordResultRsp result = evalRecordToEvalRecordResultRsp.from(getById(recordId));
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
        EvalRecord evalRecord = evalRecordMapper.selectById(recordId);
        Integer status = evalRecord.getStatus();
        result.setRunStatus(status >= EvalRecord.Status.END.getCode() ? EvalRecord.Status.END.getCode() : status);

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
            try {
                Files.delete(Paths.get(destHtmlPath));
            } catch (IOException e) {
                log.error("failed to delete file:{}", destHtmlPath);
            }
        }
        return destPdfPath;
    }

    @Override
    public List<EvalSystemResultRsp> getAllCycleResult(String resourceId) {
        List<EvalSystemResultRsp> res = new ArrayList<>();
        LambdaQueryWrapper<EvalResult> filter = new LambdaQueryWrapper<>();
        filter.eq(EvalResult::getRecord, resourceId);
        filter.last("limit 5");
        filter.orderByDesc(EvalResult::getExeCycle);
        List<EvalResult> evalResults = evalResultMapper.selectList(filter);
        EvalSystemResultRsp rsp = new EvalSystemResultRsp();

        List<EvalIndexRef> evalIndexRefs = evalIndexRefService.lambdaQuery().eq(EvalIndexRef::getRecordId, resourceId).list();
        evalIndexRefs.sort(Comparator.comparing(EvalIndexRef::getLevel));

        Map<String, EvalIndexRef> evalIndexRefMap = evalIndexRefs.stream().collect(Collectors.toMap(EvalIndexRef::getResourceId, evalIndexRef -> evalIndexRef));
        Map<String, EvalVariableRef> evalVariableRefMap = evalVariableRefService.lambdaQuery().eq(EvalVariableRef::getRecordId, resourceId).list().stream().collect(Collectors.toMap(EvalVariableRef::getResourceId, evalVariableRef -> evalVariableRef));

        for (EvalIndexRef evalIndexRef : evalIndexRefs) {
            if (evalIndexRef.getLevel().equals(EvalIndex.Level.FIRST.getCode())) {
                rsp.setResourceId(evalIndexRef.getResourceId());
                rsp.setName(evalIndexRef.getName());
                for (int i = 0; i < evalResults.size(); i++) {
                    EvalResult evalResult = evalResults.get(i);
                    if (i == 0) {
                        rsp.setExeCycle(evalResult.getExeCycle());
                    }
                    Integer firstIndexRes = evalResult.getFirstIndexRes();
                    setCycleResult(rsp, i, firstIndexRes);
                }
                rsp.setChildren(generateNextIndexesRes(evalIndexRef, evalIndexRefMap, evalVariableRefMap, evalResults));
            } else {
                break;
            }
        }
        res.add(rsp);
        return res;
    }

    private List<EvalSystemResultRsp> generateNextIndexesRes(EvalIndexRef evalIndexRef, Map<String, EvalIndexRef> evalIndexRefMap, Map<String, EvalVariableRef> evalVariableRefMap, List<EvalResult> evalResults) {
        List<EvalSystemResultRsp> res = new ArrayList<>();
        List<String> nextIds = JsonUtil.toJsonObjectArray(evalIndexRef.getNextIds(), String.class);
        nextIds.forEach(nextId -> {
            EvalSystemResultRsp rsp = new EvalSystemResultRsp();
            EvalIndexRef evalIndexRef1 = evalIndexRefMap.get(nextId);
            if (null == evalIndexRef1) {
                EvalVariableRef evalVariableRef = evalVariableRefMap.get(nextId);
                rsp.setResourceId(evalVariableRef.getResourceId());
                rsp.setName(evalVariableRef.getName());
                for (int i = 0; i < evalResults.size(); i++) {
                    EvalResult evalResult = evalResults.get(i);
                    String fifthIndexRes = evalResult.getFifthIndexRes();
                    JSONObject jsonObject = JsonUtil.toJsonObject(fifthIndexRes, JSONObject.class);
                    Integer score = null;
                    Object obj = jsonObject.get(nextId);
                    if (null != obj) {
                        score = Integer.parseInt(obj.toString());
                    }
                    setCycleResult(rsp, i, score);
                }
                rsp.setChildren(null);
                res.add(rsp);
            } else {
                if (evalIndexRef1.getLevel().equals(EvalIndex.Level.SECOND.getCode())) {
                    rsp.setResourceId(evalIndexRef1.getResourceId());
                    rsp.setName(evalIndexRef1.getName());
                    for (int i = 0; i < evalResults.size(); i++) {
                        EvalResult evalResult = evalResults.get(i);
                        String secondIndexRes = evalResult.getSecondIndexRes();
                        JSONObject jsonObject = JsonUtil.toJsonObject(secondIndexRes, JSONObject.class);
                        int score = Integer.parseInt(jsonObject.get(nextId).toString());
                        setCycleResult(rsp, i, score);
                    }
                    rsp.setChildren(generateNextIndexesRes(evalIndexRef1, evalIndexRefMap, evalVariableRefMap, evalResults));
                    res.add(rsp);
                } else if (evalIndexRef1.getLevel().equals(EvalIndex.Level.THIRD.getCode())) {
                    rsp.setResourceId(evalIndexRef1.getResourceId());
                    rsp.setName(evalIndexRef1.getName());
                    for (int i = 0; i < evalResults.size(); i++) {
                        EvalResult evalResult = evalResults.get(i);
                        String thirdIndexRes = evalResult.getThirdIndexRes();
                        JSONObject jsonObject = JsonUtil.toJsonObject(thirdIndexRes, JSONObject.class);
                        int score = Integer.parseInt(jsonObject.get(nextId).toString());
                        setCycleResult(rsp, i, score);
                    }
                    rsp.setChildren(generateNextIndexesRes(evalIndexRef1, evalIndexRefMap, evalVariableRefMap, evalResults));
                    res.add(rsp);
                } else if (evalIndexRef1.getLevel().equals(EvalIndexRef.Level.AGGREGATION.getCode())) {
                    setCurrentRsp(evalResults, nextId, rsp, evalIndexRef1);
                    rsp.setChildren(generateNextIndexesRes(evalIndexRef1, evalIndexRefMap, evalVariableRefMap, evalResults));
                    res.add(rsp);
                }
            }
        });

        return res;
    }

    private void setCurrentRsp(List<EvalResult> evalResults, String nextId, EvalSystemResultRsp rsp, EvalIndexRef evalIndexRef1) {
        rsp.setResourceId(evalIndexRef1.getResourceId());
        rsp.setName(evalIndexRef1.getName());
        for (int i = 0; i < evalResults.size(); i++) {
            EvalResult evalResult = evalResults.get(i);
            String fourthIndexRes = evalResult.getFourthIndexRes();
            JSONObject jsonObject = JsonUtil.toJsonObject(fourthIndexRes, JSONObject.class);
            Integer score = 0;
            if (null != jsonObject && jsonObject.size() > 0) {
                Object obj = jsonObject.get(nextId);
                if (null == obj) {
                    score = null;
                } else {
                    score = Integer.parseInt(obj.toString());
                }
            }
            setCycleResult(rsp, i, score);
        }
    }

    private void setCycleResult(EvalSystemResultRsp rsp, int i, Integer score) {
        if (0 == i) {
            rsp.setFirstScore(score);
        } else if (i == 1) {
            rsp.setSecondScore(score);
        } else if (i == 2) {
            rsp.setThirdScore(score);
        } else if (i == 3) {
            rsp.setFourthScore(score);
        } else if (i == 4) {
            rsp.setFifthScore(score);
        }
    }

    private JSONObject getEvalRecordJsonObject(String resourceId, String taskId) {
        JSONObject data = new JSONObject();

        EvalRecord evalRecord = getById(resourceId);
        String templateId = evalTaskMapper.getTemplateIdByTaskId(evalRecord.getTaskId());
        EvalTemplate template = evalTemplateService.getById(templateId);
        EvalTask evalTask = evalTaskMapper.selectById(taskId);

        if (StringUtils.isEmpty(evalRecord.getTaskId()) || StringUtils.isEmpty(templateId)) {
            log.error("Task not exist! taskId is empty or templateId is empty");
            throw new BusinessException(CrBusinessErrorCode.TASK_NOT_EXIST_ERROR);
        }
        data.put("title", getTitle(template.getName()));
        JSONObject topoData = localSceneService.getTopoData(template.getTopoId());
        String topoLogo = topoData.getString("logo");
        if (StringUtils.isEmpty(topoLogo)) {
            log.error("The logo file does not exist! topoLogo is empty, topoId = [{}]", template.getTopoId());
            throw new BusinessException(CrBusinessErrorCode.LOGO_NOT_EXIST);
        }
        data.put("topoLogo", remoteServerEnvService.getFileServerHost() + topoLogo);
        //设置测试基本信息
        data.put("taskName", evalTask.getTaskName());
        data.put("name", evalRecord.getName());
        data.put("templateName", template.getName());
        data.put("recordDescription", evalRecord.getDescription());
        data.put("templateDescription", template.getDescription());
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
        List<EvalVariableRef> evalVariableRefList = evalVariableRefMapper.getByRecordId(resourceId);
        List<EvalCollectionContextRsp> evalCollection = getEvalCollection(evalVariableRefList);
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
        data.put("locale", TrainingReport.TemplateI18n.getTemplateI18n());
        return data;
    }

    private List<EvalCollectionContextRsp> getEvalCollection(List<EvalVariableRef> evalVariableRefList) {
        List<EvalCollectionContextRsp> evalCollectionContextList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(evalVariableRefList)) {
            evalCollectionContextList = evalVariableRefList.stream().map(evalVariableRef -> {
                EvalCollectionContextRsp evalCollectionContextRsp = new EvalCollectionContextRsp();
                evalCollectionContextRsp.setResourceId(evalVariableRef.getResourceId());
                evalCollectionContextRsp.setName(evalVariableRef.getName());
                evalCollectionContextRsp.setDetectionAlgorithmId(evalVariableRef.getMatchAlgorithmId());
                evalCollectionContextRsp.setFormatAlgorithmId(evalVariableRef.getFormatAlgorithmId());
                evalCollectionContextRsp.setScoreAlgorithmId(evalVariableRef.getMarkAlgorithmId());
                evalCollectionContextRsp.setDetectionAlgorithmName(evalAlgorithmRefService.getById(evalVariableRef.getMatchAlgorithmId()).getName());
                evalCollectionContextRsp.setFormatAlgorithmName(evalAlgorithmRefService.getById(evalVariableRef.getFormatAlgorithmId()).getName());
                evalCollectionContextRsp.setScoreAlgorithmName(evalAlgorithmRefService.getById(evalVariableRef.getMarkAlgorithmId()).getName());
                return evalCollectionContextRsp;
            }).collect(Collectors.toList());
        }
        return evalCollectionContextList;
    }

    private String getTitle(String name) {

        if (!CommonContext.isChinese()) {
            return "Cyber Exercise Report of " + name;
        } else {
            return name + "评估报告";
        }
    }

    private EvalRecordResultIndexRsp parseGraph(EvalRecordResultRsp result, String variableId) {
        List<EvalSystemIndexesContext> indexes = result.getIndexes();
        EvalRecordResultIndexRsp evalRecordResultIndexRsp = new EvalRecordResultIndexRsp();
        if (CollectionUtils.isNotEmpty(indexes)) {
            evalRecordResultIndexRsp = evalIndexRefService.resultIndex(variableId);
            getOtherResult(evalRecordResultIndexRsp);
            setParentId(evalRecordResultIndexRsp);
        }
        return evalRecordResultIndexRsp;
    }

    private void getOtherResult(EvalRecordResultIndexRsp evalRecordResultIndexRsp) {
        List<EvalRecordResultIndexRsp> children = evalRecordResultIndexRsp.getChildren();
        if (CollectionUtils.isNotEmpty(children)) {
            for (EvalRecordResultIndexRsp child : children) {
                if (child.getLevel().equals(EvalIndex.Level.THIRD.getCode())) {
                    EvalRecordResultIndexRsp childEvalRecordResultIndexRsp = evalIndexRefService.resultIndex(child.getResourceId());
                    child.setChildren(childEvalRecordResultIndexRsp.getChildren());
                } else {
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

    private EvalVariableGraphListRsp changeGraph(String variableId) {
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

    private String changeRecordStatus(Boolean enableRecording) {
        if (CommonContext.isChinese()) {
            if (Boolean.TRUE.equals(enableRecording)) {
                return CommonConstant.RIGHT;
            } else {
                return CommonConstant.NO;
            }
        } else {
            return String.valueOf(enableRecording);
        }
    }
}
