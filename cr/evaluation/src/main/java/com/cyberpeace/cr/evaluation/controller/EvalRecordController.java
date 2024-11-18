package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordListReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordStartReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalVariableProbeDataReq;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.service.EvalIndexRefService;
import com.cyberpeace.cr.evaluation.service.EvalRecordService;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import com.cyberpeace.cr.evaluation.service.EvalVariableRefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * EVAL 记录控制器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Api(tags = "靶场测评-评估")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/record")
@Slf4j
public class EvalRecordController {
    /**
     * 评估任务Service
     *
     * @see EvalTaskService
     */
    @Autowired
    private EvalTaskService evalTaskService;

    /**
     * 评估记录Service
     *
     * @see EvalRecordService
     */
    @Autowired
    private EvalRecordService evalRecordService;

    /**
     * 评估指数裁判Service
     *
     * @see EvalIndexRefService
     */
    @Autowired
    private EvalIndexRefService evalIndexRefService;

    /**
     * 评估变量裁判Service
     *
     * @see EvalVariableRefService
     */
    @Autowired
    private EvalVariableRefService evalVariableRefService;

    /**
     * 列表
     *
     * @param queryParams 查询参数
     * @return {@code BaseRsp<ListRsp<EvalRecordListRsp>> }
     * @throws
     * @see EvalRecordListReq
     * @see BaseRsp<ListRsp<EvalRecordListRsp>>
     */
    @ApiOperation("获取靶场测评实例评估（分页）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/list")
    public BaseRsp<ListRsp<EvalRecordListRsp>> list(EvalRecordListReq queryParams) {
        return new BaseRsp<>(evalRecordService.pageList(
                queryParams.toPageParam(),
                queryParams.getTaskId(),
                queryParams.getStatus(),
                queryParams.getPattern()
        ));
    }

    /**
     * 状态枚举
     *
     * @return {@code BaseRsp<List<EvalRoomIntPairRsp>> }
     * @throws
     * @see BaseRsp<List<EvalRoomIntPairRsp>>
     */
    @ApiOperation("评估状态枚举（查询用）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/status")
    public BaseRsp<List<EvalRoomIntPairRsp>> statusEnum() {
        return new BaseRsp<>(evalRecordService.recordStatusEnum());
    }

    /**
     * 创造
     *
     * @param requestData 请求数据
     * @return {@code BaseRsp<EvalRecordRsp> }
     * @throws
     * @see EvalRecordAddReq
     * @see BaseRsp<EvalRecordRsp>
     */
    @ApiOperation("创建评估")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @PostMapping("")
    public BaseRsp<EvalRecordRsp> create(@RequestBody EvalRecordAddReq requestData) {
        requestData.validate();
        evalRecordService.isExist(requestData);
        return new BaseRsp<>(evalRecordService.create(requestData));
    }

    /**
     * 更新
     *
     * @param requestData 请求数据
     * @return {@code BaseRsp<EvalRecordRsp> }
     * @throws
     * @see EvalRecordAddReq
     * @see BaseRsp<EvalRecordRsp>
     */
    @ApiOperation("更新评估")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @PatchMapping("")
    public BaseRsp<EvalRecordRsp> update(@RequestBody EvalRecordAddReq requestData) {
        requestData.validate();
        evalRecordService.isExist(requestData);
        return new BaseRsp<>(evalRecordService.update(requestData));
    }

    /**
     * 获取
     *
     * @param recordId 记录id
     * @return {@code BaseRsp<EvalRecordRsp> }
     * @throws
     * @see String
     * @see BaseRsp<EvalRecordRsp>
     */
    @ApiOperation("获取单个评估")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("")
    public BaseRsp<EvalRecordRsp> get(@ApiParam(name = "resource_id", value = "评估ID") @RequestParam("resource_id") String recordId) {
        return new BaseRsp<>(evalRecordService.get(recordId));
    }

    /**
     * 删除
     *
     * @param requestData 请求数据
     * @return {@code BaseRsp<Boolean> }
     * @throws
     * @see BatchDeleteReq
     * @see BaseRsp<Boolean>
     */
    @ApiOperation("评估任务删除")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @DeleteMapping("")
    public BaseRsp<Boolean> delete(@RequestBody BatchDeleteReq requestData) {
        return new BaseRsp<>(evalRecordService.delete(requestData.getIds()));
    }

    /**
     * 获取节点
     *
     * @param roomId 房间id
     * @return {@code BaseRsp<List<EvalRoomPairRsp>> }
     * @throws
     * @see String
     * @see BaseRsp<List<EvalRoomPairRsp>>
     */
    @ApiOperation("场景终端节点获取")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/nodes")
    public BaseRsp<List<EvalRoomPairRsp>> getNodes(@ApiParam(name = "room_id", value = "测评任务ID") @RequestParam("room_id") String roomId) {
        return new BaseRsp<>(evalRecordService.getNodes(roomId));
    }

    /**
     * 获取探针
     *
     * @param roomId 房间id
     * @param nodeId 节点id
     * @return {@code BaseRsp<List<EvalRoomPairRsp>> }
     * @throws
     * @see String
     * @see String
     * @see BaseRsp<List<EvalRoomPairRsp>>
     */
    @ApiOperation("终端使用探针信息获取")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/probes")
    public BaseRsp<List<EvalRoomPairRsp>> getProbes(@ApiParam(name = "room_id", value = "测评任务ID") @RequestParam("room_id") String roomId,
                                                    @ApiParam(name = "node_id", value = "场景节点ID") @RequestParam("node_id") String nodeId) {
        return new BaseRsp<>(evalRecordService.getProbes(roomId, nodeId));
    }

    /**
     * 获取探针
     *
     * @param roomId 房间id
     * @return {@code BaseRsp<Map<String, List<EvalRoomPairRsp>>> }
     * @throws
     * @see String
     * @see BaseRsp<Map<String, List<EvalRoomPairRsp>>>
     */
    @ApiOperation("终端使用探针信息获取-批量")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/probes-map")
    public BaseRsp<Map<String, List<EvalRoomPairRsp>>> getProbes(@ApiParam(name = "room_id", value = "测评任务ID") @RequestParam("room_id") String roomId) {
        return new BaseRsp<>(evalRecordService.getProbes(roomId));
    }

    /**
     * 获取节点探针
     *
     * @param roomId 房间id
     * @return {@code BaseRsp<List<EvalRecordNodeProbeReq>> }
     * @throws
     * @see String
     * @see BaseRsp<List<EvalRecordNodeProbeReq>>
     */
    @ApiOperation("场景使用终端/探针")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/probes/all")
    public BaseRsp<List<EvalRecordNodeProbeReq>> getNodeProbe(@ApiParam(name = "room_id", value = "测评任务ID") @RequestParam("room_id") String roomId) {
        return new BaseRsp<>(evalRecordService.getNodeProbe(roomId));
    }

    /**
     * 开始
     *
     * @param requestData 请求数据
     * @return {@code BaseRsp<Boolean> }
     * @throws
     * @see EvalRecordStartReq
     * @see BaseRsp<Boolean>
     */
    @ApiOperation("评估任务开启")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @PostMapping("/start")
    public BaseRsp<Boolean> start(@RequestBody EvalRecordStartReq requestData) {
        // 评估对象
        EvalRecord evalRecord = evalRecordService.getById(requestData.getResourceId());
        if (evalRecord == null) {
            log.error("Evaluation not found! resourceId = [{}]", requestData.getResourceId());
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        if (evalRecord.getStatus().equals(EvalRecord.Status.RUNNING.getCode())) {
            log.error("Evaluation has been initiated! resourceId = [{}]", requestData.getResourceId());
            throw new BusinessException(CrBusinessErrorCode.EVALUATION_HAS_BEEN_INITIATED_ERROR);
        }

        // 评估实例对象
        EvalTask evalTask = evalTaskService.getTask(evalRecord.getTaskId());

        // 设置评估状态、开始时间
        if (evalRecord.getStatus().equals(EvalRecord.Status.NOT_START.getCode())) {
            evalRecord.setStatus(EvalRecord.Status.RUNNING.getCode());
        }
        evalRecord.setEvalTime(new Date());
        boolean result = evalRecordService.updateById(evalRecord);

        // 开始执行
        log.info(String.format("Range Test evaluation %s START.", evalRecord.getName()));
        evalRecordService.recordRun(evalRecord, evalTask, false, CommonContext.getLanguage());

        return new BaseRsp<>(result);
    }

    /**
     * 结果
     *
     * @param recordId 记录id
     * @return {@code BaseRsp<EvalRecordResultRsp> }
     * @throws
     * @see String
     * @see BaseRsp<EvalRecordResultRsp>
     */
    @ApiOperation("评估任务结果")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/result")
    public BaseRsp<EvalRecordResultRsp> result(@ApiParam(name = "resource_id", value = "评估ID") @RequestParam("resource_id") String recordId) {
        return new BaseRsp<>(evalRecordService.result(recordId));
    }

    /**
     * 变量
     *
     * @param indexId 指数id
     * @return {@code BaseRsp<List<EvalRoomPairRsp>> }
     * @throws
     * @see String
     * @see BaseRsp<List<EvalRoomPairRsp>>
     */
    @ApiOperation("获取某一级指标项下全部变量项")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/variables")
    public BaseRsp<List<EvalRoomPairRsp>> variables(@ApiParam(name = "index_id", value = "指标项ID") @RequestParam("index_id") String indexId) {
        return new BaseRsp<>(evalIndexRefService.selectVariables(indexId));
    }

    /**
     * 变量图
     *
     * @param variableId 变量id
     * @return {@code BaseRsp<EvalVariableGraphRsp> }
     * @throws
     * @see String
     * @see BaseRsp<EvalVariableGraphRsp>
     */
    @ApiOperation("变量项采集信息（折线图）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/variables/graph")
    public BaseRsp<EvalVariableGraphRsp> variableGraph(@ApiParam(name = "variable_id", value = "变量项ID") @RequestParam("variable_id") String variableId) {
        return new BaseRsp<>(evalVariableRefService.graph(variableId));
    }

    /**
     * 变量数据
     *
     * @param queryParams 查询参数
     * @return {@code BaseRsp<ListRsp<EvalVariableDataPageRsp>> }
     * @throws
     * @see EvalVariableProbeDataReq
     * @see BaseRsp<ListRsp<EvalVariableDataPageRsp>>
     */
    @ApiOperation("变量项采集信息（原始数据）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/variables/data")
    public BaseRsp<ListRsp<EvalVariableDataPageRsp>> variableData(EvalVariableProbeDataReq queryParams) {
        return new BaseRsp<>(evalVariableRefService.originData(queryParams.toPageParam(), queryParams.getVariableId()));
    }

    /**
     * 结果索引
     *
     * @param indexId 指数id
     * @return {@code BaseRsp<EvalRecordResultIndexRsp> }
     * @throws
     * @see String
     * @see BaseRsp<EvalRecordResultIndexRsp>
     */
    @ApiOperation("评估任务结果（指标项信息）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/result/index")
    public BaseRsp<EvalRecordResultIndexRsp> resultIndex(@ApiParam(name = "index_id", value = "指标项ID") @RequestParam("index_id") String indexId) {
        return new BaseRsp<>(evalIndexRefService.resultIndex(indexId));
    }

    /**
     * result 变量
     *
     * @param variableId 变量id
     * @return {@code BaseRsp<EvalRecordResultVariableRsp> }
     * @throws
     * @see String
     * @see BaseRsp<EvalRecordResultVariableRsp>
     */
    @ApiOperation("评估任务结果（变量项信息）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/result/variable")
    public BaseRsp<EvalRecordResultVariableRsp> resultVariable(@ApiParam(name = "variable_id", value = "变量项ID") @RequestParam("variable_id") String variableId) {
        return new BaseRsp<>(evalVariableRefService.resultVariable(variableId));
    }

    /**
     * 转储 评估记录2 PDF
     *
     * @param httpServletResponse HTTP Servlet 响应
     * @param resourceId          资源id
     * @param taskId              任务id
     * @throws
     * @see HttpServletResponse
     * @see String
     * @see String
     */
    @ApiOperation("评估详情导出为PDF")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/train2pdf")
    public void dumpEvalRecord2PDF(HttpServletResponse httpServletResponse,
                                   @ApiParam(name = "resource_id", value = "评估ID") @RequestParam("resource_id") String resourceId,
                                   @ApiParam(name = "task_id", value = "测评任务ID") @RequestParam("task_id")String taskId) {
        FileUtil.setResponse(new File(evalRecordService.dumpEvalRecordPath(resourceId, taskId)), true, httpServletResponse);
    }

    /**
     * 获取记录结果列表
     *
     * @param resourceId 资源id
     * @return {@code BaseRsp<List<EvalSystemResultRsp>> }
     * @throws
     * @see String
     * @see BaseRsp<List<EvalSystemResultRsp>>
     */
    @ApiOperation("评估任务结果-5轮评估记录")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/result/list")
    public BaseRsp<List<EvalSystemResultRsp>> getRecordResultList(@ApiParam(name = "resource_id", value = "评估ID") @RequestParam("resource_id") String resourceId) {
        return new BaseRsp<>(evalRecordService.getAllCycleResult(resourceId));
    }
}
