package com.cyberpeace.situation.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.situation.constant.MessageType;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(tags = "态势拓扑接口")
@RestController
@RequestMapping("/situation")
public class TopoHandlerController {

    @Autowired
    private RemoteSceneService remoteSceneService;

    @Autowired
    private RemoteTopoService remoteTopoService;

    @Autowired
    private RemoteTerminalService remoteTerminalService;

    @Autowired
    private TopoHandlerService topoHandlerService;

    @CrossOrigin("*")
    @ApiOperation("拓扑解析")
    @GetMapping("topology")
    public BaseRsp<SituationTopo> topology(
            @ApiParam(name = "scene_id", value = "场景编号")
            @RequestParam(value = "scene_id", required = false) String sceneId,

            @ApiParam(name = "topo_id", value = "拓扑编号")
            @RequestParam(value = "topo_id", required = false) String topoId
    ) {

        // 获取中台场景数据，优先获取场景拓扑
        JSONObject sceneTopo;
        JSONObject topology;
        JSONObject extendElementInfo;

        // 提取有效数据
        if (StringUtils.isBlank(sceneId)) {
            sceneTopo = remoteTopoService.getTopoById(topoId);
            topology = sceneTopo.getJSONObject("scene_config").getJSONObject("topology");
            extendElementInfo = sceneTopo.getJSONObject("scene_config").getJSONObject("plugins").
                    getJSONObject("cpcloud").getJSONObject("elements");
        } else {
            sceneTopo = remoteSceneService.getById(sceneId, null);
            topology = sceneTopo.getJSONObject("scene").getJSONObject("topology");
            extendElementInfo = sceneTopo.getJSONObject("scene").getJSONObject("plugins").
                    getJSONObject("cpcloud").getJSONObject("elements");
        }

        // 获取中台靶标类型数据
        JSONObject deviceCategorie = remoteTerminalService.getAllCategorie();

        // 处理靶标类型数据
        CategorieMap categorieMap = topoHandlerService.getCategorieMap(deviceCategorie);

        SceneData sceneData = new SceneData();
        sceneData.setSceneId(sceneId);
        sceneData.setSceneName(topology.getJSONObject("scene").getString("name"));
        sceneData.setSceneDesc(topology.getJSONObject("scene").getString("description"));
        sceneData.setAreas((JSONArray) topology.getOrDefault("areas",
                JSONArray.parseArray(Lists.newArrayList().toString())));
        sceneData.setElements((JSONArray) topology.getOrDefault("elements",
                JSONArray.parseArray(Lists.newArrayList().toString())));
        sceneData.setConnections((JSONArray) topology.getOrDefault("connections",
                JSONArray.parseArray(Lists.newArrayList().toString())));
        sceneData.setExtendElementInfo(extendElementInfo);
        sceneData.setXList(Lists.newArrayList());
        sceneData.setYList(Lists.newArrayList());
        sceneData.setExternalNetwork(Lists.newArrayList());
        sceneData.setPortElementMap(Maps.newHashMap());
        sceneData.setCategorieMap(categorieMap);

        // 处理拓扑数据
        Map<String, AreaInfo> areasInfo = getAreas(sceneData);
        Map<String, ElementInfo> elementsInfo = getElements(sceneData, areasInfo);
        List<ConnectionInfo> connectionsInfo = getConnections(sceneData, elementsInfo);
        SceneInfo sceneInfo = getSceneInfo(sceneData);

        // 构造返回数据
        SituationTopo situationTopo = new SituationTopo();
        situationTopo.setAreas(areasInfo);
        situationTopo.setElements(elementsInfo);
        situationTopo.setConnections(connectionsInfo);
        situationTopo.setScene(sceneInfo);

        return new BaseRsp<>(situationTopo);
    }

    private Map<String, AreaInfo> getAreas(SceneData sceneData) {

        JSONArray areas = sceneData.getAreas();
        Map<String, AreaInfo> areasInfo = Maps.newHashMap();

        for (int i = 0; i < areas.size(); i++) {

            JSONObject area = areas.getJSONObject(i);

            // 处理区域坐标数据
            JSONObject position = area.getJSONObject("position2");
            Float x = position.getJSONObject("origin").getFloat("x");
            Float y = position.getJSONObject("origin").getFloat("y");
            Float width = position.getFloat("width");
            Float height = position.getFloat("height");

            sceneData.getXList().add(x);
            sceneData.getYList().add(y);

            List<PlaneCoordinate> scope = Lists.newArrayList();
            scope.add(new PlaneCoordinate(x, y));
            scope.add(new PlaneCoordinate(x, y + height));
            scope.add(new PlaneCoordinate(x + width, y + height));
            scope.add(new PlaneCoordinate(x + width, y));

            // 构造区域数据
            AreaInfo areaInfo = new AreaInfo();
            areaInfo.setAreaId(area.getString("id"));
            areaInfo.setName("区域" + (i + 1));
            areaInfo.setDesc(area.getString("description"));
            areaInfo.setShape(topoHandlerService.getAreaShape(area));
            areaInfo.setStatus(topoHandlerService.getAreaStatus(area));
            areaInfo.setScope(scope);
            areaInfo.setStyle(topoHandlerService.getLineStyle());
            areaInfo.setEffect(topoHandlerService.getLineEffect());

            areasInfo.put(area.getString("id"), areaInfo);
        }
        return areasInfo;

    }

    private Map<String, ElementInfo> getElements(SceneData sceneData, Map<String, AreaInfo> areasInfo) {

        JSONArray elements = sceneData.getElements();
        JSONObject extendElementInfo = sceneData.getExtendElementInfo();
        CategorieMap categorieMap = sceneData.getCategorieMap();
        Map<String, ElementInfo> elementsInfo = Maps.newHashMap();

        for (int i = 0; i < elements.size(); i++) {

            JSONObject element = elements.getJSONObject(i);
            String elementId = element.getString("id");
            JSONObject elementExtendedInfo = extendElementInfo.getJSONObject(elementId);

            //处理设备类型（虚/实）
            Integer virtualType = elementExtendedInfo.getInteger("virtual_type");
            String deviceType = virtualType != 0 ? "virtual" : "entity";

            // 处理类型数据
            Integer category = elementExtendedInfo.getInteger("category");
            Integer category2 = elementExtendedInfo.getInteger("category2");
            String firstType = categorieMap.getFirstCategorie().getOrDefault(category, "其他");
            String secondType = categorieMap.getSecondCategorie().getOrDefault(category2, "其他");
            String type = firstType + "-" + secondType;

            // 处理角色数据
            Integer roleId = elementExtendedInfo.getInteger("role");
            String role = categorieMap.getRole().getOrDefault(roleId, "其他");

            // 初次处理层级数据
            Integer hierarchy;
            if ("外部网络".equals(role)) {
                sceneData.getExternalNetwork().add(elementId);
                hierarchy = 2;
            } else if ("网络设备".equals(firstType) && !"防火墙".equals(secondType)) {
                hierarchy = 1;
            } else {
                hierarchy = 0;
            }

            // 处理区域数据
            List<String> areas = Lists.newArrayList();

            // 处理坐标数据
            JSONObject position2 = element.getJSONObject("position2");
            Float x = (Float) position2.getFloat("x");
            Float y = (Float) position2.getFloat("y");
            sceneData.getXList().add(x);
            sceneData.getYList().add(y);

            // 处理端口数据
            Map<String, PortInfo> portsInfo = Maps.newHashMap();
            JSONArray ports = (JSONArray) element.getOrDefault("ports",
                    JSONArray.parseArray(Lists.newArrayList().toString()));
            for (int j = 0; j < ports.size(); j++) {
                JSONObject port = ports.getJSONObject(j);
                String portId = port.getString("id");
                sceneData.getPortElementMap().put(portId, elementId);

                PortInfo portInfo = BeanUtils.deepCopy(port, PortInfo.class);
                portInfo.setPortId(portId);

                portsInfo.put(portId, portInfo);
            }

            ElementInfo elementInfo = new ElementInfo();
            elementInfo.setElementId(elementId);
            elementInfo.setName(element.getString("name"));
            elementInfo.setRole(role);
            elementInfo.setDesc(element.getString("description"));
            elementInfo.setDeviceType(deviceType);
            elementInfo.setType(type);
            elementInfo.setActionStatus(topoHandlerService.getElementActionStatus(element));
            elementInfo.setStatus(topoHandlerService.getElementStatus(element));
            elementInfo.setAreas(topoHandlerService.getElementAreas(x, y, areasInfo));
            elementInfo.setPosition(new PlaneCoordinate(x, y));
            elementInfo.setPorts(portsInfo);
            elementInfo.setHierarchy(hierarchy);

            elementsInfo.put(elementId, elementInfo);
        }

        return elementsInfo;
    }

    private List<ConnectionInfo> getConnections(SceneData sceneData, Map<String, ElementInfo> elements) {

        JSONArray connections = sceneData.getConnections();
        Map<String, String> portElementMap = sceneData.getPortElementMap();
        List<String> externalNetwork = sceneData.getExternalNetwork();

        List<ConnectionInfo> connectionsInfo = Lists.newArrayList();

        for (int i = 0; i < connections.size(); i++) {
            JSONObject connection = connections.getJSONObject(i);
            String source = portElementMap.get(connection.getString("source"));
            String target = portElementMap.get(connection.getString("target"));

            // 二次处理层级数据
//            if (externalNetwork.contains(source) && !"终端节点".equals(elements.get(target).getRole())) {
//                elements.get(target).setHierarchy(1);
//            }
//
//            if (externalNetwork.contains(target) && !"终端节点".equals(elements.get(source).getRole())) {
//                elements.get(source).setHierarchy(1);
//            }

            // 构造返回连线数据
            ConnectionInfo connectionInfo = new ConnectionInfo();
            connectionInfo.setSource(source);
            connectionInfo.setTarget(target);
            connectionInfo.setStyle(topoHandlerService.getLineStyle());
            connectionInfo.setEffect(topoHandlerService.getLineEffect());
            connectionsInfo.add(connectionInfo);
        }

        return connectionsInfo;

    }

    private SceneInfo getSceneInfo(SceneData sceneData) {

        // 构造场景基础信息
        SceneInfo sceneInfo = new SceneInfo();
        sceneInfo.setId(sceneData.getSceneId());
        sceneInfo.setName(sceneData.getSceneName());
        sceneInfo.setDesc(sceneData.getSceneDesc());
        sceneInfo.setScope(topoHandlerService.getSceneScope(sceneData.getXList(), sceneData.getYList()));

        return sceneInfo;

    }

    @ApiOperation("随机推送单点动效信息")
    @GetMapping("single_message")
    public BaseRsp<WsSingleNodeEffect> sendSingleMessage(
            @ApiParam(name = "scene_id", value = "场景编号")
            @RequestParam(value = "scene_id", required = true) String sceneId) {

        JSONArray elements = getElements(sceneId);
        WsSingleNodeEffect wsSingleNodeEffect = new WsSingleNodeEffect();
        wsSingleNodeEffect.setSceneId(sceneId);
        wsSingleNodeEffect.setCode(MessageType.SingleNodeEffect.getDesc());
        wsSingleNodeEffect.setElementInfo(topoHandlerService.getRandomElements(elements, 1).get(0));
        JSONObject data = JsonUtil.toJsonObject(JsonUtil.toJsonString(wsSingleNodeEffect), JSONObject.class);
        topoHandlerService.pushDataToRoom(data, sceneId);
        return new BaseRsp<>(wsSingleNodeEffect);
    }

    @ApiOperation("随机推送多点动效信息")
    @GetMapping("multi_message")
    public BaseRsp<WsMultiNodeEffect> sendMultiMessage(
            @ApiParam(name = "scene_id", value = "场景编号")
            @RequestParam(value = "scene_id", required = true) String sceneId) {

        JSONArray elements = getElements(sceneId);
        WsMultiNodeEffect wsMultiNodeEffect = new WsMultiNodeEffect();
        wsMultiNodeEffect.setSceneId(sceneId);
        wsMultiNodeEffect.setCode(MessageType.MultiNodeEffect.getDesc());
        List<ElementInfo> randomElements = topoHandlerService.getRandomElements(elements, 2);
        wsMultiNodeEffect.setSourceElementInfo(randomElements.get(0));
        wsMultiNodeEffect.setDestElementInfo(randomElements.get(1));
        JSONObject data = JsonUtil.toJsonObject(JsonUtil.toJsonString(wsMultiNodeEffect), JSONObject.class);
        topoHandlerService.pushDataToRoom(data, sceneId);
        return new BaseRsp<>(wsMultiNodeEffect);
    }

    private JSONArray getElements(String sceneId) {
        JSONObject sceneTopo = remoteSceneService.getById(sceneId, null);
        JSONObject topology = sceneTopo.getJSONObject("scene").getJSONObject("topology");
        return (JSONArray) topology.getOrDefault("elements", JSONArray.parseArray(Lists.newArrayList().toString()));
    }
}
