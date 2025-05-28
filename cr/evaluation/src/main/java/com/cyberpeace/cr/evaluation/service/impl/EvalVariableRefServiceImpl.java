/**
 * 这段代码定义了一个名为 `EvalVariableRefServiceImpl` 的服务类，该类实现了 `EvalVariableRefService` 接口。这个服务类主要用于处理与评估变量相关的逻辑，包括格式化、匹配和评分等步骤。下面是对代码的主要功能和结构的简要分析： 
 * ### 主要功能 
 * 1. **格式化（Formatting）**： 
 * - 从数据库中获取指定时间段内的探针数据。 
 * - 使用远程Python服务执行格式化算法，并将结果存储到数据库中。 
 * - 如果格式化过程中出现错误，则抛出相应的业务异常。 
 * 2. **匹配（Matching）**： 
 * - 获取已格式化的数据，并使用远程Python服务执行匹配算法。 
 * - 将匹配结果更新到数据库中。 
 * - 如果匹配过程中出现错误，则抛出相应的业务异常。 
 * 3. **评分（Scoring）**： 
 * - 获取已格式化和匹配的数据，并使用远程Python服务执行评分算法。 
 * - 更新评分结果到数据库中。 
 * - 如果评分过程中出现错误，则抛出相应的业务异常。 
 * 4. **图形展示（Graph）**： 
 * - 根据变量ID查询探针数据，并生成时间、值和匹配状态的列表，用于图形展示。 
 * 5. **源数据分页查询（Origin Data Pagination）**： 
 * - 根据分页参数和变量ID查询探针数据。 
 * 6. **结果变量查询（Result Variable Query）**： 
 * - 根据变量ID查询评估结果，并返回格式化的结果对象。 
 * ### 关键组件 
 * - **依赖注入**：通过 `@Autowired` 注入了多个服务类，如 `EvalProbeDataService`、`EvalVariableRefProbeDataService`、`RemotePythonService` 等。 
 * - **日志记录**：使用 `EvalLog` 类进行日志记录，记录各个阶段的操作信息。 
 * - **数据模型**：定义了多个数据模型类，如 `EvalVariableRef`、`EvalProbeData`、`EvalVariableRefProbeData` 等，用于表示不同类型的实体对象。 
 * - **异常处理**：通过 `BusinessException` 和 `RemoteServiceException` 处理业务逻辑中的异常情况。 
 * ### 方法概述 
 * - `startVariableAlgorithm`：启动格式化过程。 
 * - `startFormat`：执行格式化算法。 
 * - `executeFormat`：具体执行格式化操作。 
 * - `startMatch`：启动匹配过程。 
 * - `startMark`：启动评分过程。 
 * - `graph`：生成图形展示数据。 
 * - `originData`：分页查询源数据。 
 * - `resultVariable`：查询并返回评估结果。 
 * ### 总结 
 * 该服务类主要负责处理与评估变量相关的复杂业务逻辑，通过调用远程服务和数据库操作来完成格式化、匹配和评分等步骤，并提供了图形展示和数据查询的功能。代码结构清晰，注释详细，易于理解和维护。 
**/
package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordResultAlgorithmRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordResultVariableRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableDataPageRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableGraphRsp;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalVariableRefMapper;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmRefService;
import com.cyberpeace.cr.evaluation.service.EvalProbeDataService;
import com.cyberpeace.cr.evaluation.service.EvalVariableRefProbeDataService;
import com.cyberpeace.cr.evaluation.service.EvalVariableRefService;
import com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext;
import com.cyberpeace.cr.evaluation.service.context.EvalVariableRefProbeDataContext;
import com.cyberpeace.integratation.model.evaluation.*;
import com.cyberpeace.integratation.service.RemotePythonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 评估变量裁判Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
@Slf4j
public class EvalVariableRefServiceImpl extends ServiceImpl<EvalVariableRefMapper, EvalVariableRef> implements EvalVariableRefService {
    /**
     * 评估探针数据Service
     */
    @Autowired
    private EvalProbeDataService evalProbeDataService;

    /**
     * 评估变量裁判探针数据Service
     */
    @Autowired
    private EvalVariableRefProbeDataService evalVariableRefProbeDataService;

    /**
     * 远程蟒Service
     */
    @Autowired
    private RemotePythonService remotePythonService;

    /**
     * 事务模板
     */
    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 评估算法裁判Service
     */
    @Autowired
    private EvalAlgorithmRefService evalAlgorithmRefService;

    /**
     * 一次格式化大小
     */
    private static final int onceFormatSize = 1000;

    /**
     * 格式值长度限制
     */
    private static final int formatValueLengthLimit = 65535;

    /**
     * Start 变量算法
     *
     * @param evalVariableRef     eval 变量 ref
     * @param evalAlgorithmRefMap EVAL 算法 Ref Map
     * @param evalRecord          EVAL 记录
     * @param evalTask            评估任务
     * @return {@code String }
     */
    @Override
    public String startVariableAlgorithm(EvalVariableRef evalVariableRef,
                                       Map<String, EvalAlgorithmRef> evalAlgorithmRefMap,
                                       EvalRecord evalRecord,
                                       EvalTask evalTask) {
        return startFormat(evalVariableRef, evalAlgorithmRefMap, evalRecord, evalTask);
    }

    /**
     * Start 格式
     *
     * @param evalVariableRef     eval 变量 ref
     * @param evalAlgorithmRefMap EVAL 算法 Ref Map
     * @param evalRecord          EVAL 记录
     * @param evalTask            评估任务
     * @return {@code String }
     */
    private String startFormat(EvalVariableRef evalVariableRef, Map<String, EvalAlgorithmRef> evalAlgorithmRefMap, EvalRecord evalRecord, EvalTask evalTask) {
        EvalAlgorithmRef evalAlgorithmRef = evalAlgorithmRefMap.get(evalVariableRef.getFormatAlgorithmId());
        EvalLog.info(evalRecord, evalVariableRef, "format algorithm start...");

        if (!evalVariableRef.getProgress().equals(EvalVariableRef.Progress.FORMATTING.getCode())) {
            EvalLog.info(evalRecord, evalVariableRef, "format algorithm has been completed, continue.");
            return startMatch(evalVariableRef, evalAlgorithmRefMap, evalRecord, evalTask);
        }

        // 已经格式化数据数量
        int count = evalVariableRefProbeDataService.lambdaQuery().eq(EvalVariableRefProbeData::getVariableId, evalVariableRef.getResourceId()).count();

        // 分页执行格式化算法
        evalVariableRef.setFormatStartTime(new Date());
        String result = executeFormat(evalVariableRef, evalAlgorithmRef, evalRecord, evalTask, count);
        evalVariableRef.setFormatEndTime(new Date());

        if (result == null) {
            evalVariableRef.setProgress(EvalVariableRef.Progress.MATCHING.getCode());
            getBaseMapper().updateById(evalVariableRef);

            EvalLog.info(evalRecord, evalVariableRef, "format algorithm completed.");
            return startMatch(evalVariableRef, evalAlgorithmRefMap, evalRecord, evalTask);
        }

        return result;
    }

    /**
     * 执行格式
     *
     * @param evalVariableRef  eval 变量 ref
     * @param evalAlgorithmRef eval 算法 ref
     * @param evalRecord       EVAL 记录
     * @param evalTask         评估任务
     * @param limit            限制
     * @return {@code String }
     */
    private String executeFormat(EvalVariableRef evalVariableRef, EvalAlgorithmRef evalAlgorithmRef, EvalRecord evalRecord, EvalTask evalTask, int limit) {

        // 变量项探针采集数据
        List<EvalProbeData> evalProbeDataList = evalProbeDataService.lambdaQuery()
                .eq(EvalProbeData::getSceneId, evalTask.getMpSceneId())
                .eq(EvalProbeData::getDeviceId, evalVariableRef.getDeviceId())
                .eq(EvalProbeData::getProbeId, evalVariableRef.getProbeId())
                .ge(EvalProbeData::getCollectionTime, evalRecord.getStartTime())
                .le(EvalProbeData::getCollectionTime, evalRecord.getEndTime())
                .orderByAsc(EvalProbeData::getCollectionTime)
                .last(String.format("limit %s, %s", limit, onceFormatSize))
                .list();

        // 时间范围内无采集数据情况
        if (evalProbeDataList.isEmpty()) {
            if (limit == 0) {
                return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.VARIABLE_DID_NOT_GET_PROBE_DATA), evalVariableRef.getName());
            } else {
                return null;
            }
        }

        // 当前采集到的数据量
        int perFormatSize = evalProbeDataList.size();
        EvalLog.info(evalRecord, evalVariableRef, String.format("size %s format algorithm calculate start...", perFormatSize));

        // 构建格式化算法数据
        FormatData data = new FormatData();
        data.setData(evalProbeDataList.stream().map(EvalProbeData::getContent).collect(Collectors.toList()));

        List<Serializable> otherArguments = evalAlgorithmRefService.buildOtherArguments(evalVariableRef.getFormatParams());

        EvalLog.debug(evalRecord, evalVariableRef, String.format("code: %s", evalAlgorithmRef.getCode()));
        EvalLog.debug(evalRecord, evalVariableRef, String.format("other arguments: %s", otherArguments));
        EvalLog.debug(evalRecord, evalVariableRef, String.format("data argument: %s", JsonUtil.toJsonString(data)));

        // 运行匹配算法
        List<String> formatReturn;
        try {
            formatReturn = remotePythonService.formatPerform(evalAlgorithmRef.getResourceId(), evalAlgorithmRef.getCode(), data, otherArguments);
        } catch (RemoteServiceException e) {
            if (e.getMessage().equals(ErrorCode.ALGORITHM_RETURN_NULL)) {
                return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.FORMAT_ALGORITHM_RESULT_IS_INVALID), evalVariableRef.getName());
            }
            return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.VARIABLE_FORMAT_EXECUTE_FAILED), evalVariableRef.getName());
        }
        EvalLog.debug(evalRecord, evalVariableRef, String.format("size %s format algorithm calculate result %s", perFormatSize, formatReturn));

        List<EvalVariableRefProbeData> evalVariableRefProbeDataList = Lists.newArrayList();
        for (int i = 0; i < formatReturn.size(); i++) {
            String formatValue = formatReturn.get(i);
            // 格式化算法返回值长度过长
            if (formatValue.length() > formatValueLengthLimit) {
                return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.FORMAT_ALGORITHM_RESULT_IS_INVALID), evalVariableRef.getName());
            }
            EvalVariableRefProbeData object = new EvalVariableRefProbeData();
            EvalProbeData originData = evalProbeDataList.get(i);
            object.setProbeDataId(originData.getId());
            object.setVariableId(evalVariableRef.getResourceId());
            object.setFormattedValue(formatValue);
            object.setStatus(EvalVariableRefProbeData.Status.NONE.getCode());
            object.setTime(originData.getCollectionTime());

            evalVariableRefProbeDataList.add(object);
        }

        evalVariableRefProbeDataService.saveBatch(evalVariableRefProbeDataList);
        if (perFormatSize == onceFormatSize) {
            return executeFormat(evalVariableRef, evalAlgorithmRef, evalRecord, evalTask, limit + onceFormatSize);
        }

        return null;
    }

    /**
     * 开始对战
     *
     * @param evalVariableRef     eval 变量 ref
     * @param evalAlgorithmRefMap EVAL 算法 Ref Map
     * @param evalRecord          EVAL 记录
     * @param evalTask            评估任务
     * @return {@code String }
     */
    private String startMatch(EvalVariableRef evalVariableRef, Map<String, EvalAlgorithmRef> evalAlgorithmRefMap, EvalRecord evalRecord, EvalTask evalTask) {
        EvalAlgorithmRef evalAlgorithmRef = evalAlgorithmRefMap.get(evalVariableRef.getMatchAlgorithmId());
        EvalLog.info(evalRecord, evalVariableRef, "match algorithm start...");

        if (!evalVariableRef.getProgress().equals(EvalVariableRef.Progress.MATCHING.getCode())) {
            EvalLog.info(evalRecord, evalVariableRef, "match algorithm has been completed, continue.");
            return startMark(evalVariableRef, evalAlgorithmRefMap, evalRecord, evalTask);
        }

        List<EvalVariableRefProbeDataContext> evalVariableRefProbeDataContextList = evalVariableRefProbeDataService
                .variableProbeDataResult(evalVariableRef.getResourceId());
        MatchData data = new MatchData();

        // 构建匹配算法数据
        data.setBaseTime(evalVariableRefProbeDataContextList.get(0).getTime().getTime());
        data.setData(Lists.newArrayList());
        evalVariableRefProbeDataContextList.forEach(variableRefProbeDataContext -> {
            MatchItemData itemData = new MatchItemData();
            itemData.setT(variableRefProbeDataContext.getTime().getTime() - data.getBaseTime());
            itemData.setV(variableRefProbeDataContext.getFormattedValue());

            data.getData().add(itemData);
        });

        List<Serializable> otherArguments = evalAlgorithmRefService.buildOtherArguments(evalVariableRef.getMatchParams());
        EvalLog.debug(evalRecord, evalVariableRef, String.format("code: %s", evalAlgorithmRef.getCode()));
        EvalLog.debug(evalRecord, evalVariableRef, String.format("other arguments: %s", otherArguments));
        EvalLog.debug(evalRecord, evalVariableRef, String.format("data argument: %s", JsonUtil.toJsonString(data)));

        // 运行匹配算法
        List<Integer> matchReturn;
        evalVariableRef.setMatchStartTime(new Date());
        try {
            matchReturn = remotePythonService.matchPerform(evalAlgorithmRef.getResourceId(), evalAlgorithmRef.getCode(), data, otherArguments);
        } catch (RemoteServiceException e) {
            if (e.getMessage().equals(ErrorCode.ALGORITHM_RETURN_NULL)) {
                return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.MATCH_ALGORITHM_RESULT_IS_INVALID), evalVariableRef.getName());
            }
            return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.VARIABLE_MATCH_EXECUTE_FAILED), evalVariableRef.getName());
        }
        evalVariableRef.setMatchEndTime(new Date());

        EvalLog.debug(evalRecord, evalVariableRef, String.format("match algorithm calculate result %s", matchReturn));

        List<EvalVariableRefProbeData> evalVariableRefProbeDataList = evalVariableRefProbeDataService.lambdaQuery()
                .eq(EvalVariableRefProbeData::getVariableId, evalVariableRef.getResourceId())
                .list();

        // 执行失败, 返回值异常
        if (matchReturn.size() != evalVariableRefProbeDataList.size()) {
            return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.MATCH_ALGORITHM_RESULT_IS_INVALID), evalVariableRef.getName());
        }

        for (int i = 0; i < evalVariableRefProbeDataList.size(); i++) {
            // 避免返回值存在[0, 1]外的值
            if (matchReturn.get(i).equals(EvalVariableRefProbeData.Status.MATCHED.getCode())) {
                evalVariableRefProbeDataList.get(i).setStatus(EvalVariableRefProbeData.Status.MATCHED.getCode());
            }
        }

        evalVariableRef.setProgress(EvalVariableRef.Progress.MARKING.getCode());
        transactionTemplate.execute(status -> {
            evalVariableRefProbeDataService.updateBatchById(evalVariableRefProbeDataList);
            return SqlHelper.retBool(getBaseMapper().updateById(evalVariableRef));
        });

        EvalLog.info(evalRecord, evalVariableRef, "match algorithm completed.");
        return startMark(evalVariableRef, evalAlgorithmRefMap, evalRecord, evalTask);
    }

    /**
     * 开始标记
     *
     * @param evalVariableRef     eval 变量 ref
     * @param evalAlgorithmRefMap EVAL 算法 Ref Map
     * @param evalRecord          EVAL 记录
     * @param evalTask            评估任务
     * @return {@code String }
     */
    private String startMark(EvalVariableRef evalVariableRef, Map<String, EvalAlgorithmRef> evalAlgorithmRefMap, EvalRecord evalRecord, EvalTask evalTask) {
        EvalAlgorithmRef evalAlgorithmRef = evalAlgorithmRefMap.get(evalVariableRef.getMarkAlgorithmId());
        EvalLog.info(evalRecord, evalVariableRef, "mark algorithm start...");

        if (!evalVariableRef.getProgress().equals(EvalVariableRef.Progress.MARKING.getCode())) {
            EvalLog.info(evalRecord, evalVariableRef, "mark algorithm has been completed, continue.");
            return null;
        }
        List<EvalVariableRefProbeDataContext> evalVariableRefProbeDataContextList = evalVariableRefProbeDataService
                .variableProbeDataResult(evalVariableRef.getResourceId());

        MarkData data = new MarkData();
        // 构建评分算法数据
        data.setBaseTime(evalVariableRefProbeDataContextList.get(0).getTime().getTime());
        data.setData(Lists.newArrayList());
        evalVariableRefProbeDataContextList.forEach(variableRefProbeDataContext -> {
            MarkItemData itemData = new MarkItemData();
            itemData.setT(variableRefProbeDataContext.getTime().getTime() - data.getBaseTime());
            itemData.setV(variableRefProbeDataContext.getFormattedValue());
            itemData.setM(variableRefProbeDataContext.getStatus());

            data.getData().add(itemData);
        });

        List<Serializable> otherArguments = evalAlgorithmRefService.buildOtherArguments(evalVariableRef.getMarkParams());
        EvalLog.debug(evalRecord, evalVariableRef, String.format("code: %s", evalAlgorithmRef.getCode()));
        EvalLog.debug(evalRecord, evalVariableRef, String.format("other arguments: %s", otherArguments));
        EvalLog.debug(evalRecord, evalVariableRef, String.format("data argument: %s", JsonUtil.toJsonString(data)));

        // 运行评分算法
        Integer score;
        evalVariableRef.setMarkStartTime(new Date());
        try {
            score = remotePythonService.markPerform(evalAlgorithmRef.getResourceId(), evalAlgorithmRef.getCode(), data, otherArguments);
        } catch (RemoteServiceException e) {
            if (e.getMessage().equals(ErrorCode.ALGORITHM_RETURN_NULL)) {
                return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.MARK_ALGORITHM_RESULT_IS_INVALID), evalVariableRef.getName());
            }
            return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.VARIABLE_MARK_EXECUTE_FAILED), evalVariableRef.getName());
        }

        if (score > 100 || 0 > score) {
            return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.MARK_ALGORITHM_RESULT_IS_INVALID), evalVariableRef.getName());
        }

        evalVariableRef.setMarkEndTime(new Date());

        EvalLog.debug(evalRecord, evalVariableRef, String.format("mark algorithm calculate result %s", score));

        evalVariableRef.setScore(score);
        evalVariableRef.setProgress(EvalVariableRef.Progress.END.getCode());
        getBaseMapper().updateById(evalVariableRef);

        EvalLog.info(evalRecord, evalVariableRef, "mark algorithm completed.");

        return null;
    }

    /**
     * 图
     *
     * @param variableId 变量id
     * @return {@code EvalVariableGraphRsp }
     */
    @Override
    public EvalVariableGraphRsp graph(String variableId) {
        EvalVariableGraphRsp result = new EvalVariableGraphRsp();
        result.setTime(Lists.newArrayList());
        result.setValue(Lists.newArrayList());
        result.setIsMatched(Lists.newArrayList());

        List<EvalVariableRefProbeData> evalVariableRefProbeDataList = evalVariableRefProbeDataService.lambdaQuery()
                .eq(EvalVariableRefProbeData::getVariableId, variableId).list();
        evalVariableRefProbeDataList.sort(Comparator.comparing(EvalVariableRefProbeData::getTime));

        evalVariableRefProbeDataList.forEach(item -> {
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
     * 源数据
     *
     * @param page       分页
     * @param variableId 变量id
     * @return {@code ListRsp<EvalVariableDataPageRsp> }
     */
    @Override
    public ListRsp<EvalVariableDataPageRsp> originData(Page page, String variableId) {
        return evalVariableRefProbeDataService.variableProbeData(page, variableId);
    }

    /**
     * result 变量
     *
     * @param variableId 变量id
     * @return {@code EvalRecordResultVariableRsp }
     */
    @Override
    public EvalRecordResultVariableRsp resultVariable(String variableId) {
        EvalVariableRef evalVariableRef = getById(variableId);
        if (evalVariableRef == null) {
            log.error("Resource not found! variableId = [{}]", variableId);
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        EvalRecordResultVariableRsp evalRecordResultVariableRsp = new EvalRecordResultVariableRsp();
        evalRecordResultVariableRsp.setResourceId(evalVariableRef.getResourceId());
        evalRecordResultVariableRsp.setName(evalVariableRef.getName());

        List<String> algorithmIds = Arrays.asList(
                evalVariableRef.getFormatAlgorithmId(),
                evalVariableRef.getMatchAlgorithmId(),
                evalVariableRef.getMarkAlgorithmId()
        );

        List<EvalAlgorithmRef> evalAlgorithmRefList = evalAlgorithmRefService.lambdaQuery().in(EvalAlgorithmRef::getResourceId, algorithmIds).list();

        evalAlgorithmRefList.forEach(evalAlgorithmRef -> {
            EvalRecordResultAlgorithmRsp evalRecordResultAlgorithmRsp = new EvalRecordResultAlgorithmRsp();
            BeanUtils.copyProperties(evalAlgorithmRef, evalRecordResultAlgorithmRsp);

            if (evalAlgorithmRef.getType().equals(EvalAlgorithm.Type.FORMAT_ALGORITHM.getCode())) {
                evalRecordResultAlgorithmRsp.setParams(JsonUtil.toJsonObjectArray(evalVariableRef.getFormatParams(), AlgorithmParamContext.class));
                evalRecordResultVariableRsp.setFormatAlgorithm(evalRecordResultAlgorithmRsp);
            } else if (evalAlgorithmRef.getType().equals(EvalAlgorithm.Type.MATCHING_ALGORITHM.getCode())) {
                evalRecordResultAlgorithmRsp.setParams(JsonUtil.toJsonObjectArray(evalVariableRef.getMatchParams(), AlgorithmParamContext.class));
                evalRecordResultVariableRsp.setMatchAlgorithm(evalRecordResultAlgorithmRsp);
            } else {
                evalRecordResultAlgorithmRsp.setParams(JsonUtil.toJsonObjectArray(evalVariableRef.getMarkParams(), AlgorithmParamContext.class));
                evalRecordResultVariableRsp.setMarkAlgorithm(evalRecordResultAlgorithmRsp);
            }
        });

        return evalRecordResultVariableRsp;
    }
}
