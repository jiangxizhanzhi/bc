package com.cyberpeace.cr.redvsblue.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.service.context.score.StartSceneRsp;
import com.cyberpeace.cr.redvsblue.controller.req.UniteAttackReq;
import com.cyberpeace.cr.redvsblue.service.CrUniteBusinessTaskService;
import com.cyberpeace.cr.redvsblue.vo.CrUniteBusinessTaskVO;
import com.cyberpeace.cr.redvsblue.vo.NetworkTaskVO;
import com.cyberpeace.cr.redvsblue.vo.NetworkTopoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "联合演训相关接口")
@RestController
@RequestMapping("/cr/unite")
@RequiresAuthentication
@Slf4j
public class CrUniteBusinessController {

    @Autowired
    private CrUniteBusinessTaskService crUniteBusinessTaskService;

    @ApiOperation("新增联合任务")
    @PostMapping("/business_task")
    public BaseRsp<Boolean> saveUniteBusinessTask(@RequestBody NetworkTaskVO networkTaskVO) {
        crUniteBusinessTaskService.saveUniteBusinessTask(networkTaskVO);
        return new BaseRsp<>();
    }

    @ApiOperation("新增联合设备信息")
    @PostMapping("/business_topo")
    public BaseRsp<Boolean> saveUniteBusinessTopo(@RequestBody NetworkTopoVO networkTopoVO) {
        crUniteBusinessTaskService.saveUniteBusinessTopo(networkTopoVO);
        return new BaseRsp<>();
    }

    @ApiOperation("获取任务信息")
    @GetMapping("/task_info")
    public BaseRsp<CrUniteBusinessTaskVO> getTaskInfoById(@ApiParam(name = "task_id", value = "任务id", required = true)
                                                              @RequestParam(value = "task_id", required = true) String taskId) {
        CrUniteBusinessTaskVO crUniteBusinessTaskVO = crUniteBusinessTaskService.getTaskById(taskId);
        return new BaseRsp<>(crUniteBusinessTaskVO);
    }

    @ApiOperation("构建结果上报")
    @PostMapping("/business/report_build_result")
    public BaseRsp<NetworkTopoVO> reportBuildResult(@RequestBody NetworkTopoVO networkTopoVO) {
        networkTopoVO = crUniteBusinessTaskService.reportBuildResult(networkTopoVO);
        return new BaseRsp<>(networkTopoVO);
    }

    @ApiOperation("启动场景")
    @PostMapping("/business/start")
    public BaseRsp<StartSceneRsp> startUniteBusines(@RequestBody NetworkTopoVO networkTopoVO) {
        String sceneId = crUniteBusinessTaskService.start(networkTopoVO);
        return new BaseRsp<>(new StartSceneRsp(sceneId));
    }

    @ApiOperation("关闭场景")
    @PostMapping("/business/stop")
    public BaseRsp<Boolean> stopUniteBusines(@RequestBody NetworkTopoVO networkTopoVO) {
        Boolean result = crUniteBusinessTaskService.stop(networkTopoVO);
        return new BaseRsp<>(result);
    }

    @ApiOperation("构建结果上报")
    @GetMapping("/business/test_report_result")
    public BaseRsp<NetworkTopoVO> testReportBuildResult(@RequestParam(value ="task_id") String taskId,
                                                        @RequestParam(value = "topo_id") String topoId) {
        NetworkTopoVO networkTopoVO = crUniteBusinessTaskService.retrieveNetworkTopologyByTaskId(taskId,topoId);
        return new BaseRsp<>(networkTopoVO);
    }

    @ApiOperation("发起攻击指令")
    @PostMapping("/business/attack")
    public BaseRsp<Boolean> attack(@RequestBody UniteAttackReq uniteAttackReq) {
        Boolean result = crUniteBusinessTaskService.attack(uniteAttackReq);
        return new BaseRsp<>();
    }

//        1. ojj定时任务五秒执行一次判断已启动的联合训练
    @ApiOperation("查联合训练链路状态")
    @GetMapping("/business/link_status")
    public BaseRsp<Boolean> linkStatus() {
        crUniteBusinessTaskService.linkStatus();
        return new BaseRsp<>();
    }

}
