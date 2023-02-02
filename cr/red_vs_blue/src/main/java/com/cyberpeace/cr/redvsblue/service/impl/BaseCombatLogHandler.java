package com.cyberpeace.cr.redvsblue.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants.*;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbCombatRecordsAllDataRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.*;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbCombatLogMapper;
import com.cyberpeace.cr.redvsblue.service.context.NodeAscription;
import com.cyberpeace.cr.redvsblue.service.context.SingleNodeAscription;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.integratation.service.RemoteSceneService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Data
@Service
public class BaseCombatLogHandler {

	private static Map<String, BaseCombatLogHandler> register = new HashMap<>();

	static Map<String, BaseCombatLogHandler> map = new HashMap<>();

	private Map<String, String> initData;
	Integer eventType = EventType.BRUTEFORCE_EXT.getCode();
	Integer eventCategory = EventCategory.OTHER.getCode();
	Integer attackPhase = AttackPhase.OTHER.getCode();
	Integer defensePhase = DefensePhase.OTHER.getCode();
	Integer attackAction = AttackAction.OTHER.getCode();
	Integer defenseAction = DefenseAction.OTHER.getCode();
	Integer actionType = ActionType.OTHER.getCode();
	Integer attackPattern = AttackPattern.OTHER.getCode();
	RbCombatLog instance = null;
	Boolean isLostHostSign = false;

	@Autowired
	private RbCombatLogMapper rbCombatLogMapper;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private CrRoleMapper crRoleMapper;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	@PostConstruct
	public void init() {
		if (register.containsKey(getCode())) {
			throw new Error("declareMessageType=[" + getCode() + "] repeat");
		}
		register.put(getCode(), this);
	}

	public String getCode() {
		return "base_combat";
	}

	public BaseCombatLogHandler get(String type, Map<String, String> initData) {
		BaseCombatLogHandler combatLogHandler = register.get(type);
		combatLogHandler.setInitData(initData);
		setAttr();
		return combatLogHandler;
	}

	public void setAttr() {
		;
	}

	public String getDestIp() {
		return initData.get("agent_ip");
	}

	public String getSourceIp() {
		if (initData.get("src_ip") != null) {
			return initData.get("src_ip");
		} else {
			return initData.get("detail.src_ip");
		}
	}

	public NodeAscription getNodeAscription() {
		return getNodeAscription(Arrays.asList(getDestIp(), getSourceIp()));
	}

	public Integer getDestGroup() {
		List<Integer> group = getNodeAscription().getAscription(getDestIp());
		if (group.size() != 1) {
			throw new BusinessException(CrBusinessErrorCode.DEST_GROUP_MISSING);
		} else {
			return group.get(0);
		}
	}

	public String getDestName() {
		String destName = getNodeAscription().getNodeData()
											 .get(getDestIp());
		return destName != null ? destName : initData.get("host_name");
	}

	public Integer getSourceGroup() {
		List<Integer> group = getNodeAscription().getAscription(getDestIp());
		if (group.size() == 1) {
			return group.get(0);
		} else if (group.size()==2 || getEventType().equals(EventCategory.ATTACK.getCode())) {
			return getDestGroup().equals(CrRole.RbRoleGroup.RED.getCode())? CrRole.RbRoleGroup.BLUE.getCode(): CrRole.RbRoleGroup.RED.getCode();
		} else {
			return null;
		}
	}

	public String getSourceName() {
		return getNodeAscription().getNodeData().get(getSourceIp());
	}

	public String getRedVsBlue() {
		NodeAscription nodeAscription = getNodeAscription();
		return nodeAscription.getRoomId();
	}

	public String getAgentName() {
		return initData.get("host_name");
	}

	public String getAgentIp() {
		return initData.get("agent_ip");
	}

	public String getEventContent() {
		return initData.get("event_content");
	}

	public Date getEventTime() {
		return new Date(Long.parseLong(initData.get("datatime")));
	}

	public Boolean getIsLostHostSign() {
		if (isLostHostSign && eventCategory.equals(EventCategory.ATTACK.getCode())) {
			RbCombatLog rbCombatLog = rbCombatLogMapper.selectOneByRoomIdAndAgentIpAndIsLostHost(getRedVsBlue(), getAgentIp(), true);
			if (rbCombatLog == null){
				return true;
			}
		}
		return false;
	}

	public void validateRbInfo() {
		if (getRedVsBlue() == null){
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_FOUND);
		}
		if (getSourceGroup().equals(getDestGroup())) {
			throw new BusinessException(CrBusinessErrorCode.GROUP_INFO_CONFLICT);
		}
	}

	public RbCombatLog getCombatLog() {
		RbCombatLog rbCombatLog = new RbCombatLog();
		rbCombatLog.setRoomId(getRedVsBlue());
		rbCombatLog.setEventType(getEventType());
		rbCombatLog.setEventCategory(getEventCategory());
		rbCombatLog.setAttackPhase(getAttackPhase());
		rbCombatLog.setDefensePhase(getDefensePhase());
		rbCombatLog.setAttackAction(getAttackAction());
		rbCombatLog.setDefenseAction(getDefenseAction());
		rbCombatLog.setActionType(getActionType());
		rbCombatLog.setSourceGroup(getSourceGroup());
		rbCombatLog.setSourceIp(getSourceIp());
		rbCombatLog.setSourceName(getSourceName());
		rbCombatLog.setDestGroup(getDestGroup());
		rbCombatLog.setDestIp(getDestIp());
		rbCombatLog.setDestName(getDestName());
		rbCombatLog.setAgentName(getAgentName());
		rbCombatLog.setAgentIp(getAgentIp());
		rbCombatLog.setEventContent(getEventContent());
		rbCombatLog.setEventTime(getEventTime());
		rbCombatLog.setIsLostHost(isLostHostSign);
		rbCombatLog.setOriginalLog(initData.get("original_log"));
		return rbCombatLog;
	}

	public void save() {
		validateRbInfo();
		RbCombatLog rbCombatLog = getCombatLog();
		setInstance(rbCombatLog);
		rbCombatLogMapper.insert(rbCombatLog);
	}

	public static void delayHandler(){
		Integer delay = (int)(Math.random()*10) + 10;
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage() {
		RbCombatRecordsAllDataRsp data = addData(null);
		for (RbAttackDefenseStage rbAttackDefenseStage: data.getRbAttackDefenseStages()){
			pushDataToRoom(rbAttackDefenseStage, getRedVsBlue());
		}
		for (RbLostHost rbLostHost: data.getLostHosts()) {
			pushDataToRoom(rbLostHost, getRedVsBlue());
		}
		for (RbAttackEvent rbAttackEvent: data.getRbAttackDefenseEvents()) {
			if (!rbAttackEvent.getVisible()) {
				delayHandler();
			}
			pushDataToRoom(rbAttackEvent, getRedVsBlue());
		}
	}

	public RbCombatRecordsAllDataRsp addData(RbCombatRecordsAllDataRsp data) {
		if (data == null){
			data = new RbCombatRecordsAllDataRsp();
		}
		addAttackDefenseStage(data, getInstance());
		addLostHost(data, getInstance());
		addAttackDefenseEvent(data, getInstance());
		return data;
	}

	public NodeAscription getNodeAscription(List<String> nodeIpList) {
		NodeAscription nodeAscription = new NodeAscription();
		// 默认同时只会启动一个场景
		LocalScene localScene = localSceneMapper.selectOne(LocalScene.BusinessCode.RED_VS_BLUE.getCode());
		if (localScene == null) {
			return nodeAscription;
		}
		List<CrRole> crRoleList = crRoleMapper.getRolesByRoom(localScene.getBusinessInstanceId(),
				BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
		List<JSONObject> nodeInfoList = remoteSceneService.getAllElements(localScene.getMpSceneId());
		List<SingleNodeAscription> singleNodeAscriptionList = new ArrayList<>();
		Map<String, String> nodeData = new HashMap<>();
		for (String nodeIp : nodeIpList) {
			if (nodeIp == null)
				continue;
			List<Integer> ascription = new ArrayList<>();
			SingleNodeAscription singleNodeAscription = new SingleNodeAscription(nodeIp, ascription);
			for (JSONObject nodeInfo : nodeInfoList) {
				List<JSONObject> ports = nodeInfo.getObject("ports", List.class);
				for (JSONObject port : ports) {
					if (port.getString("ip")
							.equals(nodeIp.trim())) {

						for (CrRole crRole : crRoleList) {
							if (crRole.getNodes() == null) {
								continue;
							}
							CrRoleNodesData crRoleNodesData = crRole.getNodeData();
							for (CrRoleNode crRoleNode : crRoleNodesData.getVisibleNodes()) {
								if (crRoleNode.getId()
											  .equals(nodeInfo.getString("id"))) {
									ascription.add(crRole.getRoleGroup());
									nodeData.put(nodeIp, nodeInfo.getString("name"));
								}
							}
						}
					}
				}
			}
			singleNodeAscriptionList.add(singleNodeAscription);
		}
		nodeAscription.setNodeAscriptionList(singleNodeAscriptionList);
		nodeAscription.setNodeData(nodeData);
		nodeAscription.setRoomId(localScene.getBusinessInstanceId());
		return nodeAscription;
	}


	public RbCombatRecordsAllDataRsp addAttackDefenseStage(RbCombatRecordsAllDataRsp data, RbCombatLog rbCombatLog) {
		if(!rbCombatLog.getAttackPhase().equals(CrCommonConstants.AttackPhase.OTHER.getCode())){
			RbAttackDefenseStage rbAttackDefenseStage = RbAttackDefenseStage.builder().
					resourceId(rbCombatLog.getResourceId()).group(rbCombatLog.getSourceGroup()).attackPhase(rbCombatLog.getAttackPhase()).build();
			data.getRbAttackDefenseStages().add(rbAttackDefenseStage);
		}
		return data;
	}

	public RbCombatRecordsAllDataRsp addLostHost(RbCombatRecordsAllDataRsp data, RbCombatLog rbCombatLog){
		if(rbCombatLog.getIsLostHost()){
			RbLostHost rbLostHost = RbLostHost.builder().
					resourceId(rbCombatLog.getResourceId()).group(rbCombatLog.getSourceGroup()).lostTime(rbCombatLog.getEventTime()).
					hostName(StringUtils.isBlank(rbCombatLog.getDestName()) ? rbCombatLog.getAgentName() :rbCombatLog.getDestName()).
					hostIp(StringUtils.isBlank(rbCombatLog.getDestIp()) ? rbCombatLog.getAgentIp() : rbCombatLog.getDestIp()).build();
			data.getLostHosts().add(rbLostHost);
		}
		return data;
	}

	public RbCombatRecordsAllDataRsp addAttackDefenseEvent(RbCombatRecordsAllDataRsp data, RbCombatLog rbCombatLog){
		String sourceGroup = getGroupName(rbCombatLog.getSourceGroup());
		String sourceIp = rbCombatLog.getSourceIp();
		String sourceName = rbCombatLog.getSourceName();
		String sourceMachine = getMachineName(sourceGroup, sourceIp, sourceName);
		String destGroup = getGroupName(rbCombatLog.getDestGroup());
		String destIp = rbCombatLog.getDestIp();
		String destName = rbCombatLog.getDestName();
		String destMachine = getMachineName(destGroup, destIp, destName);
		Boolean visible;
		String defenseAction;
		String attackEventDescribe;
		String defenseEventDescribe;
		if (!rbCombatLog.getAttackAction().equals(CrCommonConstants.AttackAction.OTHER.getCode()) ||
				!rbCombatLog.getDefenseAction().equals(CrCommonConstants.DefenseAction.OTHER.getCode())){
			if(rbCombatLog.getEventCategory().equals(CrCommonConstants.EventCategory.ATTACK.getCode()) &&
					!rbCombatLog.getAttackAction().equals(CrCommonConstants.AttackAction.OTHER.getCode())) {
				// TODO 获取attack action对应的AttackActionType
				String attackAction = CrCommonConstants.AttackAction.OTHER.getDesc();
				defenseAction = CrCommonConstants.DefenseAction.ATTACK_SOURCE_DETECTED.getDesc();
				attackEventDescribe = String.format("{%s}对{%s}发动【{%s}】攻击", sourceMachine, destMachine, attackAction);
				defenseEventDescribe = String.format("{%s}检测到来自{%s}的【{%s}】攻击", sourceMachine, destMachine, attackAction);
				visible = Boolean.FALSE;
				RbAttackEvent rbAttackEvent = RbAttackEvent.builder().visible(visible).resourceId(rbCombatLog.getResourceId()).
						group(null).type(CrCommonConstants.EventCategory.ATTACK.getDesc()).groupType(String.format("{%s}{%s}",sourceGroup, CrCommonConstants.EventCategory.ATTACK.getDesc()))
						.sourceGroup(sourceGroup).sourceIp(sourceIp).sourceName(sourceName).sourceMachine(sourceMachine)
						.destGroup(destGroup).destIp(destIp).destName(destName).destMachine(destMachine)
						.action(attackAction).eventTime(String.valueOf(rbCombatLog.getEventTime())).eventDescribe(attackEventDescribe).build();
				rbAttackEvent = updateAttackEvent(rbAttackEvent, rbCombatLog);
				data.getRbAttackDefenseEvents().add(rbAttackEvent);
			} else if (rbCombatLog.getEventCategory().equals(CrCommonConstants.EventCategory.DEFENSE.getCode()) &&
					!rbCombatLog.getDefenseAction().equals(CrCommonConstants.DefenseAction.OTHER.getCode())){
				visible = Boolean.TRUE;
				// TODO 匹配对应的防御行为类型
				defenseAction = CrCommonConstants.DefenseAction.CONFIGURE_FIREWALL_POLICY.getDesc();
				defenseEventDescribe = String.format("{%s}【{%s}】",destMachine, defenseAction);
			} else {
				return data;
			}
			RbAttackEvent rbDefenseEvent = RbAttackEvent.builder().visible(visible).resourceId(rbCombatLog.getResourceId()).
					group(null).type(CrCommonConstants.EventCategory.DEFENSE.getDesc()).groupType(String.format("{%s}{%s}",sourceGroup, CrCommonConstants.EventCategory.ATTACK.getDesc()))
					.sourceGroup(sourceGroup).sourceIp(sourceIp).sourceName(sourceName).sourceMachine(sourceMachine)
					.destGroup(destGroup).destIp(destIp).destName(destName).destMachine(destMachine)
					.action(defenseAction).eventTime(String.valueOf(rbCombatLog.getEventTime())).eventDescribe(defenseEventDescribe).build();
			rbDefenseEvent = updateDefenseEvent(rbDefenseEvent, rbCombatLog);
			data.getRbAttackDefenseEvents().add(rbDefenseEvent);

		}

		return data;
	}

	public RbAttackEvent updateDefenseEvent(RbAttackEvent rbDefenseEvent, RbCombatLog rbCombatLog) {
		if (rbDefenseEvent.getVisible()) {
			return rbDefenseEvent;
		}
		return null;
	}

	public RbAttackEvent updateAttackEvent(RbAttackEvent rbAttackEvent, RbCombatLog rbCombatLog) {
		return rbAttackEvent;
	}

	public String getMachineName(String group, String ip, String name) {
		if (StringUtils.isNotBlank(group) && StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(name)) {
			return String.format("%s的%s(%s)", group, name, ip);
		} else if (StringUtils.isNotBlank(group) && StringUtils.isNotBlank(ip)) {
			return String.format("%s(%s)", group, ip);
		} else if (StringUtils.isNotBlank(ip)) {
			return ip;
		} else {
			return group;
		}
	}

	public String getGroupName(Integer sourceGroup) {
		return Objects.equals(sourceGroup, CrRole.RbRoleGroup.RED.getCode()) ?CrRole.RbRoleGroup.RED.getDesc() : CrRole.RbRoleGroup.BLUE.getDesc();
	}

	public RbCombatRecordsAllDataRsp addData(RbCombatLog combatLog, RbCombatRecordsAllDataRsp combatRecordsAllDataRsp){
		combatRecordsAllDataRsp = addAttackDefenseStage(combatRecordsAllDataRsp, combatLog);
		combatRecordsAllDataRsp = addLostHost(combatRecordsAllDataRsp, combatLog);
		combatRecordsAllDataRsp = addAttackDefenseEvent(combatRecordsAllDataRsp, combatLog);
		return combatRecordsAllDataRsp;
	}

	public <T> void pushDataToRoom(T data, String resourceId) {

		RbWsMessageIdentifier identifier = new RbWsMessageIdentifier();
		identifier.setPath(WsPath.RB_COMBAT_LOG);
		identifier.setResourceId(resourceId);

		WebSocketMessageContext messageContext = WebSocketMessageContext.builder()
																		.data(JsonUtil.toJsonString(data,
																				PropertyNamingStrategy.SnakeCase))
																		.messageIdentifier(identifier).build();

		myWebSocketHandler.pushMessage(messageContext, true);
	}


}

