package com.cyberpeace.situation.service.impl;

import static com.cyberpeace.situation.constant.SituationConstants.DEFAULT_LINE_WIDTH;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
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
import com.cyberpeace.integratation.model.mp.MpHostEvent;
import com.cyberpeace.integratation.model.scene.SceneEventHostListReq;
import com.cyberpeace.integratation.service.RemoteSceneService;
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

/**
 * 态势处理器Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see SituationHandlerService
 * @since 1.0.0
 */
@Slf4j
@Service
public class SituationHandlerServiceImpl implements SituationHandlerService {
	/**
	 * 图层元素类型
	 *
	 * @see Integer
	 */
	private static final Integer LAYER_ELEMENT_TYPE = 8;

	/**
	 * 拓扑处理器Service
	 *
	 * @see TopoHandlerService
	 */
	@Autowired
	private TopoHandlerService topoHandlerService;

	/**
	 * 远程场景Service
	 *
	 * @see RemoteSceneService
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 我的WebSocket处理器
	 *
	 * @see MyWebSocketHandler
	 */
	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	/**
	 * 记录流量事件最后时间
	 *
	 * @see Cache<String, Date>
	 */
	Cache<String, Date> lastTimeCache = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.DAYS).build();

	/**
	 * 记录主机采集事件最后时间
	 *
	 * @see Cache<String, Date>
	 */
	Cache<String, Date> lastTimeCacheHostEvent = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.DAYS).build();

	/**
	 * 获取态势拓扑
	 *
	 * @param sceneData 场景数据
	 * @return {@code SituationTopo }
	 * @throws
	 * @see SceneData
	 * @see SituationTopo
	 */
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

	/**
	 * 获取件
	 *
	 * @param sceneData 场景数据
	 * @return {@code Map<String, PieceInfo> }
	 * @throws
	 * @see SceneData
	 * @see Map<String, PieceInfo>
	 */
	private Map<String, PieceInfo> getPieces(SceneData sceneData) {

		List<PieceInfo> pieces = JsonUtil.toJsonObjectArray(sceneData.getPieces().toString(),
				PieceInfo.class);

		Map<String, PieceInfo> piecesInfo = Maps.newHashMap();

		for (PieceInfo pieceInfo : pieces) {
			piecesInfo.put(pieceInfo.getPieceId(), pieceInfo);
		}

		return piecesInfo;
	}

	/**
	 * 获取地区
	 *
	 * @param sceneData 场景数据
	 * @return {@code Map<String, AreaInfo> }
	 * @throws
	 * @see SceneData
	 * @see Map<String, AreaInfo>
	 */
	private Map<String, AreaInfo> getAreas(SceneData sceneData) {

		JSONArray areas = sceneData.getAreas();
		Map<String, AreaInfo> areasInfo = Maps.newHashMap();
		Map<String, List<SituationConfig>> areaConfigMap = sceneData.getSituationConfigMap()
				.getOrDefault(SituationConfig.NodeType.AREA.getCode(), Maps.newHashMap());

		for (int i = 0; i < areas.size(); i++) {

			JSONObject area = areas.getJSONObject(i);
			if (area.getInteger("type") == 1) {
				// 层节点，跳过
				continue;
			}

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
			// 设置区域名称
			String name = areaConfig.getOrDefault(SituationConfig.Attribute.NAME.getCode(), null);
			// 设置区域颜色
			String color = areaConfig.getOrDefault(SituationConfig.Attribute.COLOR.getCode(), null);
			areaInfo.setConfig(name, color);

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

	/**
	 * 获取元素
	 *
	 * @param sceneData 场景数据
	 * @return {@code Map<String, ElementInfo> }
	 * @throws
	 * @see SceneData
	 * @see Map<String, ElementInfo>
	 */
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
			Integer mpElementType = element.getInteger("type");
			JSONObject elementExtendedInfo = extendElementInfo.getJSONObject(elementId);

			// 处理设备类型（虚/实）
			String virtualType = Optional.ofNullable(elementExtendedInfo).map(
					jsonObject -> jsonObject.getString("image_type")).orElse("");
			String deviceType = "entity".equals(virtualType) ? "entity" : "virtual";

			// 处理类型数据
			Integer category = Optional.ofNullable(elementExtendedInfo).map(
					jsonObject -> jsonObject.getInteger("category")).orElse(Integer.MAX_VALUE);
			Integer category2 = Optional.ofNullable(elementExtendedInfo).map(
					jsonObject -> jsonObject.getInteger("category2")).orElse(Integer.MAX_VALUE);
			String firstType = categorieMap.getFirstCategorie().getOrDefault(category, "其他");
			String secondType = categorieMap.getSecondCategorie().getOrDefault(category2, "其他");

			String icon3 = (String) element.getOrDefault("icon3", "");
			String type = StringUtils.isEmpty(icon3) ? firstType + "-" + secondType : "定制-" + icon3;

			// 处理角色数据
			Integer roleId = Optional.ofNullable(elementExtendedInfo).map(
					jsonObject -> jsonObject.getInteger("role")).orElse(Integer.MAX_VALUE);
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
			if (LAYER_ELEMENT_TYPE.equals(mpElementType)) {
				position2 = Optional.ofNullable(element).map(obj -> obj.getJSONObject("position2"))
						.map(obj -> obj.getJSONObject("origin")).orElse(new JSONObject());
			}
			if (position2 == null) {
				log.error("Failed to get node coordinates！Scene_id is [{}]",
						sceneData.getSceneId());
				throw new BusinessException(SituationErrorCode.NODE_HAS_NO_COORDINATES);
			}
			Float x = position2.getFloat("x");
			Float y = position2.getFloat("y");
			if (Objects.nonNull(x)) {
				sceneData.getXList().add(x);
			}
			if (Objects.nonNull(y)) {
				sceneData.getYList().add(y);
			}

			// 处理端口数据
			Map<String, PortInfo> portsInfo = Maps.newHashMap();
			JSONArray ports = (JSONArray) element.getOrDefault("ports",
					JSON.parseArray(Lists.newArrayList().toString()));
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
			if (LAYER_ELEMENT_TYPE.equals(mpElementType)) {
				elementInfo.setType("自定义-层节点");
			} else {
				elementInfo.setType(type);
			}
			elementInfo.setActionStatus(getElementActionStatus(element));
			elementInfo.setStatus(getElementStatus(element));
			elementInfo.setPosition(new PlaneCoordinate(x, y));
			elementInfo.setPorts(portsInfo);
			elementInfo.setHierarchy(hierarchy);

			// 提取自定义配置
			Map<Integer, String> elementConfig = getNodeConfig(elementConfigMap, elementId,elementName);
			// 设置节点名称
			String name = elementConfig.getOrDefault(SituationConfig.Attribute.NAME.getCode(), null);
			// 设置节点高度
			String high= elementConfig.getOrDefault(SituationConfig.Attribute.HEIGHT.getCode(),null);
			// 设置节点颜色
			String color = elementConfig.getOrDefault(SituationConfig.Attribute.COLOR.getCode(), null);
			elementInfo.setConfig(name, high, color);

			// 提取样式数据
			Display display = JsonUtil.toJsonObject(element.getString("display_context"), Display.class);

			if (display != null) {
				elementInfo.setFontStyle(new FontStyle(Optional.of(display).map(Display::getText).map(Display.Label::getFill)
						.orElse(""), Optional.of(display).map(Display::getText).map(Display.Label::getFontSize).orElse(12)));
			}
			//设置一级类型的编码
			elementInfo.setOneCategoryCode(category);

			//设置二级类型的编码
			elementInfo.setSecondCategoryCode(category2);

			// 写入节点列表
			elementsInfo.put(elementId, elementInfo);
		}

		return elementsInfo;
	}

	/**
	 * 获取节点配置
	 *
	 * @param situationConfigMap 态势配置映射
	 * @param nodeId             节点id
	 * @param nodeName           节点名称
	 * @return {@code Map<Integer, String> }
	 * @throws
	 * @see Map<String, List<SituationConfig>>
	 * @see String
	 * @see String
	 * @see Map<Integer, String>
	 */
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

	/**
	 * 获取连接
	 *
	 * @param sceneData 场景数据
	 * @return {@code List<ConnectionInfo> }
	 * @throws
	 * @see SceneData
	 * @see List<ConnectionInfo>
	 */
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

	/**
	 * 获取场景信息
	 *
	 * @param sceneData 场景数据
	 * @return {@code SceneInfo }
	 * @throws
	 * @see SceneData
	 * @see SceneInfo
	 */
	private SceneInfo getSceneInfo(SceneData sceneData) {

		// 构造场景基础信息
		SceneInfo sceneInfo = new SceneInfo();
		sceneInfo.setId(sceneData.getSceneId());
		sceneInfo.setName(sceneData.getSceneName());
		sceneInfo.setDesc(sceneData.getSceneDesc());
		sceneInfo.setScope(getSceneScope(sceneData.getXList(), sceneData.getYList()));

		return sceneInfo;

	}

	/**
	 * 获取线颜色
	 *
	 * @return {@code String }
	 * @throws
	 * @see String
	 */
	@Override
	public String getLineColor() {

		return Color.getRandomValue(Color.values());

	}

	/**
	 * 获取线宽度
	 *
	 * @return {@code Integer }
	 * @throws
	 * @see Integer
	 */
	@Override
	public Integer getLineWidth() {

		return DEFAULT_LINE_WIDTH;

	}

	/**
	 * 获取线类型
	 *
	 * @return {@code String }
	 * @throws
	 * @see String
	 */
	@Override
	public String getLineType() {

		return LineType.getRandomValue(LineType.values());

	}

	/**
	 * 获取线风格
	 *
	 * @return {@code LineStyle }
	 * @throws
	 * @see LineStyle
	 */
	@Override
	public LineStyle getLineStyle() {
		LineStyle lineStyle = new LineStyle();
		lineStyle.setLineType(getLineType());
		lineStyle.setWidth(getLineWidth());
		lineStyle.setColor(getLineColor());

		return lineStyle;
	}

	/**
	 * 获取线动效
	 *
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 */
	@Override
	public List<String> getLineEffect() {

		return LineEffect.getRandomEffect(LineEffect.values());
	}

	/**
	 * 获取场景范围
	 *
	 * @param xList X 列表
	 * @param yList Y 列表
	 * @return {@code List<PlaneCoordinate> }
	 * @throws
	 * @see List<Float>
	 * @see List<Float>
	 * @see List<PlaneCoordinate>
	 */
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

	/**
	 * 获取面积形状
	 *
	 * @param area 面积
	 * @return {@code String }
	 * @throws
	 * @see JSONObject
	 * @see String
	 */
	@Override
	public String getAreaShape(JSONObject area) {
		return AreaShape.RECTANGLE.getCode();
	}

	/**
	 * 获取面积状态
	 *
	 * @param area 面积
	 * @return {@code String }
	 * @throws
	 * @see JSONObject
	 * @see String
	 */
	@Override
	public String getAreaStatus(JSONObject area) {
		return AreaStatus.NORMAL.getCode();
	}

	/**
	 * 获取元素状态
	 *
	 * @param element 元素
	 * @return {@code String }
	 * @throws
	 * @see JSONObject
	 * @see String
	 */
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

	/**
	 * 获取元素行动状态
	 *
	 * @param element 元素
	 * @return {@code String }
	 * @throws
	 * @see JSONObject
	 * @see String
	 */
	@Override
	public String getElementActionStatus(JSONObject element) {
		return ElementActionStatus.NORMAL.getCode();
	}

	/**
	 * 获取元素地区
	 *
	 * @param x         x
	 * @param y         y
	 * @param areasInfo 区域信息
	 * @return {@code List<String> }
	 * @throws
	 * @see Float
	 * @see Float
	 * @see Map<String, AreaInfo>
	 * @see List<String>
	 */
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

	/**
	 * 获取攻击事件列表
	 *
	 * @param flowLogs 流量日志
	 * @param sceneId  场景id
	 * @return {@code List<WsMessage> }
	 * @throws
	 * @see List<JSONObject>
	 * @see String
	 * @see List<WsMessage>
	 */
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
				log.info("MpEventType.UNKNOWN , this flowLog  jsonObject is [{}]", flowLog);
				mpEventType = MpEventType.UNKNOWN;
			}

			attackEvent.setType(mpEventType.getActionType().getCode());

			attackEvent.setSourceId(flowLog.getString("source_node_id"));
			attackEvent.setTargetId(flowLog.getString("target_node_id"));

			// 设置动效配置
			attackEvent.setLineStyle(
					new LineStyle(Color.RED.getCode(), 5, LineType.DASHED_FLY.getCode()));
			attackEvent.setLookAtTarget(new SecureRandom().nextBoolean());

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

	/**
	 * 获取随机WS多节点动态
	 *
	 * @param sceneId    场景id
	 * @param actionCode 行动代号
	 * @return {@code WsMultiNodeDynamic }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see WsMultiNodeDynamic
	 */
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
				JSON.parseArray(Lists.newArrayList().toString()));
		JSONArray areas = (JSONArray) topology.getOrDefault("areas",
				JSON.parseArray(Lists.newArrayList().toString()));

		WsMultiNodeDynamic wsEvent = null;
		if ("compound".equals(actionType.getType()) && !areas.isEmpty() && !elements.isEmpty()
				&& new SecureRandom().nextBoolean()) {
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

		// 设置动效配置
		wsEvent.setLineStyle(new LineStyle(Color.getRandomValue(Color.values()), 5,
				LineType.getRandomValue(LineType.values())));
		wsEvent.setLookAtTarget(new SecureRandom().nextBoolean());

		// 补充动态描述
		wsEvent.setDescription(wsEvent.getDescription().replace("$攻击$", actionType.getDesc()));

		return wsEvent;
	}

	/**
	 * 获取随机WS多面积动态
	 *
	 * @param elements 元素
	 * @param areas    地区
	 * @return {@code WsMultiAreaDynamic }
	 * @throws
	 * @see JSONArray
	 * @see JSONArray
	 * @see WsMultiAreaDynamic
	 */
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

	/**
	 * 获取随机WS多元素动态
	 *
	 * @param elements 元素
	 * @return {@code WsMultiElementDynamic }
	 * @throws
	 * @see JSONArray
	 * @see WsMultiElementDynamic
	 */
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

	/**
	 * 推送数据自房间
	 *
	 * @param data    数据
	 * @param sceneId 场景id
	 * @throws
	 * @see T
	 * @see String
	 */
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

	/**
	 * 批量推送数据自房间
	 *
	 * @param sceneId  场景id
	 * @param datas    数据
	 * @param number   数
	 * @param interval 间隔
	 * @throws
	 * @see String
	 * @see List<WsMessage>
	 * @see Integer
	 * @see Integer
	 */
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
			log.info("sleep [{}]s ,delay send flow event", interval);
			Thread.sleep(interval * 1000L);
		}

	}

	/**
	 * 推送链接状态
	 *
	 * @param sceneId 场景id
	 * @return {@code WsMessage<WsNodeStatus> }
	 * @throws
	 * @see String
	 * @see WsMessage<WsNodeStatus>
	 */
	@Override
	public WsMessage<WsNodeStatus> pushLinkStatus(String sceneId) {
		WsNodeStatus wsNodeStatus = topoHandlerService.getNodeStatusMessage(sceneId);

		// 推送ws消息
		WsMessage<WsNodeStatus> wsMessage = new WsMessage<>(MessageType.NodeStatus.getDesc(),
				wsNodeStatus);
		pushDataToRoom(wsMessage, sceneId);

		return wsMessage;
	}

	/**
	 * 推送流量事件
	 *
	 * @param flowLogReq 流量日志请求体
	 * @return {@code List<WsMessage> }
	 * @throws
	 * @see FlowLogReq
	 * @see List<WsMessage>
	 */
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

	/**
	 * 获取态势拓扑由场景id
	 *
	 * @param sceneId    场景id
	 * @param isInstance 是否实例
	 * @return {@code SituationTopo }
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see SituationTopo
	 */
	@Override
	public SituationTopo getSituationTopoBySceneId(String sceneId, Boolean isInstance) {
		SceneData sceneData;
		if (isInstance != null && !isInstance) {
			sceneData = topoHandlerService.getSceneData(null, sceneId);
		} else {
			sceneData = topoHandlerService.getSceneData(sceneId, null);
		}
		return getSituationTopo(sceneData);
	}

	/**
	 * 推送主机事件
	 *
	 * @param sceneId 场景id
	 * @return {@code List<WsMessage> }
	 * @throws
	 * @see String
	 * @see List<WsMessage>
	 */
	@Override
	public List<WsMessage> pushHostEvents(String sceneId) {
		// 获取主机事件日志
		SceneEventHostListReq req = new SceneEventHostListReq(sceneId);
		List<MpHostEvent> hostEventList = remoteSceneService.getHostEventList(req);

		// 处理流量日志
		List<WsMessage> wsSingleNodeDynamics = getHostEventList(hostEventList, sceneId);

		// 分批推送消息
		batchPushDataToRoom(sceneId, wsSingleNodeDynamics, 5, 5);

		return wsSingleNodeDynamics;
	}

	/**
	 * 获取主机事件列表
	 *
	 * @param hostEventList 主机事件列表
	 * @param sceneId       场景id
	 * @return {@code List<WsMessage> }
	 * @throws
	 * @see List<MpHostEvent>
	 * @see String
	 * @see List<WsMessage>
	 */
	private List<WsMessage> getHostEventList(List<MpHostEvent> hostEventList, String sceneId) {
		List<WsMessage> messageList = Lists.newArrayList();

		Date lastTime = lastTimeCacheHostEvent.getIfPresent(sceneId);
		// 倒序，事件早的在前
		for (MpHostEvent event : Lists.reverse(hostEventList)) {
			Date time = event.getTime();

			// 如果时间小于缓存值，跳过不处理
			if (lastTime != null && (lastTime.after(time) || lastTime.equals(time))) {
				continue;
			}

			// 当缓存值为空或此日志时间大于缓存值时，更新缓存值
			Date currentLastTime = lastTimeCacheHostEvent.getIfPresent(sceneId);
			if (currentLastTime == null || currentLastTime.before(time)) {
				lastTimeCacheHostEvent.put(sceneId, time);
			}

			// 多个攻击类型触发多个动效
			for (String attack : event.getAtt_cks()) {
				// 获取类型
				Integer category = SituationGuideScheme.AttackUrlEnum.getEnumByEnglish(attack)
						.getCode();

				WsMultiNodeEffect effect = new WsMultiNodeEffect();
				effect.setSceneId(sceneId);
				effect.setDuration(3);
				effect.setName(event.getEventName());
				effect.setCategory(category);
				effect.setEventType(SituationGuideScheme.EventType.ATTACK.getCode());
				effect.setSourceId(event.getTargetNodeId());
				effect.setTargetId(event.getTargetNodeId());
				effect.setResult(SituationGuideScheme.ResultType.SUCCESS.getCode());
				effect.setRole(SituationGuideScheme.RoleType.RED.getCode());
				effect.setLineStyle(new LineStyle("#FF0000", 3, "straight"));
				// 生成消息
				WsMessage<WsMultiNodeEffect> wsMessage = new WsMessage<>(
						MessageType.DirectorSchedulingEE.getDesc(), effect);
				messageList.add(wsMessage);
			}
		}

		return messageList;
	}

}