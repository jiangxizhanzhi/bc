package com.cyberpeace.situation.service.impl;

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
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.integratation.model.mp.MpEventAggr;
import com.cyberpeace.integratation.model.mp.MpFlowTrend;
import com.cyberpeace.integratation.model.mp.MpHostState;
import com.cyberpeace.integratation.model.mp.MpRiskCount;
import com.cyberpeace.situation.constant.*;
import com.cyberpeace.situation.constant.ws.ActionType;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.constant.ws.MpEventType;
import com.cyberpeace.situation.controller.req.FlowLogReq;
import com.cyberpeace.situation.controller.res.ElementStateRes;
import com.cyberpeace.situation.controller.res.LinkStateRes;
import com.cyberpeace.situation.controller.res.SceneAllDataRes;
import com.cyberpeace.situation.dao.entity.SituationConfig;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.model.ws.*;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SituationHandlerServiceImpl implements SituationHandlerService {

	@Autowired
	private TopoHandlerService topoHandlerService;

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	Cache<String, Date> lastTimeCache = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.DAYS).build();

	Cache<String, SituationTopo> situationTopoCache = CacheBuilder.newBuilder()
																		  .expireAfterAccess(4, TimeUnit.HOURS).build();

	Cache<String, ElementStateRes> elementDataCache = CacheBuilder.newBuilder()
																  .expireAfterWrite(5, TimeUnit.SECONDS).build();

	Cache<String, LinkStateRes> linkDataCache = CacheBuilder.newBuilder()
															.expireAfterWrite(1, TimeUnit.SECONDS).build();

	@Override
	@CacheEvict(cacheNames = {"getNodeMap","getAreaMap"}, key = "#sceneData.getSceneId", allEntries = true)
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
		situationTopo.setPieces(getPieces(sceneData));
		situationTopoCache.put(sceneData.getSceneId(), situationTopo);

		return situationTopo;
	}

	@Override
	public SituationTopo getSituationTopoBySceneId(String sceneId){
		SituationTopo situationTopo = situationTopoCache.getIfPresent(sceneId);
		if(situationTopo == null){
			SceneData sceneData = topoHandlerService.getSceneData(null, sceneId);
			sceneData.setSceneId(sceneId);
			situationTopo = getSituationTopo(sceneData);
		}
		return situationTopo;
	}

	private Map<String, PieceInfo> getPieces(SceneData sceneData) {

		List<PieceInfo> pieces = JsonUtil.toJsonObjectArray(sceneData.getPieces().toString(),
				PieceInfo.class);

		Map<String, PieceInfo> piecesInfo = Maps.newHashMap();

		PieceInfo pieceInfo = new PieceInfo();
		pieceInfo.setName("1321312");
		pieceInfo.setPieceId("234234123");
		pieceInfo.setAreas(new ArrayList<>(Collections.singleton("52ab9ddf-2300-4aa9-8d6c-a20cc90e2fa6")));
		PlaneCoordinate planeCoordinate = new PlaneCoordinate(300f,800f);

		pieceInfo.setPosition(planeCoordinate);
		piecesInfo.put("11111111111111111111",pieceInfo);

//		for (PieceInfo pieceInfo : pieces) {
//			piecesInfo.put(pieceInfo.getPieceId(), pieceInfo);
//		}

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
			Integer category3 = elementExtendedInfo.getInteger("category3");
			String firstType = categorieMap.getFirstCategorie().getOrDefault(category, "其他");
			String secondType = categorieMap.getSecondCategorie().getOrDefault(category2, "其他");
			String thirdType = categorieMap.getThirdCategorie().getOrDefault(category3, "其他");

			String icon3 = (String) element.getOrDefault("icon3", "");
			String type = "";
			if((secondType.equals("交换机")||secondType.equals("路由器")||secondType.equals("OTHER")) && !thirdType.equals("其他")){
				type = firstType + "-" + thirdType;
			}else {
				type = StringUtils.isEmpty(icon3) ? firstType + "-" + secondType : "定制-" + icon3;
			}

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
			if(position2 == null){
				log.error("Failed to get node coordinates！Scene_id is [{}]", sceneData.getSceneId());
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
			// elementInfo.setAreas(topoHandlerService.getElementAreas(x, y, areasInfo));
			elementInfo.setPosition(new PlaneCoordinate(x, y));
			elementInfo.setPorts(portsInfo);
			elementInfo.setHierarchy(hierarchy);
			elementInfo.setModelUrl(element.getString("model_url"));

			// 提取自定义配置
			Map<Integer, String> elementConfig = getNodeConfig(elementConfigMap, elementId,
					elementName);
			elementInfo.setConfig(elementConfig);

			JSONArray leaks = elementExtendedInfo.getJSONArray("leaks");
			List<LeakInfo> leakInfos = leaks.toJavaList(LeakInfo.class);
			elementInfo.setLeakCount(leaks.size());
			elementInfo.setLeakInfos(leakInfos);

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

			// // 二次处理层级数据
			// if (externalNetwork.contains(source)
			// && !"终端节点".equals(elements.get(target).getRole())) {
			// elements.get(target).setHierarchy(1);
			// }
			//
			// if (externalNetwork.contains(target)
			// && !"终端节点".equals(elements.get(source).getRole())) {
			// elements.get(source).setHierarchy(1);
			// }

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
		if (datas.isEmpty()){
			return;
		}

		// 推送中台动效时打印日志
		log.info(String.format("Push mp flow event successfully, content is %s", JsonUtil.arrayToString(datas)));

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

	@Override
	@Cacheable(cacheNames = "getNodeMap", key = "#sceneId")
	public Map<String, String> getNodeMap(String sceneId) {
		SituationTopo situationTopo = getSituationTopoBySceneId(sceneId);
		List<ElementInfo> elementInfoList = Lists.newArrayList(situationTopo.getElements()
																			.values());
		Map<String, String> nodeMap = new HashMap<>();
		for (ElementInfo elementInfo : elementInfoList) {
			nodeMap.put(elementInfo.getElementId(), elementInfo.getName());
		}
		return nodeMap;
	}

	@Override
	@Cacheable(cacheNames = "getAreaMap", key = "#sceneId")
	public Map<String, String> getAreaMap(String sceneId){
		SituationTopo situationTopo = getSituationTopoBySceneId(sceneId);
		List<AreaInfo> areaInfoList = Lists.newArrayList(situationTopo.getAreas()
																			.values());
		Map<String, String> areaMap = new HashMap<>();
		for (AreaInfo areaInfo : areaInfoList) {
			areaMap.put(areaInfo.getAreaId(), areaInfo.getName());
		}
		return areaMap;
	}

	@Override
	public <T> void pushDataToRealisticScene(String topoId, T data) {
		RealistSceneWsMessageIdentifier identifier = new RealistSceneWsMessageIdentifier();
		identifier.setPath(WsPath.REALISTIC_SCENE);
		identifier.setTopoId(topoId);
		WebSocketMessageContext messageContext = WebSocketMessageContext.builder()
																		.data(JsonUtil.toJsonString(data,
																				PropertyNamingStrategy.SnakeCase))
																		.messageIdentifier(identifier).build();
		myWebSocketHandler.pushMessage(messageContext, true);
	}


	@Override
	public ElementStateRes getRandomElementData(String elementId) {
		ElementStateRes elementStateRes = elementDataCache.getIfPresent(elementId);
		if (elementStateRes == null) {
			elementStateRes = new ElementStateRes();
			MpEventAggr mpEventAggr = new MpEventAggr();
			int leave = 100;
			int user = (int) (Math.random() * leave) ;
			leave -= user;
			int network = (int) (Math.random() * leave);
			leave -= network;
			int port = (int) (Math.random() * leave);
			leave -= port;
			int process = (int) (Math.random() * leave);
			int file = leave - process;
			mpEventAggr.setFile((double) file);
			mpEventAggr.setNetwork((double) network);
			mpEventAggr.setPort((double) port);
			mpEventAggr.setProcess((double) process);
			mpEventAggr.setUser((double) user);
			elementStateRes.setEventAggr(mpEventAggr);

			MpHostState mpHostState = new MpHostState();
			mpHostState.setCpuCount(4);
			mpHostState.setMemoryTotal(4096);
			mpHostState.setDiskSpace(500);
			mpHostState.setMemoryFree(1604);
			mpHostState.setElementId(elementId);
			mpHostState.setCpuPercent(Math.random() * 100);
			mpHostState.setDiskPercent(Math.random() * 100);
			mpHostState.setMemoryPercent(Math.random() * 100);
			elementStateRes.setHostState(mpHostState);

			MpRiskCount mpRiskCount = new MpRiskCount();
			mpRiskCount.setLowCount((int) (Math.random() * 30));
			mpRiskCount.setMediumCount((int) (Math.random() * 25));
			mpRiskCount.setHighCount((int) (Math.random() * 20));
			elementStateRes.setRiskCount(mpRiskCount);

			List<MpFlowTrend> flowTrend = new ArrayList<>();
			for (int i = 19; i >= 0; i--){
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - (i * 5) );
				flowTrend.add(new MpFlowTrend((int) (Math.random() * 100), calendar.getTime()));
			}
			elementStateRes.setFlowTrend(flowTrend);
			elementDataCache.put(elementId, elementStateRes);
		}
		return elementStateRes;
	}

	@Override
	public LinkStateRes getRandomLinkData(String linkId) {
		LinkStateRes linkStateRes = linkDataCache.getIfPresent(linkId);
		if (linkStateRes == null) {
			linkStateRes = new LinkStateRes();
			linkStateRes.setUpLinkRate((int) (Math.random() * 9000 + 1000));
			linkStateRes.setDownLinkRate((int) (Math.random() * 9000 + 1000));
			linkStateRes.setLinkId(linkId);
			linkDataCache.put(linkId, linkStateRes);
		}
		return linkStateRes;
	}

	@Override
	public SceneAllDataRes getSceneAllDate(String topoId) {
		SituationTopo situationTopo = situationTopoCache.getIfPresent(topoId);
		if(situationTopo == null){
			SceneData sceneData = topoHandlerService.getSceneData(null, topoId);
			sceneData.setSceneId(topoId);
			situationTopo = getSituationTopo(sceneData);
		}
		List<String> elementIdList = new ArrayList<>(situationTopo.getElements().keySet());
		List<String> linkIdList = StreamApiUtils.map(situationTopo.getConnections(), ConnectionInfo::getConnectionId);
		SceneAllDataRes sceneAllDataRes = new SceneAllDataRes();
		List<LinkStateRes> linkStateList = new ArrayList<>();
		List<ElementStateRes> elementStateList = new ArrayList<>();
		TotalSceneData totalSceneData = new TotalSceneData();
		AverageSceneData averageSceneData = new AverageSceneData();
		ElementStateRes totalElementStateRes = new ElementStateRes();
		ElementStateRes averageElementStateRes = new ElementStateRes();

		MpEventAggr totalEventAggr = new MpEventAggr();
		MpHostState totalHostState = new MpHostState();
		MpRiskCount totalRiskCount = new MpRiskCount();
		List<MpFlowTrend> totalFlowTrend = new ArrayList<>();

		MpEventAggr averageEventAggr = new MpEventAggr();
		MpHostState averageHostState = new MpHostState();
		MpRiskCount averageRiskCount = new MpRiskCount();
		List<MpFlowTrend> averageFlowTrend = new ArrayList<>();

		LinkStateRes totalLinkState = new LinkStateRes();
		LinkStateRes averageLinkState = new LinkStateRes();

		int elementCount = elementIdList.size();
		for (String elementId : elementIdList) {
			ElementStateRes elementStateRes = getRandomElementData(elementId);
			elementStateList.add(elementStateRes);

			MpEventAggr eventAggr = elementStateRes.getEventAggr();
			totalEventAggr.setFile(totalEventAggr.getFile() + eventAggr.getFile());
			totalEventAggr.setNetwork(totalEventAggr.getNetwork() + eventAggr.getNetwork());
			totalEventAggr.setPort(totalEventAggr.getPort() + eventAggr.getPort());
			totalEventAggr.setUser(totalEventAggr.getUser() + eventAggr.getUser());
			totalEventAggr.setProcess(totalEventAggr.getProcess() + eventAggr.getProcess());

			MpHostState hostState = elementStateRes.getHostState();
			totalHostState.setMemoryTotal(totalHostState.getMemoryTotal() + hostState.getMemoryTotal());
			totalHostState.setMemoryPercent(totalHostState.getMemoryPercent() + hostState.getMemoryPercent());
			totalHostState.setCpuCount(totalHostState.getCpuCount() + hostState.getCpuCount());
			totalHostState.setDiskSpace(totalHostState.getDiskSpace() + hostState.getDiskSpace());
			totalHostState.setCpuPercent(totalHostState.getCpuPercent() + hostState.getCpuPercent());
			totalHostState.setDiskPercent(totalHostState.getDiskPercent() + hostState.getDiskPercent());

			MpRiskCount riskCount = elementStateRes.getRiskCount();
			totalRiskCount.setHighCount(totalRiskCount.getHighCount() + riskCount.getHighCount());
			totalRiskCount.setMediumCount(totalRiskCount.getMediumCount() + riskCount.getMediumCount());
			totalRiskCount.setLowCount(totalRiskCount.getLowCount() + riskCount.getLowCount());

			if (totalFlowTrend.size() == 0) {
				totalFlowTrend.addAll(elementStateRes.getFlowTrend());
			} else {
				for (int i = 0; i < 20; i++) {
					totalFlowTrend.get(i).setDataSize(totalFlowTrend.get(i).getDataSize()
							+ elementStateRes.getFlowTrend().get(i).getDataSize());
				}
			}
		}
		totalElementStateRes.setHostState(totalHostState);
		totalElementStateRes.setFlowTrend(totalFlowTrend);
		totalElementStateRes.setRiskCount(totalRiskCount);
		totalElementStateRes.setEventAggr(totalEventAggr);
		totalSceneData.setElementStateRes(totalElementStateRes);

		averageEventAggr.setProcess(totalEventAggr.getProcess()/elementCount);
		averageEventAggr.setPort(totalEventAggr.getPort()/elementCount);
		averageEventAggr.setFile(totalEventAggr.getFile()/elementCount);
		averageEventAggr.setNetwork(totalEventAggr.getNetwork()/elementCount);
		averageEventAggr.setUser(totalEventAggr.getUser()/elementCount);
		averageElementStateRes.setEventAggr(averageEventAggr);

		averageHostState.setDiskPercent(totalHostState.getDiskPercent()/elementCount);
		averageHostState.setCpuPercent(totalHostState.getCpuPercent()/elementCount);
		averageHostState.setDiskSpace(totalHostState.getDiskSpace()/elementCount);
		averageHostState.setCpuCount(totalHostState.getCpuCount()/elementCount);
		averageHostState.setMemoryPercent(totalHostState.getMemoryPercent()/elementCount);
		averageHostState.setMemoryTotal(totalHostState.getMemoryTotal()/elementCount);
		averageElementStateRes.setHostState(averageHostState);

		averageRiskCount.setLowCount(totalRiskCount.getLowCount()/elementCount);
		averageRiskCount.setMediumCount(totalRiskCount.getMediumCount()/elementCount);
		averageRiskCount.setHighCount(totalRiskCount.getHighCount()/elementCount);
		averageElementStateRes.setRiskCount(averageRiskCount);

		for (MpFlowTrend flowTrend: totalFlowTrend) {
			averageFlowTrend.add(new MpFlowTrend(flowTrend.getDataSize()/elementCount, flowTrend.getTime()));
		}
		averageElementStateRes.setFlowTrend(averageFlowTrend);
		averageSceneData.setElementStateRes(averageElementStateRes);

		int linkCount = linkIdList.size();
		for (String linkId : linkIdList) {
			LinkStateRes linkStateRes = getRandomLinkData(linkId);
			linkStateList.add(linkStateRes);

			totalLinkState.setUpLinkRate(totalLinkState.getUpLinkRate() + linkStateRes.getUpLinkRate());
			totalLinkState.setDownLinkRate(totalLinkState.getDownLinkRate() + linkStateRes.getDownLinkRate());
			totalLinkState.setBandwidth(totalLinkState.getBandwidth() + linkStateRes.getBandwidth());
		}
		totalSceneData.setLinkStateRes(totalLinkState);

		averageLinkState.setBandwidth(totalLinkState.getBandwidth()/linkCount);
		averageLinkState.setUpLinkRate(totalLinkState.getUpLinkRate()/linkCount);
		averageLinkState.setDownLinkRate(totalLinkState.getDownLinkRate()/linkCount);
		averageSceneData.setLinkStateRes(averageLinkState);

		sceneAllDataRes.setTotalSceneData(totalSceneData);
		sceneAllDataRes.setElementStateList(elementStateList);
		sceneAllDataRes.setLinkStateList(linkStateList);
		sceneAllDataRes.setAverageSceneData(averageSceneData);
		return sceneAllDataRes;
	}

}