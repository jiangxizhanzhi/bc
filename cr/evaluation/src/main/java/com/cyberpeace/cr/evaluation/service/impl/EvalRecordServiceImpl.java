/**
 * 这段代码是一个Spring Boot服务类，实现了评估记录管理功能。以下是该代码的主要组成部分和功能分析： 
 * ### 类定义和依赖注入 
 * - **类名**：`EvalRecordServiceImpl` 
 * - **继承**：`ServiceImpl<EvalRecordMapper, EvalRecord>`，继承了MyBatis-Plus的基本实现。 
 * - **注解**： 
 * - `@Service`：标记这是一个Spring服务组件。 
 * - `@Slf4j`：引入日志功能。 
 * - **依赖注入**：通过`@Autowired`注入了多个业务逻辑和服务层组件，如`EvalIndexRefService`, `EvalVariableRefService`, `EvalIndexService`等。 
 * ### 主要方法 
 * 1. **`isExist`** 
 * - 检查给定的评估记录是否已存在。 
 * - 使用`LambdaQueryWrapper`构建查询条件，并检查记录数量。 
 * 2. **`getTask`** 
 * - 根据任务ID获取评估任务，并验证任务的存在性和用户的权限。 
 * 3. **`getEntity`** 
 * - 根据资源ID获取评估记录实体，并进行合法性检查。 
 * 4. **`pageList`** 
 * - 分页查询评估记录列表。 
 * 5. **`get`** 
 * - 根据记录ID获取详细的评估记录信息，并组装成响应对象。 
 * 6. **`create`** 
 * - 创建或更新评估记录，并处理相关的关联数据。 
 * - 涉及到多级指标和变量的处理，包括创建和更新相关数据。 
 * 7. **`update`** 
 * - 更新评估记录，并处理相关的关联数据。 
 * 8. **`delete`** 
 * - 删除评估记录及其相关关联数据。 
 * 9. **`recordRun`** 
 * - 异步执行评估记录的运行逻辑，包括同步数据、计算变量和指标。 
 * 10. **`addResToEvalResult`** 
 * - 将评估结果添加到结果表中。 
 * 11. **`startVariables`** 
 * - 开始计算变量项。 
 * 12. **`startIndexes`** 
 * - 开始计算指标项。 
 * 13. **`syncProbeData`** 
 * - 同步探测数据。 
 * 14. **`exeCollectionEvent`** 
 * - 执行数据采集事件。 
 * 15. **`nodeIpMap`** 
 * - 获取场景下的节点IP映射。 
 * 16. **`getNodes`** 
 * - 获取房间中的节点列表。 
 * 17. **`getProbes`** 
 * - 获取指定节点的探针列表。 
 * 18. **`dumpEvalRecordPath`** 
 * - 导出评估记录为PDF文件。 
 * 19. **`getAllCycleResult`** 
 * - 获取所有周期的结果。 
 * 20. **`parseGraph`** 
 * - 解析评估记录的图形数据。 
 * ### 其他辅助方法 
 * - 多个辅助方法用于处理数据转换、生成算法引用、设置父ID等操作。 
 * ### 总结 
 * 该类主要负责评估记录的CRUD操作，以及与之相关的复杂业务逻辑处理，如多级指标和变量的管理、数据同步、结果计算等。整体设计较为复杂，涉及多层嵌套的数据处理和业务逻辑。 
**/
package com.cyberpeace.cr.evaluation.service.impl;


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
import com.cyberpeace.cr.evaluation.service.context.EvalRecordIndexContext;
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

/**
 * 评估记录Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
@Slf4j
public class EvalRecordServiceImpl extends ServiceImpl<EvalRecordMapper, EvalRecord> implements EvalRecordService {
    /**
     * 评估指数裁判Service
     */
    @Autowired
    private EvalIndexRefService evalIndexRefService;

    /**
     * 评估变量裁判Service
     */
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
     * 评估收集Mapper
     */
    @Autowired
    private EvalCollectionMapper evalCollectionMapper;

    /**
     * 三级指标关联关系
     */
    @Autowired
    private EvalIndicatorService evalIndicatorService;

    /**
     * 事务模板
     */
    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 评估算法Service
     */
    @Autowired
    private EvalAlgorithmService evalAlgorithmService;

    /**
     * 远程场景Service
     */
    @Autowired
    private RemoteSceneService remoteSceneService;

    /**
     * 评估探针数据Service
     */
    @Autowired
    private EvalProbeDataService evalProbeDataService;

    /**
     * 评估算法裁判Service
     */
    @Autowired
    private EvalAlgorithmRefService evalAlgorithmRefService;

    /**
     * 评估任务Mapper
     */
    @Autowired
    private EvalTaskMapper evalTaskMapper;

    /**
     * 评估任务步Service
     */
    @Autowired
    private EvalTaskStepService evalTaskStepService;

    /**
     * 评估模板Service
     */
    @Autowired
    @Lazy
    private EvalTemplateService evalTemplateService;

    /**
     * 当地场景Service
     */
    @Autowired
    private LocalSceneService localSceneService;

    /**
     * 评估变量裁判探针数据Service
     */
    @Autowired
    private EvalVariableRefProbeDataService evalVariableRefProbeDataService;

    /**
     * 靶场常数Service
     */
    @Autowired
    private CrConstantsService crConstantsService;

    /**
     * 评估变量裁判Mapper
     */
    @Autowired
    private EvalVariableRefMapper evalVariableRefMapper;

    /**
     * 远程服务器环境Service
     */
    @Autowired
    private RemoteServerEnvService remoteServerEnvService;

    /**
     * 评估结果Mapper
     */
    @Autowired
    private EvalResultMapper evalResultMapper;

    /**
     * 评估记录Mapper
     */
    @Autowired
    private EvalRecordMapper evalRecordMapper;

    /**
     * 评估指数Mapper
     */
    @Autowired
    private EvalIndexMapper evalIndexMapper;

    /**
     * 一次探测数据大小
     */
    private static final long onceProbeDataSize = 1000L;  // 10/100/1000

    /**
     * 存在
     *
     * @param requestData 请求数据
     */
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
        if (!Objects.equals(evalTask.getCreateUserId(), UserInfoContext.getUserContext().getUserId())) {
            log.error("Permission denied! CreateUserId = [{}], UserId = [{}]", evalTask.getCreateUserId(), UserInfoContext.getUserContext().getUserId());
            throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
        }

        return evalTask;
    }

    /**
     * 获取实体
     *
     * @param resourceId 资源id
     * @return {@code EvalRecord }
     */
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

    /**
     * 分页列表
     *
     * @param page    分页
     * @param taskId  任务id
     * @param status  状态
     * @param pattern 模式
     * @return {@code ListRsp<EvalRecordListRsp> }
     */
    @Override
    public ListRsp<EvalRecordListRsp> pageList(Page page, String taskId, Integer status, String pattern) {
        return PageUtil.getListResult(this.getBaseMapper().pageList(page, taskId, status, pattern));
    }

    /**
     * 获取
     *
     * @param recordId 记录id
     * @return {@code EvalRecordRsp }
     */
    @Override
    public EvalRecordRsp get(String recordId) {
        EvalRecord evalRecord = getEntity(recordId);

        EvalRecordRsp recordRsp = BeanUtils.copy(evalRecord, EvalRecordRsp.class);
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

    /**
     * 创造
     *
     * @param requestData 请求数据
     * @return {@code EvalRecordRsp }
     */
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
            collectionIds.forEach(collectionId->{
                String matrixData = evalAggregation.getMatrixData();
                if(!collections.contains(collectionId)){
                    collections.add(collectionId);
                } else {
                    String id = IDGeneratorUtil.getID();
                    EvalCollection evalCollection = evalCollectionMapper.selectById(collectionId);
                    evalCollection.setResourceId(id);
                    evalCollections.add(evalCollection);
                    collectionIds.set(collectionIds.indexOf(collectionId), id);
                    EvalRecordVariableContext evalRecordVariableContext = evalRecordVariableContextMap.get(collectionId);
                    evalRecordVariableContextMap.put(id, evalRecordVariableContext);
                    if(StringUtils.isNotBlank(matrixData)){
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
            if(evalAlgorithmMap.containsKey(formatAlgorithmId)) {
                formatAlgorithmId = IDGeneratorUtil.getID();
                evalAlgorithmMap.put(formatAlgorithmId, evalAlgorithmMap.get(evalCollection.getFormatAlgorithmId()));
                evalVariableRef.setFormatAlgorithmId(formatAlgorithmId);
            }
            EvalAlgorithmRef newFormatAlgorithm = generateAlgorithmRef(formatAlgorithmId, evalAlgorithmMap, evalAlgorithmRefMap);
            evalVariableRef.setFormatAlgorithmId(newFormatAlgorithm.getResourceId());
            evalVariableRef.setFormatParams(evalCollection.getFormatAlgorithmParams());

            // 匹配算法
            String detectionAlgorithmId = evalCollection.getDetectionAlgorithmId();
            if(evalAlgorithmMap.containsKey(detectionAlgorithmId)) {
                detectionAlgorithmId = IDGeneratorUtil.getID();
                evalAlgorithmMap.put(detectionAlgorithmId, evalAlgorithmMap.get(evalCollection.getDetectionAlgorithmId()));
                evalVariableRef.setMatchAlgorithmId(detectionAlgorithmId);
            }
            EvalAlgorithmRef newMatchAlgorithm = generateAlgorithmRef(detectionAlgorithmId, evalAlgorithmMap, evalAlgorithmRefMap);
            evalVariableRef.setMatchAlgorithmId(newMatchAlgorithm.getResourceId());
            evalVariableRef.setMatchParams(evalCollection.getDetectionAlgorithmParams());

            // 评分算法
            String scoreAlgorithmId = evalCollection.getScoreAlgorithmId();
            if(evalAlgorithmMap.containsKey(scoreAlgorithmId)){
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
            for(String item :JsonUtil.toJsonObjectArray(evalAggregation.getCollectionIds(), String.class)){
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
            if(evalAlgorithmMap.containsKey(aggregatingAlgorithmId)){
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
                if(evalAlgorithmMap.containsKey(aggregatingAlgorithmId)){
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
                if(evalAlgorithmMap.containsKey(aggregatingAlgorithmId)){
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
            if(evalIndex.getLevel().equals(EvalIndex.Level.THIRD.getCode())){
                matrixData = evalIndicatorService.getById(evalIndex.getEvalIndicatorId()).getMatrixData();
            }
            for(String nextId:nextIdLists){
                String currentId = evalIndexIdsMap.get(nextId);
                if (StringUtils.isNotBlank(matrixData)) {
                    matrixData = matrixData.replaceAll(nextId, currentId);
                }
            }
            evalIndexRef.setMatrixData(matrixData);
            if(evalIndex.getLevel().equals(EvalIndex.Level.SECOND.getCode()) && StringUtils.isNotBlank(matrixData)){
                // 三级指标的 indicator id与 三级指标 resource_id 关联
                Map<String, String> tmp = new HashMap<>();
                for(String id:nextIdLists){
                    EvalIndex index = evalIndexMap.get(id);
                    String evalIndicatorId = index.getEvalIndicatorId();
                    tmp.put(evalIndicatorId, index.getResourceId());
                }
                JSONArray objects = JsonUtil.stringToArray(matrixData);
                // 从上面的map 中获取三级指标的indicator 对应的id 并替换成当前新生成的id
                for(int i = 0;i< objects.size();i++){
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

    /**
     * 设置特殊列表
     *
     * @param fourthIndexes                第四个指标
     * @param fourthIndexesTypeMap         第四索引类型 Map
     * @param fourthSpecialistMap          第四张专家地图
     * @param evalRecordVariableContextMap eval record 变量上下文映射
     */
    public void setSpecialList(List<EvalRecordIndexContext> fourthIndexes, Map<String, Integer> fourthIndexesTypeMap,
                               Map<String, Integer> fourthSpecialistMap,Map<String, EvalRecordVariableContext> evalRecordVariableContextMap){

        fourthIndexes.forEach(fourthIndex->{
            fourthIndexesTypeMap.put(fourthIndex.getResourceId(), fourthIndex.getType());
            List<EvalRecordVariableContext> variables = fourthIndex.getVariables();
            if (fourthIndex.getType().equals(EvalIndexRef.Type.SPECIALIST.getCode())) {
                fourthSpecialistMap.put(fourthIndex.getResourceId(), fourthIndex.getSpecialist().getValue());
            }
            variables.forEach(evalRecordVariableContext -> {
                evalRecordVariableContextMap.put(evalRecordVariableContext.getResourceId(), evalRecordVariableContext);
            });
        });
    }

    /**
     * 生成算法 ref
     *
     * @param algorithmId         算法id
     * @param originAlgorithmMap  原点算法映射
     * @param evalAlgorithmRefMap EVAL 算法 Ref Map
     * @return {@code EvalAlgorithmRef }
     */
    private EvalAlgorithmRef generateAlgorithmRef(String algorithmId, Map<String, EvalAlgorithm> originAlgorithmMap, Map<String, EvalAlgorithmRef> evalAlgorithmRefMap) {
        // 取消原逻辑 【已拷贝算法, 不再重复拷贝, evalAlgorithmRefMap获取返回】


        EvalAlgorithm originAlgorithm = originAlgorithmMap.get(algorithmId);
        EvalAlgorithmRef newAlgorithm = new EvalAlgorithmRef();
        org.springframework.beans.BeanUtils.copyProperties(originAlgorithm, newAlgorithm, "id");
        newAlgorithm.setResourceId(IDGeneratorUtil.getID());

        // 拷贝过一次算法, 放入evalAlgorithmRefMap
        evalAlgorithmRefMap.put(algorithmId, newAlgorithm);
        return newAlgorithm;
    }

    /**
     * 更新
     *
     * @param requestData 请求数据
     * @return {@code EvalRecordRsp }
     */
    @Override
    public EvalRecordRsp update(EvalRecordAddReq requestData) {
        EvalRecord originEvalRecord = getEntity(requestData.getResourceId());

        EvalRecord newEvalRecord = BeanUtils.copy(originEvalRecord, EvalRecord.class);
        BeanUtils.copyProperties(requestData, newEvalRecord);

        // 指标体系ID被更新情况, 调用this.create, 由create方法进行指标体系重建
        if (!originEvalRecord.getSystemId().equals(newEvalRecord.getSystemId())) {
            return create(requestData);
        }
        // TODO 不止3级 可修改评估方式
        Map<String, EvalIndexRef> evalIndexRefMap = evalIndexRefService.lambdaQuery().eq(EvalIndexRef::getRecordId, newEvalRecord.getResourceId()).eq(EvalIndexRef::getLevel, EvalIndex.Level.THIRD.getCode()).list().stream().collect(Collectors.toMap(EvalIndexRef::getResourceId, evalIndexRef -> evalIndexRef));
//        Map<String, EvalIndexRef> evalIndexRefMap = evalIndexRefService.lambdaQuery().eq(EvalIndexRef::getRecordId, newEvalRecord.getResourceId()).list().stream().collect(Collectors.toMap(EvalIndexRef::getResourceId, evalIndexRef -> evalIndexRef));
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

    /**
     * 生成下一个索引
     *
     * @param evalIndexRef     eval 索引 ref
     * @param fullIndexesMap   完整索引映射
     * @param fullVariablesMap 完整变量映射
     * @param isFull           已满
     * @return {@code EvalSystemIndexesContext }
     */
    private EvalSystemIndexesContext generateNextIndexes(EvalIndexRef evalIndexRef, Map<String, EvalIndexRef> fullIndexesMap, Map<String, EvalVariableRef> fullVariablesMap, Boolean isFull) {
        EvalSystemIndexesContext evalSystemIndexesContext = BeanUtils.copy(evalIndexRef, EvalSystemIndexesContext.class);
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

    /**
     * 生成下一个索引
     *
     * @param evalIndexRef     eval 索引 ref
     * @param fullIndexesMap   完整索引映射
     * @param fullVariablesMap 完整变量映射
     * @return {@code EvalSystemIndexesContext }
     */
    private EvalSystemIndexesContext generateNextIndexes(EvalIndexRef evalIndexRef, Map<String, EvalIndexRef> fullIndexesMap, Map<String, EvalVariableRef> fullVariablesMap) {

        return generateNextIndexes(evalIndexRef, fullIndexesMap, fullVariablesMap, false);
    }

    /**
     * 删除
     *
     * @param recordIds 记录id列表
     * @return {@code Boolean }
     */
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

    /**
     * 删除记录
     *
     * @param taskIds 任务id列表
     * @return {@code Boolean }
     */
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

    /**
     * 删除关系
     *
     * @param recordIds 记录id列表
     */
    private void deleteRelation(List<String> recordIds) {
        // todo 算法, 同步数据, 计算结果一并删除
        evalIndexRefService.removeByIds(evalIndexRefService.lambdaQuery().in(EvalIndexRef::getRecordId, recordIds).list().stream().map(EvalIndexRef::getResourceId).collect(Collectors.toList()));

        evalVariableRefService.removeByIds(evalVariableRefService.lambdaQuery().in(EvalVariableRef::getRecordId, recordIds).list().stream().map(EvalVariableRef::getResourceId).collect(Collectors.toList()));
    }

    /**
     * 记录运行
     *
     * @param evalRecord EVAL 记录
     * @param evalTask   评估任务
     * @param isRestart  是重新启动
     * @param language   语言
     */
    @Override
    @Async
    public void recordRun(EvalRecord evalRecord, EvalTask evalTask, Boolean isRestart, String language) {
        CommonContext.setLanguage(language);
        EvalLog.info(evalRecord, String.format("Range Test evaluation %s %s.", evalRecord.getName(), isRestart ? "RESTART" : "START"));
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
        startIndexes(evalRecord, evalTask);
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

    /**
     * 新增结果自评估结果
     *
     * @param evalRecord EVAL 记录
     */
    private void addResToEvalResult(EvalRecord evalRecord) {
        if(evalRecord.getStatus().equals(EvalRecord.Status.EXCEPTION.getCode())){
            return;
        }
        Integer exeCycle = 1;
        EvalResult evalResult = new EvalResult();
        evalResult.setExeCycle(exeCycle);
        LambdaQueryWrapper<EvalResult> filter = new LambdaQueryWrapper<>();
        String record = evalRecord.getResourceId();
        evalResult.setRecord(record);
        filter.eq(EvalResult::getRecord, record);
        filter.orderByDesc(EvalResult::getExeCycle);
        filter.last("limit 1");
        EvalResult evalResultQuery = evalResultMapper.selectOne(filter);
        evalResult.setExeCycle(exeCycle);
        if(null != evalResultQuery){
            exeCycle += evalResultQuery.getExeCycle();
            evalResult.setExeCycle(exeCycle);
        }
        // 保存各index_ref id：score 1-4
        LambdaQueryWrapper<EvalIndexRef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EvalIndexRef::getRecordId, record);
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

        for(EvalIndexRef first:firstIndexes){
            String nextIds = first.getNextIds();
            Integer score = first.getScore();
            if(first.getType().equals(EvalIndexRef.Type.SPECIALIST.getCode())){
                score = first.getSpecialistValue();
            }
            evalResult.setFirstIndexRes(score);
            List<String> nextIdList = JsonUtil.toJsonObjectArray(nextIds, String.class);
            JSONObject secondObj = new JSONObject();
            JSONObject thirdObj = new JSONObject();
            JSONObject fourthObj = new JSONObject();
            JSONObject fifthObj = new JSONObject();

            for(String tmpIndex:nextIdList){
                EvalIndexRef evalIndexRef = secondEvalIndexRefMap.get(tmpIndex);
                if(null != evalIndexRef){
                    secondObj.put(evalIndexRef.getResourceId(), evalIndexRef.getScore());
                }
                String refNextIds = evalIndexRef.getNextIds();
                List<String> nextRedIdList = JsonUtil.toJsonObjectArray(refNextIds, String.class);
                for(String tmpNextIndex:nextRedIdList){
                    EvalIndexRef indexRef = thirdEvalIndexRefMap.get(tmpNextIndex);
                    if(null != indexRef){
                        Integer indexRefScore = indexRef.getScore();
                        thirdObj.put(indexRef.getResourceId(), indexRef.getType().equals(EvalIndexRef.Type.SPECIALIST.getCode()) ? indexRef.getSpecialistValue() : indexRefScore);
                    }
                    String ids = indexRef.getNextIds();
                    List<String> idList = JsonUtil.toJsonObjectArray(ids, String.class);
                    for(String currentNextId:idList){
                        EvalIndexRef current = fourthEvalIndexRefMap.get(currentNextId);
                        log.info("current evalIndexRef name is : [{}], id is [{}], score is [{}]", current.getName(), current.getResourceId(), current.getScore());
                        if(null != current){
                            Integer type = current.getType();
                            Integer currentScore = current.getScore();
                            if(type.equals(EvalIndexRef.Type.SPECIALIST.getCode())){
                                currentScore = current.getSpecialistValue();
                            }
                            fourthObj.put(current.getResourceId(), currentScore);
                        }
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

    /**
     * 获取第五对象
     *
     * @param fifthEvalIndexRefMap Fifth EVAL Index 参考图
     * @param fifthObj             第五目标
     * @param current              当前
     */
    private void getFifthObj(Map<String, EvalVariableRef> fifthEvalIndexRefMap, JSONObject fifthObj, EvalIndexRef current) {
        String fifthIds = current.getNextIds();
        List<String> fifthIdsList = JsonUtil.toJsonObjectArray(fifthIds, String.class);
        for(String fifthId:fifthIdsList){
            EvalVariableRef fifthVariableRef = fifthEvalIndexRefMap.get(fifthId);
            if(null != fifthVariableRef){
                Integer score = fifthVariableRef.getScore();
                if(fifthVariableRef.getType().equals(EvalIndexRef.Type.SPECIALIST.getCode())){
                    score = current.getSpecialistValue();
                }
                fifthObj.put(fifthId, score);
            }
        }
    }

    /**
     * 起始变量
     *
     * @param evalRecord  EVAL 记录
     * @param evalTask    评估任务
     * @param variableIds 变量id列表
     */
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

    /**
     * 起始索引
     *
     * @param evalRecord EVAL 记录
     * @param evalTask   评估任务
     */
    private void startIndexes(EvalRecord evalRecord, EvalTask evalTask) {

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

    /**
     * 同步探测数据
     *
     * @param evalRecord  EVAL 记录
     * @param evalTask    评估任务
     * @param variableIds 变量id列表
     */
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
        evalVariableRefs.forEach(a->a.setProgress(EvalVariableRef.Progress.NOT_START.getCode()));
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
                evalRecord.setMessage(msg + "\n");
                updateById(evalRecord);

                evalVariableRef.setProgress(EvalVariableRef.Progress.END.getCode());
                evalVariableRef.setScore(0);
            }
            evalVariableRefService.updateById(evalVariableRef);

        });
    }

    /**
     * EXE 集合事件
     *
     * @param evalRecord                 EVAL 记录
     * @param evalVariableRef            eval 变量 ref
     * @param collectionEventPageContext 收集事件分页上下文
     * @param ipMap                      ip地址地图
     * @param isFirstCall                是第一次调用
     */
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

    /**
     * 获取节点
     *
     * @param roomId 房间id
     * @return {@code List<EvalRoomPairRsp> }
     */
    @Override
    public List<EvalRoomPairRsp> getNodes(String roomId) {
        EvalTask evalTask = getTask(roomId);

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

    /**
     * 获取探针
     *
     * @param roomId 房间id
     * @param nodeId 节点id
     * @return {@code List<EvalRoomPairRsp> }
     */
    @Override
    public List<EvalRoomPairRsp> getProbes(String roomId, String nodeId) {
        EvalTask evalTask = getTask(roomId);

        List<EvalRoomPairRsp> results = Lists.newArrayList();

        JSONObject jsonObject = remoteSceneService.getById(evalTask.getMpSceneId(), UserInfoContext.getUserContext().getUserId());
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

    /**
     * 获取探针
     *
     * @param roomId 房间id
     * @return {@code Map<String, List<EvalRoomPairRsp>> }
     */
    @Override
    public Map<String, List<EvalRoomPairRsp>> getProbes(String roomId) {
        EvalTask evalTask = getTask(roomId);

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

    /**
     * 获取节点探针
     *
     * @param roomId 房间id
     * @return {@code List<EvalRecordNodeProbeReq> }
     */
    @Override
    public List<EvalRecordNodeProbeReq> getNodeProbe(String roomId) {
        EvalTask evalTask = getTask(roomId);

        JSONObject jsonObject = remoteSceneService.getById(evalTask.getMpSceneId(), UserInfoContext.getUserContext().getUserId());

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

    /**
     * 结果
     *
     * @param recordId 记录id
     * @return {@code EvalRecordResultRsp }
     */
    @Override
    public EvalRecordResultRsp result(String recordId) {
        EvalRecordResultRsp result = new EvalRecordResultRsp();
        BeanUtils.copyProperties(getById(recordId), result);

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

    /**
     * 记录状态枚举
     *
     * @return {@code List<EvalRoomIntPairRsp> }
     */
    @Override
    public List<EvalRoomIntPairRsp> recordStatusEnum() {

        return crConstantsService.getByType(CrConstantsConfig.ConstantsType.EVAL_STATUS.getCode()).stream().map(item -> {
            EvalRoomIntPairRsp result = new EvalRoomIntPairRsp();
            result.setLabel(item.selectI18nName());
            result.setId(item.getCode());
            return result;
        }).collect(Collectors.toList());
    }

    /**
     * dump eval 记录路径
     *
     * @param resourceId 资源id
     * @param taskId     任务id
     * @return {@code String }
     */
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

    /**
     * 获取都周期结果
     *
     * @param resourceId 资源id
     * @return {@code List<EvalSystemResultRsp> }
     */
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
                for(int i = 0; i < evalResults.size(); i++){
                    EvalResult evalResult = evalResults.get(i);
                    if(i == 0){
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

    /**
     * 生成下一个指标结果
     *
     * @param evalIndexRef       eval 索引 ref
     * @param evalIndexRefMap    EVAL 索引参考图
     * @param evalVariableRefMap eval 变量 ref map
     * @param evalResults        评估结果
     * @return {@code List<EvalSystemResultRsp> }
     */
    private List<EvalSystemResultRsp> generateNextIndexesRes(EvalIndexRef evalIndexRef, Map<String, EvalIndexRef> evalIndexRefMap, Map<String, EvalVariableRef> evalVariableRefMap, List<EvalResult> evalResults) {
        List<EvalSystemResultRsp> res = new ArrayList<>();
        List<String> nextIds = JsonUtil.toJsonObjectArray(evalIndexRef.getNextIds(), String.class);
        nextIds.forEach(nextId -> {
            EvalSystemResultRsp rsp = new EvalSystemResultRsp();
            EvalIndexRef evalIndexRef1 = evalIndexRefMap.get(nextId);
            if(null == evalIndexRef1){
                EvalVariableRef evalVariableRef = evalVariableRefMap.get(nextId);
                rsp.setResourceId(evalVariableRef.getResourceId());
                rsp.setName(evalVariableRef.getName());
                for (int i = 0; i < evalResults.size(); i++) {
                    EvalResult evalResult = evalResults.get(i);
                    String fifthIndexRes = evalResult.getFifthIndexRes();
                    JSONObject jsonObject = JsonUtil.toJsonObject(fifthIndexRes, JSONObject.class);
                    Integer score = null;
                    Object obj = jsonObject.get(nextId);
                    if(null != obj){
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

    /**
     * 设置当前响应
     *
     * @param evalResults   评估结果
     * @param nextId        下一个id
     * @param rsp           响应
     * @param evalIndexRef1 评估索引 ref1
     */
    private void setCurrentRsp(List<EvalResult> evalResults, String nextId, EvalSystemResultRsp rsp, EvalIndexRef evalIndexRef1) {
        rsp.setResourceId(evalIndexRef1.getResourceId());
        rsp.setName(evalIndexRef1.getName());
        for (int i = 0; i < evalResults.size(); i++) {
            EvalResult evalResult = evalResults.get(i);
            String fourthIndexRes = evalResult.getFourthIndexRes();
            JSONObject jsonObject = JsonUtil.toJsonObject(fourthIndexRes, JSONObject.class);
            Integer score = 0;
            if(null != jsonObject && jsonObject.size() > 0){
                Object obj = jsonObject.get(nextId);
                if(null == obj){
                    score = null;
                } else {
                    score = Integer.parseInt(obj.toString());
                }
            }
            setCycleResult(rsp, i, score);
        }
    }

    /**
     * 设置周期结果
     *
     * @param rsp   响应
     * @param i     我
     * @param score 得分
     */
    private void setCycleResult(EvalSystemResultRsp rsp, int i, Integer score) {
        if(0 == i) {
            rsp.setFirstScore(score);
        } else if( i == 1 ) {
            rsp.setSecondScore(score);
        } else if(i == 2) {
            rsp.setThirdScore(score);
        } else if(i ==3) {
            rsp.setFourthScore(score);
        } else if(i ==4) {
            rsp.setFifthScore(score);
        }
    }

    /**
     * 获取评估记录JSON 格式对象
     *
     * @param resourceId 资源id
     * @param taskId     任务id
     * @return {@code JSONObject }
     */
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

    /**
     * 获取评估收集
     *
     * @param EvalVariableRefList eval 变量 ref list
     * @return {@code List<EvalCollectionContextRsp> }
     */
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

    /**
     * 获取模板i18n
     *
     * @return {@code JSONObject }
     */
    private JSONObject getTemplateI18n() {
        JSONObject jsonObject = new JSONObject();

        for (TrainingReport.TemplateI18n enumData : TrainingReport.TemplateI18n.values()) {
            jsonObject.put(enumData.name(), enumData.getMsg());
        }

        return jsonObject;
    }

    /**
     * 获取标题
     *
     * @param name 名称
     * @return {@code String }
     */
    private String getTitle(String name) {

        if (!CommonContext.isChinese()) {
            return "Cyber Exercise Report of " + name;
        } else {
            return name + "评估报告";
        }
    }

    /**
     * 解析图
     *
     * @param result     结果
     * @param variableId 变量id
     * @return {@code EvalRecordResultIndexRsp }
     */
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

    /**
     * 获取其他结果
     *
     * @param evalRecordResultIndexRsp 评估记录结果指数响应
     */
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

    /**
     * 设置父母id
     *
     * @param evalRecordResultIndexRsp 评估记录结果指数响应
     */
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

    /**
     * 更改图表
     *
     * @param variableId 变量id
     * @return {@code EvalVariableGraphListRsp }
     */
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

    /**
     * 改变记录状态
     *
     * @param enableRecording 启用录制
     * @return {@code String }
     */
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
