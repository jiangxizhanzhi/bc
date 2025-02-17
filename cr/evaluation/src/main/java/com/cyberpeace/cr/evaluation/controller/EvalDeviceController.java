package com.cyberpeace.cr.evaluation.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.evaluation.controller.req.DeviceCommandExecReq;
import com.cyberpeace.cr.evaluation.controller.req.DeviceCommandModelReq;
import com.cyberpeace.cr.evaluation.controller.rsp.CrRbTargetCommandLogResp;
import com.cyberpeace.cr.evaluation.entity.CrRbTargetCommandLog;
import com.cyberpeace.cr.evaluation.entity.CrRbTargetCommandParameters;
import com.cyberpeace.cr.evaluation.entity.CrRbTargetCommandTemplates;
import com.cyberpeace.cr.evaluation.service.EvalDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "靶场测评-设备清单")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/device")
public class EvalDeviceController {

    @Autowired
    private EvalDeviceService evalDeviceService;


    @ApiOperation("设备列表")
    @GetMapping("/list")
    public BaseRsp<JSONArray> getDeviceList(
            @ApiParam(name = "resource_id", value = "模板id", required = true) @RequestParam(name = "resource_id") String resourceId,
            @ApiParam(name = "category", value = "设备类型", required = false) Integer category,
            @ApiParam(name = "name", value = "搜索关键字", required = false) String name) {
        return new BaseRsp<>(evalDeviceService.getDeviceList(resourceId, category, name));
    }

    @ApiOperation("设备统计")
    @GetMapping("/device/statistics")
    public BaseRsp<JSONObject> getDeviceStatistics(
            @ApiParam(name = "resource_id", value = "模板id", required = true) @RequestParam(name = "resource_id") String resourceId) {
        return new BaseRsp<>(evalDeviceService.getDeviceStatistics(resourceId));
    }

    @ApiOperation("当前设备可执行的模板命令")
    @PostMapping("/command/modes")
    public BaseRsp getDeviceCommandModels(@RequestBody DeviceCommandModelReq req) {
        List<CrRbTargetCommandTemplates> list = evalDeviceService.getDeviceCommandModels(req);
        return new BaseRsp<>(list);
    }

    @ApiOperation("当前模板命令可填写的参数")
    @GetMapping("/command/parameters")
    public BaseRsp getDeviceCommandParameters(@RequestParam(name = "id") String id) {
        List<CrRbTargetCommandParameters> list = evalDeviceService.getDeviceCommandParameters(id);
        return new BaseRsp<>(list);
    }

    @ApiOperation("实体设备执行命令")
    @PostMapping("/exec_cmd_by_agent")
    public BaseRsp execCmdByAgent(@RequestBody DeviceCommandExecReq req) {
        evalDeviceService.execCmdByAgent(req);
        return new BaseRsp<>();
    }

    @ApiOperation("实体设备执行命令历史记录")
    @GetMapping("/exec_cmd_by_agent/result")
    public BaseRsp execCmdByAgentRusult(@RequestParam(name = "id") String id, @RequestParam(name = "node_id") String nodeId) {
        List<CrRbTargetCommandLogResp> result = evalDeviceService.execCmdByAgentRusult(id, nodeId);
        return new BaseRsp<>(result);
    }

}
