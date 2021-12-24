package com.cyberpeace.situation.controller;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.cyberpeace.situation.model.SceneInfo;
import com.cyberpeace.situation.service.TopoHandlerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Api(tags = "态势拓扑接口")
@RestController
@RequestMapping("/situation")
public class TopoHandlerController {


    @Autowired
    private RemoteSceneService remoteSceneService;

    @Autowired
    private RemoteTerminalService remoteTerminalService;

    @Autowired
    private TopoHandlerService topoHandlerService;

//    String sceneId = "5fb9de782ecf4e968ac3ac9c55388f97";

    @GetMapping("topo_3d")
    public BaseRsp<SceneInfo> topo3d(@ApiParam(name = "scene_id",
            value = "场景编号") @RequestParam("scene_id") @Valid @NotBlank String sceneId) {

        JSONObject sceneTopo = remoteSceneService.getById(sceneId, null);
        JSONObject deviceCategorie = remoteTerminalService.getAllCategorie();

        SceneInfo sceneInfo = getSceneInfo(sceneTopo);

        return new BaseRsp(sceneInfo);
    }

    private SceneInfo getSceneInfo(JSONObject sceneTopo) {

        JSONObject topology = sceneTopo.getJSONObject("scene").getJSONObject("topology");
        JSONObject sceneData = topology.getJSONObject("scene");

        String id = sceneTopo.getString("id");
        String name = sceneData.getString("name");
        String description = sceneData.getString("description");

        SceneInfo sceneInfo = new SceneInfo();
        sceneInfo.setId(id);
        sceneInfo.setName(name);
        sceneInfo.setDesc(description);

        return sceneInfo;

    }

}