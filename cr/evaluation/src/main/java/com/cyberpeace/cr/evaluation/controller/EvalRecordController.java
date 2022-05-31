package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
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
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "靶场测评-评估")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/record")
public class EvalRecordController {
    @Autowired
    private EvalTaskService evalTaskService;

    @Autowired
    private EvalRecordService evalRecordService;

    @Autowired
    private EvalIndexRefService evalIndexRefService;

    @Autowired
    private EvalVariableRefService evalVariableRefService;

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

    @ApiOperation("评估状态枚举（查询用）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/status")
    public BaseRsp<List<EvalRoomIntPairRsp>> statusEnum() {
        return new BaseRsp<>(evalRecordService.recordStatusEnum());
    }

    @ApiOperation("创建评估")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @PostMapping("")
    public BaseRsp<EvalRecordRsp> create(@RequestBody EvalRecordAddReq requestData) {
        return new BaseRsp<>(evalRecordService.create(requestData));
    }

    @ApiOperation("更新评估")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @PatchMapping("")
    public BaseRsp<EvalRecordRsp> update(@RequestBody EvalRecordAddReq requestData) {
        return new BaseRsp<>(evalRecordService.update(requestData));
    }

    @ApiOperation("获取单个评估")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("")
    public BaseRsp<EvalRecordRsp> get(@RequestParam("resource_id") String recordId) {
        return new BaseRsp<>(evalRecordService.get(recordId));
    }

    @ApiOperation("评估任务删除")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @DeleteMapping("")
    public BaseRsp<Boolean> delete(@RequestBody BatchDeleteReq requestData) {
        return new BaseRsp<>(evalRecordService.delete(requestData.getIds()));
    }

    @ApiOperation("场景终端节点获取")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/nodes")
    public BaseRsp<List<EvalRoomPairRsp>> getNodes(@RequestParam("room_id") String roomId) {
        return new BaseRsp<>(evalRecordService.getNodes(roomId));
    }

    @ApiOperation("终端使用探针信息获取")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/probes")
    public BaseRsp<List<EvalRoomPairRsp>> getProbes(@RequestParam("room_id") String roomId, @RequestParam("node_id") String nodeId) {
        return new BaseRsp<>(evalRecordService.getProbes(roomId, nodeId));
    }

    @ApiOperation("终端使用探针信息获取-批量")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/probes-map")
    public BaseRsp<Map<String, List<EvalRoomPairRsp>>> getProbes(@RequestParam("room_id") String roomId) {
        return new BaseRsp<>(evalRecordService.getProbes(roomId));
    }

    @ApiOperation("场景使用终端/探针")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/probes/all")
    public BaseRsp<List<EvalRecordNodeProbeReq>> getNodeProbe(@RequestParam("room_id") String roomId) {
        return new BaseRsp<>(evalRecordService.getNodeProbe(roomId));
    }

    @PostConstruct
    public void init() {
        List<EvalRecord> evalRecordList = evalRecordService.lambdaQuery().eq(EvalRecord::getStatus, EvalRecord.Status.RUNNING.getCode()).list();
        if (evalRecordList.isEmpty()) {
            return;
        }
        Map<String, EvalTask> evalTaskMap = evalTaskService.lambdaQuery()
                .in(EvalTask::getResourceId, evalRecordList.stream().map(EvalRecord::getTaskId).collect(Collectors.toList()))
                .list()
                .stream().collect(Collectors.toMap(EvalTask::getResourceId, evalTask -> evalTask));

        evalRecordList.forEach(evalRecord -> {
            EvalTask evalTask = evalTaskMap.get(evalRecord.getTaskId());
            if (evalRecord.getEvalTime() == null) {
                evalRecord.setEvalTime(new Date());
            }
            evalRecordService.recordRun(evalRecord, evalTask, true);
        });
    }

    @ApiOperation("评估任务开启")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @PostMapping("/start")
    public BaseRsp<Boolean> start(@RequestBody EvalRecordStartReq requestData) {
        // 评估对象
        EvalRecord evalRecord = evalRecordService.getEntity(requestData.getResourceId());
        // 评估实例对象
        EvalTask evalTask = evalTaskService.getTask(evalRecord.getTaskId());

        // 设置评估状态、开始时间
        if (evalRecord.getStatus().equals(EvalRecord.Status.NOT_START.getCode())) {
            evalRecord.setStatus(EvalRecord.Status.RUNNING.getCode());
        }
        evalRecord.setEvalTime(new Date());
        boolean result = evalRecordService.updateById(evalRecord);

        // 开始执行
        evalRecordService.recordRun(evalRecord, evalTask, false);

        return new BaseRsp<>(result);
    }

    @ApiOperation("评估任务结果")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/result")
    public BaseRsp<EvalRecordResultRsp> result(@RequestParam("resource_id") String recordId) {
        return new BaseRsp<>(evalRecordService.result(recordId));
    }

    @ApiOperation("获取三级指标项下全部变量项")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/variables")
    public BaseRsp<List<EvalRoomPairRsp>> variables(@RequestParam("index_id") String indexId) {
        return new BaseRsp<>(evalIndexRefService.selectVariables(indexId));
    }

    @ApiOperation("变量项采集信息（折线图）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/variables/graph")
    public BaseRsp<EvalVariableGraphRsp> VariableGraph(@RequestParam("variable_id") String variableId) {
        return new BaseRsp<>(evalVariableRefService.graph(variableId));
    }

    @ApiOperation("变量项采集信息（原始数据）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/variables/data")
    public BaseRsp<ListRsp<EvalVariableDataPageRsp>> VariableData(EvalVariableProbeDataReq queryParams) {
        return new BaseRsp<>(evalVariableRefService.originData(queryParams.toPageParam(), queryParams.getVariableId()));
    }
}
