package com.cyberpeace.scene.controller;


import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.scene.controller.rsp.SceneConfigDataRsp;
import com.cyberpeace.scene.dao.entity.TopologyEntity;
import com.cyberpeace.scene.service.SceneService;
import com.cyberpeace.scene.service.TopologyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :   SN
 * @date :   2023/12/20 15:21
 * @desc :
 **/
@Api(tags = "场景API", value = "场景")
@RestController
@RequestMapping("/scene")
public class ScenesController {

    @Autowired
    private TopologyService topologyService;

    @Autowired
    private SceneService sceneService;

    @GetMapping("/topology")
    @ApiOperation(value = "获取拓扑结构")
    public BaseRsp getTopology(@ApiParam(value = "拓扑结构id", name = "topology_id")@RequestParam("topology_id") String topologyId){
        JSONObject topology = topologyService.getTopology(topologyId);
        return new BaseRsp<>(topology);
    }

    @GetMapping("/start")
    @ApiOperation(value = "启动场景")
    public BaseRsp start(@ApiParam(name = "topology_id", value = "拓扑结构id") @RequestParam("topology_id") String topologyId,
                    @ApiParam(value = "实例id", name = "instance_id")@RequestParam("instance_id")String instanceId){
        // 验证场景是否超限
        sceneService.verify();
        sceneService.startScene(topologyId, instanceId);
        return new BaseRsp<>();
    }

    @GetMapping("/console")
    @ApiOperation(value = "控制台接入")
    public BaseRsp console(@ApiParam(name = "nodeId", value = "主机实例id")@RequestParam String nodeId,
                           @ApiParam(name = "type", value = "主机类型")@RequestParam Integer type){

        return sceneService.console(nodeId, type);
    }

    @GetMapping("/destroy")
    @ApiOperation(value = "销毁场景")
    public BaseRsp destroy(@ApiParam(name = "resource_id", value = "场景id")
                               @RequestParam("resource_id") String resourceId){
        sceneService.destroyScene(resourceId);
        return new BaseRsp<>();
    }

    @GetMapping("/detail")
    @ApiOperation(value = "场景详情")
    public BaseRsp detail(@ApiParam(name = "topology_id", value = "拓扑结构id") @RequestParam("topology_id") String topologyId,
                          @ApiParam(value = "实例id", name = "instance_id")@RequestParam("instance_id")String instanceId){

        return sceneService.detail(topologyId, instanceId);
    }
}
