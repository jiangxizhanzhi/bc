package com.cyberpeace.situation.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.model.scene.FlowLogsPageContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.situation.constant.*;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.FlowLogReq;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.model.ws.SituationWsMessageIdentifier;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsMultiNodeDynamic;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class TopoHandlerServiceImpl implements TopoHandlerService {

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private RemoteTerminalService remoteTerminalService;

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	@Override
	public SceneData getSceneData(String sceneId, String topoId) {

		// 获取中台场景数据，优先获取场景拓扑
		JSONObject sceneTopo;
		JSONObject topology;
		JSONObject extendElementInfo;

		// 提取有效数据
		if (StringUtils.isBlank(sceneId)) {
			sceneTopo = remoteTopoService.getTopoById(topoId);
			topology = sceneTopo.getJSONObject("scene_config").getJSONObject("topology");
			extendElementInfo = sceneTopo.getJSONObject("scene_config").getJSONObject("plugins")
					.getJSONObject("cpcloud").getJSONObject("elements");
		} else {
			sceneTopo = remoteSceneService.getById(sceneId, null);
			topology = sceneTopo.getJSONObject("scene").getJSONObject("topology");
			extendElementInfo = sceneTopo.getJSONObject("scene").getJSONObject("plugins")
					.getJSONObject("cpcloud").getJSONObject("elements");
		}

		// 获取中台靶标类型数据
		JSONObject deviceCategorie = remoteTerminalService.getAllCategorie();

		// 处理靶标类型数据
		CategorieMap categorieMap = getCategorieMap(deviceCategorie);

		// 构造场景数据
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

		return sceneData;
	}

	@Override
	public SituationTopo getSituationTopo(SceneData sceneData) {

		// 处理态势数据
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

		return situationTopo;
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

			// 写入坐标范围
			sceneData.getXList().add(x);
			sceneData.getXList().add(x + width);
			sceneData.getYList().add(y);
			sceneData.getYList().add(y + height);

			// 写入区域四至
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
			areaInfo.setShape(getAreaShape(area));
			areaInfo.setStatus(getAreaStatus(area));
			areaInfo.setScope(scope);
			areaInfo.setStyle(getLineStyle());
			areaInfo.setEffect(getLineEffect());

			areasInfo.put(area.getString("id"), areaInfo);
		}
		return areasInfo;

	}

	private Map<String, ElementInfo> getElements(SceneData sceneData,
			Map<String, AreaInfo> areasInfo) {

		JSONArray elements = sceneData.getElements();
		JSONObject extendElementInfo = sceneData.getExtendElementInfo();
		CategorieMap categorieMap = sceneData.getCategorieMap();
		Map<String, ElementInfo> elementsInfo = Maps.newHashMap();

		for (int i = 0; i < elements.size(); i++) {

			JSONObject element = elements.getJSONObject(i);
			String elementId = element.getString("id");
			JSONObject elementExtendedInfo = extendElementInfo.getJSONObject(elementId);

			// 处理设备类型（虚/实）
			String virtualType = elementExtendedInfo.getString("image_type");
			String deviceType = "entity".equals(virtualType) ? "entity" : "virtual";

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
			} else if ("路由器".equals(secondType)) {
				hierarchy = 1;
			} else {
				hierarchy = 0;
			}

			// 处理坐标数据
			JSONObject position2 = element.getJSONObject("position2");
			Float x = position2.getFloat("x");
			Float y = position2.getFloat("y");
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
			elementInfo.setActionStatus(getElementActionStatus(element));
			elementInfo.setStatus(getElementStatus(element));
			// elementInfo.setAreas(topoHandlerService.getElementAreas(x, y, areasInfo));
			elementInfo.setPosition(new PlaneCoordinate(x, y));
			elementInfo.setPorts(portsInfo);
			elementInfo.setHierarchy(hierarchy);

			elementsInfo.put(elementId, elementInfo);
		}

		return elementsInfo;
	}

	private List<ConnectionInfo> getConnections(SceneData sceneData,
			Map<String, ElementInfo> elements) {

		JSONArray connections = sceneData.getConnections();
		Map<String, String> portElementMap = sceneData.getPortElementMap();
		List<String> externalNetwork = sceneData.getExternalNetwork();

		List<ConnectionInfo> connectionsInfo = Lists.newArrayList();

		for (int i = 0; i < connections.size(); i++) {
			JSONObject connection = connections.getJSONObject(i);
			String source = portElementMap.get(connection.getString("source"));
			String target = portElementMap.get(connection.getString("target"));

			// 二次处理层级数据
			if (externalNetwork.contains(source)
					&& !"终端节点".equals(elements.get(target).getRole())) {
				elements.get(target).setHierarchy(1);
			}

			if (externalNetwork.contains(target)
					&& !"终端节点".equals(elements.get(source).getRole())) {
				elements.get(source).setHierarchy(1);
			}

			// 构造返回连线数据
			ConnectionInfo connectionInfo = new ConnectionInfo();
			connectionInfo.setSource(source);
			connectionInfo.setTarget(target);
			connectionInfo.setStyle(getLineStyle());
			connectionInfo.setEffect(getLineEffect());
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
		sceneInfo.setScope(getSceneScope(sceneData.getXList(), sceneData.getYList()));

		return sceneInfo;

	}

	@Override
	public CategorieMap getCategorieMap(JSONObject deviceCategorie) {

		List<DeviceType> firstDeviceTypes = JsonUtil.toJsonObjectArray(
				deviceCategorie.getJSONArray("first_type_list").toString(), DeviceType.class);
		List<DeviceType> secondDeviceTypes = JsonUtil.toJsonObjectArray(
				deviceCategorie.getJSONArray("second_type_list").toString(), DeviceType.class);

		List<DeviceRole> roles = JsonUtil.toJsonObjectArray(
				deviceCategorie.getJSONArray("role").toString(), DeviceRole.class);

		// 处理靶标一级分类映射
		Map<Integer, String> firstCategorie = Maps.newHashMap();
		for (DeviceType firstDeviceType : firstDeviceTypes) {
			firstCategorie.put(firstDeviceType.getId(), firstDeviceType.getName());
		}

		// 处理靶标二级分类映射
		Map<Integer, String> secondCategorie = Maps.newHashMap();
		for (DeviceType secondDeviceType : secondDeviceTypes) {
			secondCategorie.put(secondDeviceType.getId(), secondDeviceType.getName());
		}

		// 处理靶标角色映射
		Map<Integer, String> role = Maps.newHashMap();
		for (DeviceRole deviceRole : roles) {
			role.put(deviceRole.getId(), deviceRole.getName());
		}

		// 构造返回数据
		CategorieMap categorieMap = new CategorieMap();
		categorieMap.setFirstCategorie(firstCategorie);
		categorieMap.setSecondCategorie(secondCategorie);
		categorieMap.setRole(role);

		return categorieMap;
	}

	@Override
	public String getLineColor() {

		String color = Color.getRandomValue(Color.values());

		return color;

	}

	@Override
	public Integer getLineWidth() {

		Integer lineWidth = 5;

		return lineWidth;

	}

	@Override
	public String getLineType() {

		String lineType = LineType.getRandomValue(LineType.values());

		return lineType;

	}

	@Override
	public LineStyle getLineStyle() {
		LineStyle lineStyle = new LineStyle();
		lineStyle.setLineType(getLineType());
		lineStyle.setWidth(getLineWidth());
		lineStyle.setColor(getLineColor());

		return lineStyle;
	}

	@Override
	public List<String> getLineEffect() {

		List<String> effect = LineEffect.getRandomEffect(LineEffect.values());

		return effect;
	}

	@Override
	public List<PlaneCoordinate> getSceneScope(List<Float> xList, List<Float> yList) {

		// 获取x、y坐标的极值
		Float xMax = Collections.max(xList);
		Float xMin = Collections.min(xList);
		Float yMax = Collections.max(yList);
		Float yMin = Collections.min(yList);

		// 构造拓扑最大坐标范围
		List<PlaneCoordinate> sceneScope = Lists.newArrayList();
		sceneScope.add(new PlaneCoordinate(xMin, yMin));
		sceneScope.add(new PlaneCoordinate(xMin, yMax));
		sceneScope.add(new PlaneCoordinate(xMax, yMax));
		sceneScope.add(new PlaneCoordinate(xMax, yMin));

		return sceneScope;

	}

	@Override
	public String getAreaShape(JSONObject area) {
		return AreaShape.RECTANGLE.getCode();
	}

	@Override
	public String getAreaStatus(JSONObject area) {
		return AreaStatus.NORMAL.getCode();
	}

	@Override
	public String getElementStatus(JSONObject element) {

		// 提取原始数据
		Integer event = (Integer) element.getOrDefault("event", Event.UNUSE.getCode());
		Integer eventStatus = (Integer) element.getOrDefault("event_status",
				EventStatus.OVER.getCode());
		Integer processStatus = (Integer) element.getOrDefault("process_status",
				ProcessStatus.OVER.getCode());

		// 综合判断节点状态
		String elementStatus;

		if (eventStatus.equals(EventStatus.ABNORMAL.getCode())
				|| processStatus.equals(ProcessStatus.ABNORMAL.getCode())) {
			// 异常节点
			elementStatus = ElementStatus.ABNORMAL.getCode();
		} else if (Event.getUnstartedCodeList().contains(event)
				&& eventStatus.equals(EventStatus.OVER.getCode())) {
			// 未启动节点
			elementStatus = ElementStatus.UNSTARTED.getCode();
		} else if (Event.getCreatingCodeList().contains(event)
				&& eventStatus.equals(EventStatus.OVER.getCode())) {
			// 创建中节点
			elementStatus = ElementStatus.CREATING.getCode();
		} else if (Event.getStartingCodeList().contains(event)
				&& eventStatus.equals(EventStatus.IN_PROGRESS.getCode())) {
			// 启动中节点
			elementStatus = ElementStatus.STARTING.getCode();
		} else {
			// 运行中节点
			elementStatus = ElementStatus.RUNNING.getCode();
		}
		return elementStatus;
	}

	@Override
	public String getElementActionStatus(JSONObject element) {
		return ElementActionStatus.NORMAL.getCode();
	}

	@Override
	public List<String> getElementAreas(Float x, Float y, Map<String, AreaInfo> areasInfo) {

		List<String> elementAreas = Lists.newArrayList();

		for (AreaInfo areaInfo : areasInfo.values()) {

			Float xMin = areaInfo.getScope().get(0).getX();
			Float yMin = areaInfo.getScope().get(0).getY();
			Float xMax = areaInfo.getScope().get(2).getX();
			Float yMax = areaInfo.getScope().get(2).getY();

			if (xMin <= x && x <= xMax && yMin <= y && y <= yMax) {
				elementAreas.add(areaInfo.getAreaId());
			}
		}

		return elementAreas;
	}

	@Override
	public List<ElementInfo> getRandomElements(JSONArray elements, Integer num) {
		List<Integer> indexs = Lists.newArrayList();
		List<ElementInfo> randomElements = Lists.newArrayList();

		for (int i = 0; i < num; i++) {
			while (true) {
				Integer j = (int) (Math.random() * elements.size());
				if (!indexs.contains(j)) {
					JSONObject element = elements.getJSONObject(j);
					ElementInfo elementInfo = new ElementInfo();
					elementInfo.setElementId(element.getString("id"));
					elementInfo.setName(element.getString("name"));
					elementInfo.setActionStatus(
							ElementActionStatus.getRandomValue(ElementActionStatus.values()));
					randomElements.add(elementInfo);
					indexs.add(j);
					break;
				}
			}
		}
		return randomElements;
	}

	@Override
	public List<JSONObject> getFlowLogs(FlowLogReq flowLogReq) {

		// 构造请求中台参数
		FlowLogsPageContext flowLogsPageContext = BeanUtils.copy(flowLogReq,
				FlowLogsPageContext.class);
		flowLogsPageContext.setId(flowLogReq.getSceneId());

		// 请求中台数据
		JSONObject jsonObject = remoteSceneService.getFlowLogs(flowLogsPageContext);
		List<JSONObject> flowLogs = JsonUtil
				.toJsonObjectArray(jsonObject.getJSONArray("list").toString(), JSONObject.class);

		return flowLogs;
	}

	@Override
	public List<JSONObject> getAttackEventList(List<JSONObject> flowLogs, String sceneId) {

		List<JSONObject> arrayObjects = Lists.newArrayList();

		for (JSONObject flowLog : flowLogs) {
			WsMultiNodeDynamic attackEvent = new WsMultiNodeDynamic();

			// 设置基础信息
			attackEvent.setSceneId(sceneId);
			attackEvent.setTime(flowLog.getString("time"));
			attackEvent.setCategory("attack");

			// 设置事件类型
			String enDesc = flowLog.getObject("attack_info", JSONObject.class)
					.getString("attack_type");
			AttackType attackType = AttackType.getEnumByEnDesc(enDesc, AttackType.class);
			attackEvent.setType(attackType.getActionType().getCode());

			attackEvent.setName(enDesc);
			attackEvent.setSourceId(flowLog.getString("source_node_id"));
			attackEvent.setTargetId(flowLog.getString("target_node_id"));

			// 设置动效配置
			attackEvent.setLineStyle(
					new LineStyle(Color.RED.getCode(), 5, LineType.DASHED_FLY.getCode()));
			attackEvent.setLookAtTarget(new Random().nextBoolean());

			// 设置事件描述
			String sourceIp = flowLog.getString("source_ip");
			String targetIp = flowLog.getString("target_ip");
			attackEvent.setDescription(
					String.format("%s对%s发起%s", sourceIp, targetIp, attackType.getCnDesc()));

			// ws推送消息
			WsMessage<WsMultiNodeDynamic> wsMessage = new WsMessage<>(
					MessageType.AttackEvent.getDesc(), attackEvent);
			JSONObject data = JsonUtil.toJsonObject(JsonUtil.toJsonString(wsMessage),
					JSONObject.class);
			pushDataToRoom(data, sceneId);
			arrayObjects.add(data);
		}

		return arrayObjects;
	}

	@Override
	public void pushDataToRoom(JSONObject data, String sceneId) {

		SituationWsMessageIdentifier identifier = new SituationWsMessageIdentifier();
		identifier.setPath(WsPath.SITUATION);
		identifier.setSceneId(sceneId);

		WebSocketMessageContext messageContext = WebSocketMessageContext.builder().data(data)
				.messageIdentifier(identifier).build();

		myWebSocketHandler.pushMessage(messageContext, true);
	}

}