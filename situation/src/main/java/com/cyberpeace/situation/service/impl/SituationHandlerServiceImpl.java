package com.cyberpeace.situation.service.impl;

import static com.cyberpeace.situation.constant.SituationConstants.DEFAULT_LINE_WIDTH;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.situation.constant.*;
import com.cyberpeace.situation.constant.ws.ActionType;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.constant.ws.MpEventType;
import com.cyberpeace.situation.dao.entity.SituationConfig;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.model.ws.*;
import com.cyberpeace.situation.req.FlowLogReq;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SituationHandlerServiceImpl implements SituationHandlerService {

	@Autowired
	private TopoHandlerService topoHandlerService;

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	Cache<String, Date> lastTimeCache = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.DAYS).build();

	@Override
	public SituationTopo getSituationTopo(SceneData sceneData) {

		// 处理态势数据
		Map<String, AreaInfo> areasInfo = getAreas(sceneData);
		Map<String, ElementInfo> elementsInfo = getElements(sceneData);
		List<ConnectionInfo> connectionsInfo = getConnections(sceneData);
		SceneInfo sceneInfo = getSceneInfo(sceneData);

		// 构造返回数据
		SituationTopo situationTopo = new SituationTopo();
		situationTopo.setAreas(areasInfo);
		situationTopo.setElements(elementsInfo);
		situationTopo.setConnections(connectionsInfo);
		situationTopo.setScene(sceneInfo);
		situationTopo.setPieces(getPieces(sceneData));

		return situationTopo;
	}

	private Map<String, PieceInfo> getPieces(SceneData sceneData) {

		List<PieceInfo> pieces = JsonUtil.toJsonObjectArray(sceneData.getPieces().toString(),
				PieceInfo.class);

		Map<String, PieceInfo> piecesInfo = Maps.newHashMap();

		for (PieceInfo pieceInfo : pieces) {
			piecesInfo.put(pieceInfo.getPieceId(), pieceInfo);
		}

		return piecesInfo;
	}

	private Map<String, AreaInfo> getAreas(SceneData sceneData) {

		JSONArray areas = sceneData.getAreas();
		Map<String, AreaInfo> areasInfo = Maps.newHashMap();
		Map<String, List<SituationConfig>> areaConfigMap = sceneData.getSituationConfigMap()
				.getOrDefault(SituationConfig.NodeType.AREA.getCode(), Maps.newHashMap());

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
			areaInfo.setDesc(area.getString("description"));
			areaInfo.setShape(getAreaShape(area));
			areaInfo.setStatus(getAreaStatus(area));
			areaInfo.setScope(scope);
			areaInfo.setStyle(getLineStyle());
			areaInfo.setEffect(getLineEffect());
			areaInfo.setName(area.getString("name"));

			// 提取自定义配置
			Map<Integer, String> areaConfig = getNodeConfig(areaConfigMap, area.getString("id"),
					area.getString("name"));
			areaInfo.setConfig(areaConfig);

			// 提取样式数据
			Display display = JsonUtil.toJsonObject(area.getString("display_context"),
					Display.class);

			if (display != null) {
				// 区域填充数据
				areaInfo.setColor(display.getBody().getFill());

				// 区域字体数据
				areaInfo.setFontStyle(
						new FontStyle(display.getLabel().getFill(), display.getLabel().getFontSize()));

				// 区域边框数据
				areaInfo.setStyle(new LineStyle(display.getBody().getStroke(),
						display.getBody().getStrokeWidth(), LineType.DASHED_STRAIGHT.getCode()));
			}

			// 将区域数据添加入列表
			areasInfo.put(area.getString("id"), areaInfo);

		}
		return areasInfo;

	}

	private Map<String, ElementInfo> getElements(SceneData sceneData) {

		JSONArray elements = sceneData.getElements();
		JSONObject extendElementInfo = sceneData.getExtendElementInfo();
		CategorieMap categorieMap = sceneData.getCategorieMap();
		Map<String, ElementInfo> elementsInfo = Maps.newHashMap();
		Map<String, List<SituationConfig>> elementConfigMap = sceneData.getSituationConfigMap()
				.getOrDefault(SituationConfig.NodeType.ELEMENT.getCode(), Maps.newHashMap());

		for (int i = 0; i < elements.size(); i++) {

			JSONObject element = elements.getJSONObject(i);
			String elementId = element.getString("id");
			String elementName = element.getString("name");
			JSONObject elementExtendedInfo = extendElementInfo.getJSONObject(elementId);

			// 处理设备类型（虚/实）
			String virtualType = elementExtendedInfo.getString("image_type");
			String deviceType = "entity".equals(virtualType) ? "entity" : "virtual";

			// 处理类型数据
			Integer category = elementExtendedInfo.getInteger("category");
			Integer category2 = elementExtendedInfo.getInteger("category2");
			String firstType = categorieMap.getFirstCategorie().getOrDefault(category, "其他");
			String secondType = categorieMap.getSecondCategorie().getOrDefault(category2, "其他");

			String icon3 = (String) element.getOrDefault("icon3", "");
			String type = StringUtils.isEmpty(icon3) ? firstType + "-" + secondType : "定制-" + icon3;

			// 处理角色数据
			Integer roleId = elementExtendedInfo.getInteger("role");
			String role = categorieMap.getRole().getOrDefault(roleId, "其他");

			// 初次处理层级数据
			Float hierarchy = null;
			if ("外部网络".equals(role)) {
				sceneData.getExternalNetwork().add(elementId);
				hierarchy = 2.0f;
			} else if ("路由器".equals(secondType)) {
				hierarchy = 1.0f;
			} else {
				hierarchy = 0.0f;
			}

			// 处理坐标数据
			JSONObject position2 = element.getJSONObject("position2");
			if (position2 == null) {
				log.error("Failed to get node coordinates！Scene_id is [{}]",
						sceneData.getSceneId());
				throw new BusinessException(SituationErrorCode.NODE_HAS_NO_COORDINATES);
			}
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
			elementInfo.setName(elementName);
			elementInfo.setRole(role);
			elementInfo.setDesc(element.getString("description"));
			elementInfo.setDeviceType(deviceType);
			elementInfo.setType(type);
			elementInfo.setActionStatus(getElementActionStatus(element));
			elementInfo.setStatus(getElementStatus(element));
			elementInfo.setPosition(new PlaneCoordinate(x, y));
			elementInfo.setPorts(portsInfo);
			elementInfo.setHierarchy(hierarchy);

			// 提取自定义配置
			Map<Integer, String> elementConfig = getNodeConfig(elementConfigMap, elementId,
					elementName);
			elementInfo.setConfig(elementConfig);

			// 提取样式数据
			Display display = JsonUtil.toJsonObject(element.getString("display_context"),
					Display.class);

			if (display != null) {
				elementInfo.setFontStyle(
						new FontStyle(display.getText().getFill(), display.getText().getFontSize()));
			}

			// 写入节点列表
			elementsInfo.put(elementId, elementInfo);
		}

		return elementsInfo;
	}

	private Map<Integer, String> getNodeConfig(
			Map<String, List<SituationConfig>> situationConfigMap, String nodeId, String nodeName) {

		Map<Integer, String> nodeConfig = Maps.newHashMap();

		// 当前场景无配置时，返回空
		if (situationConfigMap.isEmpty()) {
			return nodeConfig;
		}

		List<SituationConfig> situationConfigList = situationConfigMap.getOrDefault(nodeId,
				Lists.newArrayList());
		situationConfigList.addAll(situationConfigMap.getOrDefault(nodeName, Lists.newArrayList()));

		for (SituationConfig situationConfig : situationConfigList) {
			nodeConfig.put(situationConfig.getAttribute(), situationConfig.getAttributeValue());
		}

		return nodeConfig;
	}

	private List<ConnectionInfo> getConnections(SceneData sceneData) {

		JSONArray connections = sceneData.getConnections();
		Map<String, String> portElementMap = sceneData.getPortElementMap();

		List<ConnectionInfo> connectionsInfo = Lists.newArrayList();

		for (int i = 0; i < connections.size(); i++) {
			JSONObject connection = connections.getJSONObject(i);
			String source = portElementMap.get(connection.getString("source"));
			String target = portElementMap.get(connection.getString("target"));

			// 构造返回连线数据
			ConnectionInfo connectionInfo = new ConnectionInfo();
			connectionInfo.setConnectionId(connection.getString("id"));
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
	public String getLineColor() {

		return Color.getRandomValue(Color.values());

	}

	@Override
	public Integer getLineWidth() {

		return DEFAULT_LINE_WIDTH;

	}

	@Override
	public String getLineType() {

		return LineType.getRandomValue(LineType.values());

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

		return LineEffect.getRandomEffect(LineEffect.values());
	}

	@Override
	public List<PlaneCoordinate> getSceneScope(List<Float> xList, List<Float> yList) {

		// 获取x、y坐标的极值
		Float xMax = 0.0f;
		Float xMin = 0.0f;
		Float yMax = 0.0f;
		Float yMin = 0.0f;

		if (!xList.isEmpty() && !yList.isEmpty()) {
			xMax = Collections.max(xList);
			xMin = Collections.min(xList);
			yMax = Collections.max(yList);
			yMin = Collections.min(yList);
		}

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

	@SneakyThrows
	@Override
	public List<WsMessage> getAttackEventList(List<JSONObject> flowLogs, String sceneId) {

		List<WsMessage> arrayObjects = Lists.newArrayList();

		Date lastTime = lastTimeCache.getIfPresent(sceneId);

		for (JSONObject flowLog : flowLogs) {

			Date time = TimeUtil.toDate1(flowLog.getString("time"));

			// 如果时间小于缓存值，跳过不处理
			if (lastTime != null && (lastTime.after(time) || lastTime.equals(time))) {
				continue;
			}

			// 当缓存值为空或此日志时间大于缓存值时，更新缓存值
			Date currentLastTime = lastTimeCache.getIfPresent(sceneId);
			if (currentLastTime == null || currentLastTime.before(time)) {
				lastTimeCache.put(sceneId, time);
			}

			WsMultiElementDynamic attackEvent = new WsMultiElementDynamic();

			// 设置基础信息
			attackEvent.setSceneId(sceneId);
			attackEvent.setTime(time.getTime());
			attackEvent.setCategory("attack");

			// 设置事件类型
			String enDesc = flowLog.getObject("attack_info", JSONObject.class)
					.getString("attack_type");
			MpEventType mpEventType = MpEventType.getEnumByEnDesc(enDesc, MpEventType.class);

			// 如果未识别流量，统一判断为未知流量
			if (mpEventType == null) {
				mpEventType = MpEventType.UNKNOWN;
			}

			attackEvent.setType(mpEventType.getActionType().getCode());

			attackEvent.setSourceId(flowLog.getString("source_node_id"));
			attackEvent.setTargetId(flowLog.getString("target_node_id"));

			// 设置动效配置
			attackEvent.setLineStyle(
					new LineStyle(Color.RED.getCode(), 5, LineType.DASHED_FLY.getCode()));
			attackEvent.setLookAtTarget(new Random().nextBoolean());

			// 设置事件描述
			String sourceIp = flowLog.getString("source_ip");
			String sourceName = flowLog.getString("source_node_name");
			String targetIp = flowLog.getString("target_ip");
			String targetName = flowLog.getString("target_node_name");
			attackEvent.setDescription(String.format("%s(%s)对%s(%s)%s", sourceName, sourceIp,
					targetName, targetIp, mpEventType.getCnDesc()));

			// ws推送消息
			WsMessage<WsMultiElementDynamic> wsMessage = new WsMessage<>(
					MessageType.AttackEventEE.getDesc(), attackEvent);
			arrayObjects.add(wsMessage);
		}

		return arrayObjects;
	}

	@Override
	public WsMultiNodeDynamic getRandomWsMultNodeDynamic(String sceneId, Integer actionCode) {

		// 处理事件类型
		ActionType actionType = null;
		if (actionCode != null) {
			actionType = ActionType.getEnumByCode(actionCode, ActionType.class);
		} else {
			actionType = ActionType.getRandom(ActionType.values());
		}

		JSONObject sceneTopo = topoHandlerService.getSceneTopoBySceneId(sceneId);
		JSONObject topology = sceneTopo.getJSONObject("scene").getJSONObject("topology");
		JSONArray elements = (JSONArray) topology.getOrDefault("elements",
				JSONArray.parseArray(Lists.newArrayList().toString()));
		JSONArray areas = (JSONArray) topology.getOrDefault("areas",
				JSONArray.parseArray(Lists.newArrayList().toString()));

		WsMultiNodeDynamic wsEvent = null;
		if ("compound".equals(actionType.getType()) && !areas.isEmpty() && !elements.isEmpty()
				&& new Random().nextBoolean()) {
			wsEvent = getRandomWsMultiAreaDynamic(elements, areas);
		} else if (elements.size() >= 2) {
			wsEvent = getRandomWsMultiElementDynamic(elements);
		} else {
			log.error("no enough scene node, scene_id:{}", sceneId);
			throw new BusinessException(ErrorCode.LACK_OF_SCENE_NODE);
		}

		wsEvent.setSceneId(sceneId);
		wsEvent.setTime(System.currentTimeMillis());
		wsEvent.setCategory("attack");

		wsEvent.setType(actionType.getCode());
		// wsEvent.setName(actionType.getDesc());

		// 设置动效配置
		wsEvent.setLineStyle(new LineStyle(Color.getRandomValue(Color.values()), 5,
				LineType.getRandomValue(LineType.values())));
		wsEvent.setLookAtTarget(new Random().nextBoolean());

		// 补充动态描述
		wsEvent.setDescription(wsEvent.getDescription().replace("$攻击$", actionType.getDesc()));

		return wsEvent;
	}

	private WsMultiAreaDynamic getRandomWsMultiAreaDynamic(JSONArray elements, JSONArray areas) {

		JSONObject sourceNode = topoHandlerService.getRandomNodes(elements, 1).get(0);
		JSONObject targetNode = topoHandlerService.getRandomNodes(areas, 1).get(0);
		WsMultiAreaDynamic wsMultiAreaDynamic = new WsMultiAreaDynamic();
		wsMultiAreaDynamic.setNodeInfo(new ElementInfo(sourceNode), new AreaInfo(targetNode));

		// 设置事件描述
		wsMultiAreaDynamic.setDescription(
				String.format("%s对%s发起$攻击$", wsMultiAreaDynamic.getSourceElementInfo().getName(),
						("区域<" + wsMultiAreaDynamic.getTargetAreaInfo().getAreaId() + ">")));

		return wsMultiAreaDynamic;
	}

	private WsMultiElementDynamic getRandomWsMultiElementDynamic(JSONArray elements) {
		List<JSONObject> nodes = topoHandlerService.getRandomNodes(elements, 2);
		WsMultiElementDynamic wsMultiElementDynamic = new WsMultiElementDynamic();
		wsMultiElementDynamic.setNodeInfo(new ElementInfo(nodes.get(0)),
				new ElementInfo(nodes.get(1)));

		// 设置事件描述
		wsMultiElementDynamic.setDescription(
				String.format("%s对%s发起$攻击$", wsMultiElementDynamic.getSourceElementInfo().getName(),
						wsMultiElementDynamic.getTargetElementInfo().getName()));

		return wsMultiElementDynamic;
	}

	@Override
	public <T> void pushDataToRoom(T data, String sceneId) {

		SituationWsMessageIdentifier identifier = new SituationWsMessageIdentifier();
		identifier.setPath(WsPath.SITUATION);
		identifier.setSceneId(sceneId);

		WebSocketMessageContext messageContext = WebSocketMessageContext.builder()
				.data(JsonUtil.toJsonString(data, PropertyNamingStrategy.SnakeCase))
				.messageIdentifier(identifier).build();

		myWebSocketHandler.pushMessage(messageContext, true);
	}

	@SneakyThrows
	@Async
	@Override
	public void batchPushDataToRoom(String sceneId, List<WsMessage> datas, Integer number,
			Integer interval) {

		// 无推送消息时，直接结束
		if (datas.isEmpty()) {
			return;
		}

		// 推送中台动效时打印日志
		log.info(String.format("Push mp flow event successfully, content is %s",
				JsonUtil.arrayToString(datas)));

		Integer circle = (int) Math.ceil((double) datas.size() / number);

		for (int i = 0; i < circle; i++) {

			for (int j = 0; j < number && !datas.isEmpty(); j++) {
				pushDataToRoom(datas.remove(0), sceneId);
			}

			// 延时发送
			Thread.sleep(interval * 1000);
		}

	}

	@Override
	public WsMessage<WsNodeStatus> pushLinkStatus(String sceneId) {
		WsNodeStatus wsNodeStatus = topoHandlerService.getNodeStatusMessage(sceneId);

		// 推送ws消息
		WsMessage<WsNodeStatus> wsMessage = new WsMessage<>(MessageType.NodeStatus.getDesc(),
				wsNodeStatus);
		pushDataToRoom(wsMessage, sceneId);

		return wsMessage;
	}

	@Override
	public List<WsMessage> pushFlowEvents(FlowLogReq flowLogReq) {

		// 获取流量日志
		List<JSONObject> flowLogs = topoHandlerService.getFlowLogs(flowLogReq);

		// 处理流量日志
		List<WsMessage> wsMultiNodeDynamics = getAttackEventList(flowLogs, flowLogReq.getSceneId());

		// 分批推送消息
		batchPushDataToRoom(flowLogReq.getSceneId(), wsMultiNodeDynamics, 5, 5);

		return wsMultiNodeDynamics;

	}

}