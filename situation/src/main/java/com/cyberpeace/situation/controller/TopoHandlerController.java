package com.cyberpeace.situation.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.*;

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
    public BaseRsp<SituationTopo> topo3d(@ApiParam(name = "scene_id",
            value = "场景编号") @RequestParam("scene_id") @Valid @NotBlank String sceneId) {

        JSONObject sceneTopo = remoteSceneService.getById(sceneId, null);
        JSONObject deviceCategorie = remoteTerminalService.getAllCategorie();

        JSONObject topology = sceneTopo.getJSONObject("scene").getJSONObject("topology");
        JSONObject extendElementInfo = sceneTopo.getJSONObject("scene").getJSONObject("plugins").getJSONObject("cpcloud").getJSONObject("elements");

        Map<String, AreaInfo> areas = getAreas(topology);
        Map<String, ElementInfo> elements = getElements(topology);
        List<ConnectionInfo> connections = getConnections(topology);
        SceneInfo sceneInfo = getSceneInfo(sceneId, topology);

        SituationTopo situationTopo = new SituationTopo();
        situationTopo.setScene(sceneInfo);
        situationTopo.setAreas(areas);
        situationTopo.setElements(elements);
        situationTopo.setConnections(connections);

        return new BaseRsp<SituationTopo>(situationTopo);
    }

    private SceneInfo getSceneInfo(String sceneId, JSONObject topology) {

        JSONObject sceneData = topology.getJSONObject("scene");
        String name = sceneData.getString("name");
        String description = sceneData.getString("description");

        SceneInfo sceneInfo = new SceneInfo();
        sceneInfo.setId(sceneId);
        sceneInfo.setName(name);
        sceneInfo.setDesc(description);

        return sceneInfo;

    }

    private Map<String, AreaInfo> getAreas(JSONObject topology) {

        Map<String, AreaInfo> areasInfo = Maps.newHashMap();

        JSONArray areas = (JSONArray) topology.getOrDefault("areas", Lists.newArrayList());

        for (int i = 0; i < areas.size(); i++) {

            JSONObject area = areas.getJSONObject(i);
            JSONObject position = area.getJSONObject("position2");
            Float x = position.getJSONObject("origin").getFloat("x");
            Float y = position.getJSONObject("origin").getFloat("y");
            Float width = position.getFloat("width");
            Float height = position.getFloat("height");

            List<PlaneCoordinate> scope = Lists.newArrayList();
            scope.add(new PlaneCoordinate(x, y));
            scope.add(new PlaneCoordinate(x, y + height));
            scope.add(new PlaneCoordinate(x + width, y + height));
            scope.add(new PlaneCoordinate(x + width, y));

            LineStyle style = new LineStyle();
            style.setColor("#FFFFFF");
            style.setWidth(1);
            style.setLineType("fly");

            List<String> effects = Lists.newArrayList();

            AreaInfo areaInfo = new AreaInfo();
            areaInfo.setAreaId(area.getString("id"));
            areaInfo.setName(area.getString("name"));
            areaInfo.setDesc(area.getString("description"));
            areaInfo.setShape((String) area.getOrDefault("shape", "rectangle"));
            areaInfo.setStatus((String) area.getOrDefault("status", "normal"));
            areaInfo.setScope(scope);
            areaInfo.setStyle(style);
            areaInfo.setEffect(effects);

            areasInfo.put(area.getString("id"), areaInfo);
        }
        return areasInfo;

    }

    private Map<String, ElementInfo> getElements(JSONObject topology) {

        Map<String, ElementInfo> elements = Maps.newHashMap();

        return elements;

    }

    private List<ConnectionInfo> getConnections(JSONObject topology) {

        List<ConnectionInfo> connections = Lists.newArrayList();

        return connections;

    }


}