package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants.*;
import com.cyberpeace.cr.crbase.constant.RbSituationWsCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbCombatRecordsAllDataRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.*;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbCombatLogMapper;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbMaliceProcessMapper;
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

//	基础日志处理类
@Data
@Service
public class BaseCombatLogHandler {

	private static Map<String, BaseCombatLogHandler> register = new HashMap<>();

	static Map<String, BaseCombatLogHandler> map = new HashMap<>();

	private Map<String, String> initData;
	Integer eventType = EventType.OTHER.getCode();
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

	@Autowired
	private RbMaliceProcessMapper rbMaliceProcessMapper;

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

	public static BaseCombatLogHandler get(String type, Map<String, String> initData, RbCombatLog rbCombatLog) {
		BaseCombatLogHandler combatLogHandler = register.get(type);
		if (combatLogHandler == null) {
			combatLogHandler = register.get("common_combat");
		}
		combatLogHandler.setInitData(initData);
		combatLogHandler.setInstance(rbCombatLog);
		if (initData != null) {
			combatLogHandler.setAttr();
		}

		combatLogHandler.clear();

		return combatLogHandler;
	}

	public void setAttr() {
	}

	public void clear() {

	}

	public String getDestIp() {
		return initData.get("agent_ip");
	}

	public String getSourceIp() {
		String sourceIp = initData.get("src_ip");
		if (sourceIp != null) {
			return sourceIp;
		} else {
			sourceIp = initData.get("detail.src_ip");
		}
		if (sourceIp == null) {
			sourceIp = "";
		}
		return sourceIp;
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
		List<Integer> group = getNodeAscription().getAscription(getSourceIp());
		if (group.size() == 1) {
			return group.get(0);
		} else if (group.size()==2 || getEventCategory().equals(EventCategory.ATTACK.getCode())) {
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

		// 获取攻防阶段
		String phase = EventCategory.DEFENSE.getCode().equals(getEventCategory())
				? EnumUtils.getEnumDescByCode(getDefensePhase(), DefensePhase.class)
				: EnumUtils.getEnumDescByCode(getAttackPhase(), AttackPhase.class);

		return String.format("对%s的%s(%s)发起了%s", CrCommonUtil.getGroupName(getDestGroup()),
				getDestName(), getDestIp(), phase);
	}

	public String getEventTime() {
		return TimeUtil.format1(new Date(Long.parseLong(initData.get("datatime")) * 1000));
	}

	public Boolean getIsLostHost() {
		if (getIsLostHostSign() && getEventCategory().equals(EventCategory.ATTACK.getCode())) {
			List<RbCombatLog> rbCombatLog = rbCombatLogMapper.selectOneByRoomIdAndAgentIpAndIsLostHost(getRedVsBlue(), getAgentIp(), true);
			if (rbCombatLog.isEmpty()){
				return true;
			}
		}
		return false;
	}

	public void validateRbInfo() {
		if (getRedVsBlue() == null){
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_FOUND);
		}
		if (getSourceGroup() != null && getSourceGroup().equals(getDestGroup())) {
			throw new BusinessException(CrBusinessErrorCode.GROUP_INFO_CONFLICT);
		}
	}

	public RbCombatLog getCombatLog() {
		return RbCombatLog.builder().resourceId(IDGeneratorUtil.getID()).roomId(getRedVsBlue()).eventType(getEventType()).eventCategory(getEventCategory()).attackPhase(getAttackPhase())
						  .defensePhase(getDefensePhase()).attackAction(getAttackAction()).defenseAction(getDefenseAction())
						  .actionType(getActionType()).sourceGroup(getSourceGroup()).sourceIp(getSourceIp())
						  .sourceName(getSourceName()).destGroup(this.getDestGroup()).destIp(getDestIp())
						  .destName(getDestName()).agentName(getAgentName()).agentIp(getAgentIp())
						  .eventContent(getEventContent()).eventTime(TimeUtil.toDate1(getEventTime())).isLostHost(getIsLostHost())
						  .originalLog(initData.get("original_log")).build();
	}

	public void save() {
		validateRbInfo();
		RbCombatLog rbCombatLog = getCombatLog();
		setInstance(rbCombatLog);
		rbCombatLogMapper.insert(rbCombatLog);
	}

	public static void delayHandler(){
		Integer delay = (int)(Math.random()*10 + 10) * 1000;
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage() throws Exception {
		RbCombatRecordsAllDataRsp data = addData(null);
		data.setCode(RbSituationWsCode.LOG_HANDLER_RES_CODE);
		for (RbAttackEvent attackEvent: data.getRbAttackDefenseEvents()) {
			if (!attackEvent.getVisible()) {
				data.getRbAttackDefenseEvents().remove(attackEvent);
				pushDataToRoom(data, getRedVsBlue());
				RbCombatRecordsAllDataRsp rbCombatRecordsAllDataRsp = new RbCombatRecordsAllDataRsp();
				rbCombatRecordsAllDataRsp.getRbAttackDefenseEvents().add(attackEvent);
				rbCombatRecordsAllDataRsp.setCode(RbSituationWsCode.LOG_HANDLER_RES_CODE);
				// 处理防守消息延时
				delayHandler();
				pushDataToRoom(rbCombatRecordsAllDataRsp, getRedVsBlue());
				return;
			}
		}
		if(data.getRbAttackStages().size() > 1){
			List<RbAttackDefenseStage> rbAttackStages = data.getRbAttackStages();
			List<RbAttackEvent> rbAttackDefenseEvents = data.getRbAttackDefenseEvents();
			for(int i=0;i<3;i++){
				RbCombatRecordsAllDataRsp data1 = new RbCombatRecordsAllDataRsp();
				List<RbAttackEvent> attackEvents = new ArrayList<>();
				attackEvents.add(rbAttackDefenseEvents.get(i));
				data1.setRbAttackDefenseEvents(attackEvents);
				List<RbAttackDefenseStage> attackStage = new ArrayList<>();
				attackStage.add(rbAttackStages.get(i));
				data1.setRbAttackStages(attackStage);
				if(!data.getLostHosts().isEmpty()){
					data1.setLostHosts(data.getLostHosts());
					data.setLostHosts(new ArrayList<>());
				}
				data1.setCode(data.getCode());
				pushDataToRoom(data1, getRedVsBlue());
				if(i != 2){
					Thread.sleep(5000);
				}
			}
		} else {
			pushDataToRoom(data, getRedVsBlue());

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
			if (nodeIp == null) {
				continue;
			}
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

	public String getAttackStageContent(RbCombatLog rbCombatLog) {
		return String.format("【%s】%s",
				EnumUtils.getEnumDescByCode(rbCombatLog.getEventType(), EventType.class), rbCombatLog.getEventContent());
	}

	public RbCombatRecordsAllDataRsp addAttackDefenseStage(RbCombatRecordsAllDataRsp data, RbCombatLog rbCombatLog) {
		if(!rbCombatLog.getAttackPhase().equals(CrCommonConstants.AttackPhase.OTHER.getCode())){
			RbAttackDefenseStage rbAttackDefenseStage = RbAttackDefenseStage.builder().
					resourceId(rbCombatLog.getResourceId()).group(rbCombatLog.getSourceGroup()).attackPhase(rbCombatLog.getAttackPhase()).content(this.getAttackStageContent(rbCombatLog)).build();
			data.getRbAttackStages().add(rbAttackDefenseStage);
		}
		return data;
	}

	public RbCombatRecordsAllDataRsp addLostHost(RbCombatRecordsAllDataRsp data, RbCombatLog rbCombatLog){
		if(rbCombatLog.getIsLostHost()){
			RbLostHost rbLostHost = RbLostHost.builder().
					resourceId(rbCombatLog.getResourceId()).group(rbCombatLog.getDestGroup()).lostTime(TimeUtil.format1(rbCombatLog.getEventTime())).
					hostName(StringUtils.isBlank(rbCombatLog.getDestName()) ? rbCombatLog.getAgentName() :rbCombatLog.getDestName()).
					hostIp(StringUtils.isBlank(rbCombatLog.getDestIp()) ? rbCombatLog.getAgentIp() : rbCombatLog.getDestIp()).build();
			data.getLostHosts().add(rbLostHost);
		}
		return data;
	}

	public RbCombatRecordsAllDataRsp addAttackDefenseEvent(RbCombatRecordsAllDataRsp data, RbCombatLog rbCombatLog){
		String sourceGroup = CrCommonUtil.getGroupName(rbCombatLog.getSourceGroup());
		String sourceIp = rbCombatLog.getSourceIp();
		String sourceName = rbCombatLog.getSourceName();
		String sourceMachine = getMachineName(sourceGroup, sourceIp, sourceName);
		String destGroup = CrCommonUtil.getGroupName(rbCombatLog.getDestGroup());
		String destIp = rbCombatLog.getDestIp();
		String destName = rbCombatLog.getDestName();
		String destMachine = getMachineName(destGroup, destIp, destName);
		Boolean visible;
		String defenseAction;
		String attackEventDescribe;
		String defenseEventDescribe;
		RbAttackEvent rbAttackEvent = null;
		if (!rbCombatLog.getAttackAction().equals(CrCommonConstants.AttackAction.OTHER.getCode()) ||
				!rbCombatLog.getDefenseAction().equals(CrCommonConstants.DefenseAction.OTHER.getCode())){
			if(rbCombatLog.getEventCategory().equals(CrCommonConstants.EventCategory.ATTACK.getCode()) &&
					!rbCombatLog.getAttackAction().equals(CrCommonConstants.AttackAction.OTHER.getCode())) {
				// TODO 获取attack action对应的AttackActionType
				String attackAction = EnumUtils.getEnumDescByCode(rbCombatLog.getAttackAction(), CrCommonConstants.AttackAction.class);
				defenseAction = CrCommonConstants.DefenseAction.ATTACK_SOURCE_DETECTED.getDesc();
				attackEventDescribe = String.format("%s对%s发动【%s】攻击", sourceMachine, destMachine, attackAction);
				defenseEventDescribe = String.format("%s检测到来自%s的【%s】攻击", sourceMachine, destMachine, attackAction);
				visible = Boolean.FALSE;
				rbAttackEvent = RbAttackEvent.builder().visible(Boolean.TRUE).resourceId(rbCombatLog.getResourceId()).
						group(null).type(CrCommonConstants.EventCategory.ATTACK.getDesc()).groupType(String.format("%s%s",sourceGroup, CrCommonConstants.EventCategory.ATTACK.getDesc()))
						.sourceGroup(sourceGroup).sourceIp(sourceIp).sourceName(sourceName).sourceMachine(sourceMachine)
						.destGroup(destGroup).destIp(destIp).destName(destName).destMachine(destMachine)
						.action(attackAction).eventTime(TimeUtil.format1(rbCombatLog.getEventTime())).eventDescribe(attackEventDescribe).build();
				rbAttackEvent = updateAttackEvent(rbAttackEvent, rbCombatLog);
			} else if (rbCombatLog.getEventCategory().equals(CrCommonConstants.EventCategory.DEFENSE.getCode()) &&
					!rbCombatLog.getDefenseAction().equals(CrCommonConstants.DefenseAction.OTHER.getCode())){
				visible = Boolean.TRUE;
				// TODO 匹配对应的防御行为类型
				defenseAction = EnumUtils.getEnumDescByCode(rbCombatLog.getDefenseAction(), CrCommonConstants.DefenseAction.class);
				defenseEventDescribe = String.format("%s【%s】",destMachine, defenseAction);
			} else {
				return data;
			}
			RbAttackEvent rbDefenseEvent = RbAttackEvent.builder().visible(visible).resourceId(rbCombatLog.getResourceId()).
					group(null).type(CrCommonConstants.EventCategory.DEFENSE.getDesc()).groupType(String.format("%s%s",sourceGroup, EventCategory.DEFENSE.getDesc()))
					.sourceGroup(sourceGroup).sourceIp(sourceIp).sourceName(sourceName).sourceMachine(sourceMachine)
					.destGroup(destGroup).destIp(destIp).destName(destName).destMachine(destMachine)
					.action(defenseAction).eventTime(TimeUtil.format1(rbCombatLog.getEventTime())).eventDescribe(defenseEventDescribe).build();
			rbDefenseEvent = updateDefenseEvent(rbDefenseEvent, rbCombatLog);
			if(null != rbAttackEvent){
				data.getRbAttackDefenseEvents().add(rbAttackEvent);
			}
			if(null != rbDefenseEvent){
				data.getRbAttackDefenseEvents().add(rbDefenseEvent);
			}

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



	public RbCombatRecordsAllDataRsp addData(RbCombatLog combatLog, RbCombatRecordsAllDataRsp combatRecordsAllDataRsp){
		combatRecordsAllDataRsp = addAttackDefenseStage(combatRecordsAllDataRsp, combatLog);
		combatRecordsAllDataRsp = addLostHost(combatRecordsAllDataRsp, combatLog);
		combatRecordsAllDataRsp = addAttackDefenseEvent(combatRecordsAllDataRsp, combatLog);
		return combatRecordsAllDataRsp;
	}

	public <T> void pushDataToRoom(T data, String roomId) {

		RbWsMessageIdentifier identifier = new RbWsMessageIdentifier();
		identifier.setPath(WsPath.RB_COMBAT_LOG);
		identifier.setRoomId(roomId);

		WebSocketMessageContext messageContext = WebSocketMessageContext.builder()
																		.data(JsonUtil.toJsonString(data,
																				PropertyNamingStrategy.SnakeCase))
																		.messageIdentifier(identifier).build();

		myWebSocketHandler.pushMessage(messageContext, true);
	}

	public Boolean isSpringboard() {
		Map<String, String> initData = this.getInitData();
		String pName = initData.get("detail.pname");
		if (StringUtils.isNotBlank(pName)) {
			if (pName.startsWith("frp") || pName.startsWith("ew")) {
				return true;
			}
		}

		String comment = initData.get("detail.backdoor_type");
		if (StringUtils.isNotBlank(comment) && comment.contains("Msf")) {
			return true;
		}
		return false;
	}


}

