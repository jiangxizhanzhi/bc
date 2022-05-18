package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordListReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalVariableProbeDataReq;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.service.EvalIndexRefService;
import com.cyberpeace.cr.evaluation.service.EvalRecordService;
import com.cyberpeace.cr.evaluation.service.EvalVariableRefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "靶场测评-评估")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/record")
public class EvalRecordController {
    @Autowired
    private EvalRecordService evalRecordService;

    @Autowired
    private EvalIndexRefService evalIndexRefService;

    @Autowired
    private EvalVariableRefService evalVariableRefService;

    @ApiOperation("获取靶场测评实例评估（分页）")
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
    @GetMapping("/status")
    public BaseRsp<List<EvalRoomIntPairRsp>> statusEnum() {
        return new BaseRsp<>(evalRecordService.recordStatusEnum());
    }

    @ApiOperation("创建评估")
    @PostMapping("")
    public BaseRsp<EvalRecordRsp> create(@RequestBody EvalRecordAddReq requestData) {
        return new BaseRsp<>(evalRecordService.create(requestData));
    }

    @ApiOperation("更新评估")
    @PatchMapping("")
    public BaseRsp<EvalRecordRsp> update(@RequestBody EvalRecordAddReq requestData) {
        return new BaseRsp<>(evalRecordService.update(requestData));
    }

    @ApiOperation("获取单个评估")
    @GetMapping("")
    public BaseRsp<EvalRecordRsp> get(@RequestParam("resource_id") String recordId) {
        return new BaseRsp<>(evalRecordService.get(recordId));
    }

    @ApiOperation("评估任务删除")
    @DeleteMapping("")
    public BaseRsp<Boolean> delete(@RequestBody BatchDeleteReq requestData) {
        return new BaseRsp<>(evalRecordService.delete(requestData.getIds()));
    }

    @ApiOperation("场景终端节点获取")
    @GetMapping("/nodes")
    public BaseRsp<List<EvalRoomPairRsp>> getNodes(@RequestParam("room_id") String roomId) {
        return new BaseRsp<>(evalRecordService.getNodes(roomId));
    }

    @ApiOperation("终端使用探针信息获取")
    @GetMapping("/probes")
    public BaseRsp<List<EvalRoomPairRsp>> getProbes(@RequestParam("room_id") String roomId, @RequestParam("node_id") String nodeId) {
        return new BaseRsp<>(evalRecordService.getProbes(roomId, nodeId));
    }

    @ApiOperation("评估任务开启")
    @PostMapping("/start")
    public BaseRsp<Boolean> start(@RequestParam("resource_id") String recordId) {
        return new BaseRsp<>(evalRecordService.startRecord(recordId));
    }

    @ApiOperation("评估任务结果")
    @GetMapping("/result")
    public BaseRsp<EvalRecordResultRsp> result(@RequestParam("resource_id") String recordId) {
        return new BaseRsp<>(evalRecordService.result(recordId));
    }

    @ApiOperation("获取三级指标项下全部变量项")
    @GetMapping("/variables")
    public BaseRsp<List<EvalRoomPairRsp>> variables(@RequestParam("index_id") String indexId) {
        return new BaseRsp<>(evalIndexRefService.selectVariables(indexId));
    }

    @ApiOperation("变量项采集信息（折线图）")
    @GetMapping("/variables/graph")
    public BaseRsp<EvalVariableGraphRsp> VariableGraph(@RequestParam("variable_id") String variableId) {
        return new BaseRsp<>(evalVariableRefService.graph(variableId));
    }

    @ApiOperation("变量项采集信息（原始数据）")
    @GetMapping("/variables/data")
    public BaseRsp<ListRsp<EvalVariableDataPageRsp>> VariableData(EvalVariableProbeDataReq queryParams) {
        return new BaseRsp<>(evalVariableRefService.originData(queryParams.toPageParam(), queryParams.getVariableId()));
    }
}
