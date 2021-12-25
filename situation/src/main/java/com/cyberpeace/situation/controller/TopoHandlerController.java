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
        List<Float> xList = Lists.newArrayList();
        List<Float> yList = Lists.newArrayList();
        Map<String, String> portElementMap = Maps.newHashMap();

        Map<String, AreaInfo> areas = getAreas(topology, xList, yList);
        Map<String, ElementInfo> elements = getElements(topology, extendElementInfo, xList, yList, portElementMap);
        List<ConnectionInfo> connections = getConnections(topology, portElementMap);
        SceneInfo sceneInfo = getSceneInfo(sceneId, topology, xList, yList);

        SituationTopo situationTopo = new SituationTopo();
        situationTopo.setScene(sceneInfo);
        situationTopo.setAreas(areas);
        situationTopo.setElements(elements);
        situationTopo.setConnections(connections);

        return new BaseRsp<>(situationTopo);
    }

    private SceneInfo getSceneInfo(String sceneId, JSONObject topology, List<Float> xList, List<Float> yList) {

        JSONObject sceneData = topology.getJSONObject("scene");
        String name = sceneData.getString("name");
        String description = sceneData.getString("description");

        Float xMax = Collections.max(xList);
        Float xMin = Collections.min(xList);
        Float yMax = Collections.max(yList);
        Float yMin = Collections.min(yList);

        List<PlaneCoordinate> scope = Lists.newArrayList();
        scope.add(new PlaneCoordinate(xMin, yMin));
        scope.add(new PlaneCoordinate(xMin, yMax));
        scope.add(new PlaneCoordinate(xMax, yMax));
        scope.add(new PlaneCoordinate(xMax, yMin));

        SceneInfo sceneInfo = new SceneInfo();
        sceneInfo.setId(sceneId);
        sceneInfo.setName(name);
        sceneInfo.setDesc(description);
        sceneInfo.setScope(scope);

        return sceneInfo;

    }

    private Map<String, AreaInfo> getAreas(JSONObject topology, List<Float> xList, List<Float> yList) {

        Map<String, AreaInfo> areasInfo = Maps.newHashMap();

        JSONArray areas = (JSONArray) topology.getOrDefault("areas", Lists.newArrayList());

        for (int i = 0; i < areas.size(); i++) {

            JSONObject area = areas.getJSONObject(i);
            JSONObject position = area.getJSONObject("position2");
            Float x = position.getJSONObject("origin").getFloat("x");
            Float y = position.getJSONObject("origin").getFloat("y");
            Float width = position.getFloat("width");
            Float height = position.getFloat("height");

            xList.add(x);
            yList.add(y);

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

    private Map<String, ElementInfo> getElements(JSONObject topology, JSONObject extendElementInfo, List<Float> xList, List<Float> yList, Map<String, String> portElementMap) {

        Map<String, ElementInfo> elementsInfo = Maps.newHashMap();

        JSONArray elements = (JSONArray) topology.getOrDefault("elements", Lists.newArrayList());

        for (int i = 0; i < elements.size(); i++) {
            JSONObject element = elements.getJSONObject(i);
            String elementId = element.getString("id");
            String name = element.getString("name");
            String desc = element.getString("description");
            JSONObject elementExtendedInfo = extendElementInfo.getJSONObject(elementId);
            // Integer role = element.getInteger("role");
            String role = "终端节点";
            Integer virtualType = elementExtendedInfo.getInteger("virtual_type");
            String deviceType = virtualType != 0 ? "virtual" : "entity";
//            String deviceType = "virtual";
            // 处理类型数据
            Integer category = elementExtendedInfo.getInteger("category");
            Integer category2 = elementExtendedInfo.getInteger("category2");
            String type = "暂无";

            // 处理状态数据
            String actionStatus = "normal";
            String status = "running";

            // 处理区域数据
            List<String> areas = Lists.newArrayList();

            // 处理坐标数据
            JSONObject position2 = element.getJSONObject("position2");
            Float x = position2.getFloat("x");
            Float y = position2.getFloat("y");
            PlaneCoordinate position = new PlaneCoordinate(x, y);

            // 处理端口数据
            Map<String, PortInfo> portsInfo = Maps.newHashMap();
            JSONArray ports = (JSONArray) element.getOrDefault("ports", Lists.newArrayList());
            for (int j = 0; j < ports.size(); j++) {
                JSONObject port = ports.getJSONObject(j);
                String portId = port.getString("id");
                String portIp = port.getString("ip");
                String portName = port.getString("name");

                PortInfo portInfo = new PortInfo();
                portInfo.setPortId(portId);
                portInfo.setName(portName);
                portInfo.setIp(portIp);

                portElementMap.put(portId, elementId);
                portsInfo.put(portId, portInfo);
            }

            // 处理初步等级数据
            Integer hierarchy = 0;

            ElementInfo elementInfo = new ElementInfo();
            elementInfo.setElementId(elementId);
            elementInfo.setName(name);
            elementInfo.setRole(role);
            elementInfo.setDesc(desc);
            elementInfo.setDeviceType(deviceType);
            elementInfo.setType(type);
            elementInfo.setActionStatus(actionStatus);
            elementInfo.setStatus(status);
            elementInfo.setAreas(areas);
            elementInfo.setPosition(position);
            elementInfo.setPorts(portsInfo);
            elementInfo.setHierarchy(hierarchy);

            elementsInfo.put(elementId, elementInfo);
        }

        return elementsInfo;
    }

    private List<ConnectionInfo> getConnections(JSONObject topology, Map<String, String> portElementMap) {

        JSONArray connections = (JSONArray) topology.getOrDefault("connections", Lists.newArrayList());

        List<ConnectionInfo> connectionsInfo = Lists.newArrayList();

        for (int i = 0; i < connections.size(); i++) {
            JSONObject connection = connections.getJSONObject(i);

            String source = portElementMap.get(connection.getString("source"));
            String target = portElementMap.get(connection.getString("target"));

            ConnectionInfo connectionInfo = new ConnectionInfo();
            connectionInfo.setSource(source);
            connectionInfo.setTarget(target);
//            connectionInfo.setStyle();
//            connectionInfo.setEffect();

            connectionsInfo.add(connectionInfo);
        }

        return connectionsInfo;

    }


}