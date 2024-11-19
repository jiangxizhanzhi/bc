package com.cyberpeace.situation.service.impl;

import static com.cyberpeace.situation.constant.SituationConstants.DEMO_SCENE_ID;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.integratation.model.scene.SceneEventHostListReq;
import com.cyberpeace.situation.constant.SituationErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.SceneFlowContext;
import com.cyberpeace.cs.service.context.scene.Cpcollection;
import com.cyberpeace.integratation.model.mp.MpHostState;
import com.cyberpeace.integratation.model.mp.MpNodeStatus;
import com.cyberpeace.integratation.model.scene.ElementCommonContext;
import com.cyberpeace.integratation.model.scene.FlowLogsPageContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.situation.controller.res.ElementStateRes;
import com.cyberpeace.situation.dao.entity.SituationConfig;
import com.cyberpeace.situation.dao.mapper.SituationConfigMapper;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.model.ws.WsNodeStatus;
import com.cyberpeace.situation.req.FlowLogReq;
import com.cyberpeace.situation.req.SituationConfigFilterReq;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.cyberpeace.situation.service.context.ConnectionStatusContext;
import com.cyberpeace.situation.service.context.ElementStatusContext;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.log4j.Log4j2;

/**
 * 拓扑处理器Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see TopoHandlerService
 * @since 1.0.0
 */
@Log4j2
@Service
public class TopoHandlerServiceImpl implements TopoHandlerService {

	/**
	 * 远程场景Service
	 *
	 * @see RemoteSceneService
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 远程拓扑Service
	 *
	 * @see RemoteTopoService
	 */
	@Autowired
	private RemoteTopoService remoteTopoService;

	/**
	 * 远程终端Service
	 *
	 * @see RemoteTerminalService
	 */
	@Autowired
	private RemoteTerminalService remoteTerminalService;

	/**
	 * 当地场景Service
	 *
	 * @see LocalSceneService
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 态势配置Mapper
	 *
	 * @see SituationConfigMapper
	 */
	@Autowired
	private SituationConfigMapper situationConfigMapper;

	/**
	 * 中台场景删除事件代号
	 *
	 * @see Integer
	 */
	private static final Integer MP_SCENE_DELETE_EVENT_CODE = 9;

	/**
	 * 中台场景进展完成代号
	 *
	 * @see Integer
	 */
	private static final Integer MP_SCENE_PROGRESS_FINISH_CODE = 4;

	/**
	 * 场景
	 *
	 * @see String
	 */
	private static final String SCENE = "scene";

	/**
	 * 获取场景数据由CSenseid和面积id
	 *
	 * @param csceneInstanceId CSense实例id
	 * @param csceneId         CSenseid
	 * @param areaId           面积id
	 * @return {@code SceneData }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see SceneData
	 */
	@Override
	public SceneData getSceneDataByCsceneIdAndAreaId(String csceneInstanceId, String csceneId, String areaId) {
		// 获取中台场景数据，优先获取场景拓扑
		JSONObject sceneTopo;
		JSONObject topology;
		JSONObject plugins;
		Map<Integer, Map<String, List<SituationConfig>>> situationConfigMap;

		if (StringUtils.isBlank(csceneInstanceId)) {

			sceneTopo = remoteTopoService.getTopoByIdAndAreaIdFor3D(csceneId, areaId);
			topology = sceneTopo.getJSONObject("scene_config").getJSONObject("topology");
			plugins = sceneTopo.getJSONObject("scene_config").getJSONObject("plugins");
			situationConfigMap = getSituationConfigMap(csceneId);
		} else {
			sceneTopo = remoteSceneService.getByIdAndAreaIdFor3D(csceneInstanceId, areaId);
			topology = sceneTopo.getJSONObject(SCENE).getJSONObject("topology");
			plugins = sceneTopo.getJSONObject(SCENE).getJSONObject("plugins");
			situationConfigMap = getSituationConfigMap(csceneInstanceId);
		}

		return constructSceneData(csceneInstanceId, topology, plugins, situationConfigMap);
	}

	/**
	 * 获取场景数据
	 *
	 * @param sceneId 场景id
	 * @param topoId  拓扑id
	 * @return {@code SceneData }
	 * @throws
	 * @see String
	 * @see String
	 * @see SceneData
	 */
	@Override
	public SceneData getSceneData(String sceneId, String topoId) {

		// 获取中台场景数据，优先获取场景拓扑
		JSONObject sceneTopo;
		JSONObject topology;
		JSONObject plugins;
		Map<Integer, Map<String, List<SituationConfig>>> situationConfigMap;

		// 提取有效数据
		if (StringUtils.isBlank(sceneId)) {
			sceneTopo = remoteTopoService.getTopoById(topoId);
			topology = sceneTopo.getJSONObject("scene_config").getJSONObject("topology");
			plugins = sceneTopo.getJSONObject("scene_config").getJSONObject("plugins");
			situationConfigMap = getSituationConfigMap(topoId);
		} else {
			sceneTopo = getSceneTopoBySceneId(sceneId);
			topology = sceneTopo.getJSONObject(SCENE).getJSONObject("topology");
			plugins = sceneTopo.getJSONObject(SCENE).getJSONObject("plugins");
			situationConfigMap = getSituationConfigMap(sceneId);
		}
		return constructSceneData(sceneId, topology, plugins, situationConfigMap);
	}

	/**
	 * 构建场景数据
	 *
	 * @param sceneId            场景id
	 * @param topology           拓扑
	 * @param plugins            插件
	 * @param situationConfigMap 态势配置映射
	 * @return {@code SceneData }
	 * @throws
	 * @see String
	 * @see JSONObject
	 * @see JSONObject
	 * @see Map<Integer, Map<String, List<SituationConfig>>>
	 * @see SceneData
	 */
	private SceneData constructSceneData(String sceneId, JSONObject topology, JSONObject plugins, Map<Integer, Map<String, List<SituationConfig>>> situationConfigMap) {
		JSONObject extendElementInfo = (JSONObject) ((JSONObject) plugins.getOrDefault("cpcloud",
				new JSONObject())).getOrDefault("elements", new JSONObject());

		// 获取中台靶标类型数据
		JSONObject deviceCategorie = remoteTerminalService.getAllCategorie();

		// 处理靶标类型数据
		CategorieMap categorieMap = getCategorieMap(deviceCategorie);

		// 构造场景数据
		SceneData sceneData = new SceneData();
		sceneData.setSceneId(sceneId);
		sceneData.setSceneName(topology.getJSONObject(SCENE).getString("name"));
		sceneData.setSceneDesc(topology.getJSONObject(SCENE).getString("description"));
		sceneData.setAreas((JSONArray) topology.getOrDefault("areas",
				JSON.parseArray(Lists.newArrayList().toString())));
		sceneData.setElements((JSONArray) topology.getOrDefault("elements",
				JSON.parseArray(Lists.newArrayList().toString())));
		sceneData.setConnections((JSONArray) topology.getOrDefault("connections",
				JSON.parseArray(Lists.newArrayList().toString())));
		sceneData.setPieces((JSONArray) topology.getOrDefault("pieces",
				JSON.parseArray(Lists.newArrayList().toString())));
		sceneData.setExtendElementInfo(extendElementInfo);
		sceneData.setXList(Lists.newArrayList());
		sceneData.setYList(Lists.newArrayList());
		sceneData.setExternalNetwork(Lists.newArrayList());
		sceneData.setPortElementMap(Maps.newHashMap());
		sceneData.setCategorieMap(categorieMap);
		sceneData.setSituationConfigMap(situationConfigMap);

		return sceneData;
	}

	/**
	 * 获取态势配置映射
	 *
	 * @param scope 范围
	 * @return {@code Map<Integer, Map<String, List<SituationConfig>>> }
	 * @throws
	 * @see String
	 * @see Map<Integer, Map<String, List<SituationConfig>>>
	 */
	private Map<Integer, Map<String, List<SituationConfig>>> getSituationConfigMap(String scope) {

		Map<Integer, Map<String, List<SituationConfig>>> situationConfigMap = Maps.newHashMap();

		// 获取场景相关的态势配置
		List<String> relatedIds = situationConfigMapper
				.filterIds(new SituationConfigFilterReq(scope));
		relatedIds.addAll(situationConfigMapper.filterIds(new SituationConfigFilterReq("[]")));

		// 构造当前场景相关配置
		if (!relatedIds.isEmpty()) {

			List<SituationConfig> situationConfigList = situationConfigMapper
					.selectBatchIds(relatedIds);

			for (SituationConfig situationConfig : situationConfigList) {

				Map<String, List<SituationConfig>> nodeTypeMap = situationConfigMap
						.get(situationConfig.getNodeType());
				if (nodeTypeMap == null) {
					nodeTypeMap = Maps.newHashMap();
					situationConfigMap.put(situationConfig.getNodeType(), nodeTypeMap);
				}

				// 获取节点id配置
				String nodeId = situationConfig.getNodeId();
				if (nodeId != null) {
					List<SituationConfig> nodeSituationConfigList = nodeTypeMap.getOrDefault(nodeId,
							Lists.newArrayList());
					nodeSituationConfigList.add(situationConfig);
					nodeTypeMap.put(nodeId, nodeSituationConfigList);
				}

				// 获取节点名称配置
				String nodeName = situationConfig.getNodeName();
				if (nodeName != null) {
					List<SituationConfig> nodeSituationConfigList = nodeTypeMap
							.getOrDefault(nodeName, Lists.newArrayList());
					nodeSituationConfigList.add(situationConfig);
					nodeTypeMap.put(nodeName, nodeSituationConfigList);
				}
			}

		}

		return situationConfigMap;
	}

	/**
	 * 获取场景拓扑由场景id
	 *
	 * @param sceneId 场景id
	 * @return {@code JSONObject }
	 * @throws
	 * @see String
	 * @see JSONObject
	 */
	@Override
	public JSONObject getSceneTopoBySceneId(String sceneId) {
		if (DEMO_SCENE_ID.equals(sceneId)) {
			return getDemoSceneTopo();
		} else {
			return remoteSceneService.getById(sceneId, null);
		}
	}

	/**
	 * 获取演示场景拓扑
	 *
	 * @return {@code JSONObject }
	 * @throws
	 * @see JSONObject
	 */
	private JSONObject getDemoSceneTopo() {
		InputStream inputStream = getClass().getResourceAsStream("/demo.json");
		return JsonUtil.toJsonObject(inputStream, JSONObject.class).getJSONObject("data");
	}

	/**
	 * 获取分类映射
	 *
	 * @param deviceCategorie 设备分类
	 * @return {@code CategorieMap }
	 * @throws
	 * @see JSONObject
	 * @see CategorieMap
	 */
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
			if (deviceRole.getChild() != null) {
				for (DeviceRole childDeviceRole : deviceRole.getChild()) {
					role.put(childDeviceRole.getId(), deviceRole.getName());
				}
			}
		}

		// 构造返回数据
		CategorieMap categorieMap = new CategorieMap();
		categorieMap.setFirstCategorie(firstCategorie);
		categorieMap.setSecondCategorie(secondCategorie);
		categorieMap.setRole(role);

		return categorieMap;
	}

	/**
	 * 获取随机节点
	 *
	 * @param nodes 节点
	 * @param num   数量
	 * @return {@code List<JSONObject> }
	 * @throws
	 * @see JSONArray
	 * @see Integer
	 * @see List<JSONObject>
	 */
	@Override
	public List<JSONObject> getRandomNodes(JSONArray nodes, Integer num) {
		List<Integer> indexs = Lists.newArrayList();
		List<JSONObject> randomNodes = Lists.newArrayList();

		for (int i = 0; i < num; i++) {
			while (true) {
				Integer j = (int) (Math.random() * nodes.size());
				if (!indexs.contains(j)) {
					randomNodes.add(nodes.getJSONObject(j));
					indexs.add(j);
					break;
				}
			}
		}
		return randomNodes;
	}

	/**
	 * 获取流量日志
	 *
	 * @param flowLogReq 流量日志请求体
	 * @return {@code List<JSONObject> }
	 * @throws
	 * @see FlowLogReq
	 * @see List<JSONObject>
	 */
	@Override
	public List<JSONObject> getFlowLogs(FlowLogReq flowLogReq) {

		// 构造请求中台参数
		FlowLogsPageContext flowLogsPageContext = BeanUtils.copy(flowLogReq,
				FlowLogsPageContext.class);
		flowLogsPageContext.setId(flowLogReq.getSceneId());

		// 请求中台数据
		JSONObject jsonObject = remoteSceneService.getFlowLogs(flowLogsPageContext);

		return JsonUtil.toJsonObjectArray(jsonObject.getJSONArray("list").toString(),
				JSONObject.class);
	}

	/**
	 * 获取元素状态
	 *
	 * @param sceneId   场景id
	 * @param elementId 元素id
	 * @return {@code ElementStateRes }
	 * @throws
	 * @see String
	 * @see String
	 * @see ElementStateRes
	 */
	@Override
	public ElementStateRes getElementState(String sceneId, String elementId) {
		ElementCommonContext elementCommonContext = new ElementCommonContext();
		elementCommonContext.setElementIds(elementId);
		elementCommonContext.setSceneId(sceneId);

		ElementStateRes elementStateRes = new ElementStateRes();
		List<MpHostState> mpHostStates = remoteSceneService.getHostState(elementCommonContext);
		if (mpHostStates.size() == 1) {
			elementStateRes.setHostState(mpHostStates.get(0));
		}
		elementStateRes.setEventAggr(remoteSceneService.getEventAggr(elementCommonContext));
		elementStateRes.setFlowTrend(remoteSceneService.getFlowTrend(elementCommonContext));
		elementStateRes.setRiskCount(remoteSceneService.getRiskCount(elementCommonContext));

		return elementStateRes;
	}

	/**
	 * 获取节点状态消息
	 *
	 * @param sceneId 场景id
	 * @return {@code WsNodeStatus }
	 * @throws
	 * @see String
	 * @see WsNodeStatus
	 */
	@Override
	public WsNodeStatus getNodeStatusMessage(String sceneId) {

		List<MpNodeStatus> nodeStatusList = remoteSceneService.getConnectionStatus(sceneId);

		WsNodeStatus wsNodeStatus = new WsNodeStatus();

		for (MpNodeStatus nodeStatus : nodeStatusList) {

			wsNodeStatus.getElementStatus()
					.add(BeanUtils.copy(nodeStatus, ElementStatusContext.class));

			nodeStatus.getConnectionStatusList().forEach(connectionStatus -> {
				wsNodeStatus.getConnectionStatus()
						.add(BeanUtils.copy(connectionStatus, ConnectionStatusContext.class));
			});

		}

		return wsNodeStatus;

	}

	/**
	 * 获取中台场景数据
	 *
	 * @param sceneId 场景id
	 * @return {@code MpSceneData }
	 * @throws
	 * @see String
	 * @see MpSceneData
	 */
	@Override
	public MpSceneData getMpSceneData(String sceneId) {
		JSONObject sceneData = localSceneService.getSceneData(sceneId, null);
		if (sceneData == null) {
			return null;
		}
		return sceneData.toJavaObject(MpSceneData.class);
	}

	/**
	 * 检查场景状态
	 *
	 * @param mpSceneData 中台场景数据
	 * @return {@code Boolean }
	 * @throws
	 * @see MpSceneData
	 * @see Boolean
	 */
	@Override
	public Boolean checkSceneStatus(MpSceneData mpSceneData) {

		if (mpSceneData == null) {
			return false;
		}

		Integer event = mpSceneData.getTopology().getScene().getEvent();
		Integer eventStatus = mpSceneData.getTopology().getScene().getEvent_status();

		return !event.equals(MP_SCENE_DELETE_EVENT_CODE)
				|| !eventStatus.equals(MP_SCENE_PROGRESS_FINISH_CODE);
	}

	/**
	 * 使流量自动跑
	 *
	 * @param mpSceneData 中台场景数据
	 * @return {@code Boolean }
	 * @throws
	 * @see MpSceneData
	 * @see Boolean
	 */
	@Override
	public Boolean enableFlowAutoRun(MpSceneData mpSceneData) {
		return !localSceneService.getSceneFlowList(mpSceneData, null).isEmpty();
	}

	/**
	 * 使流量收集
	 *
	 * @param mpSceneData 中台场景数据
	 * @return {@code Boolean }
	 * @throws
	 * @see MpSceneData
	 * @see Boolean
	 */
	@Override
	public Boolean enableFlowCollect(MpSceneData mpSceneData) {

		Cpcollection cpCollection = mpSceneData.getPlugins().getCpcollection();

		if (cpCollection == null) {
			return false;
		}

		if (cpCollection.getScene() == null) {
			return false;
		}

		Boolean enableFlowCollect = cpCollection.getScene().getEnable_flow_collect();
		return !(enableFlowCollect == null || !enableFlowCollect);

	}

	/**
	 * 流量自动跑
	 *
	 * @param sceneId         场景id
	 * @param mpSceneData     中台场景数据
	 * @param interactiveType 交互式类型
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see MpSceneData
	 * @see Integer
	 * @see Boolean
	 */
	@Override
	public Boolean flowAutoRun(String sceneId, MpSceneData mpSceneData, Integer interactiveType) {
		Map<String, SceneFlowContext> sceneFlowContextMap = localSceneService
				.getSceneFlowList(mpSceneData, interactiveType);

		return localSceneService.startRandomFlow(sceneId, sceneFlowContextMap);
	}
}