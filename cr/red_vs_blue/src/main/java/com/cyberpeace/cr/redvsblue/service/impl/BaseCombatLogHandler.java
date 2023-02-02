package com.cyberpeace.cr.redvsblue.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbCombatRecordsAllDataRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbCombatRecordsRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackDefenseStage;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import com.cyberpeace.cr.redvsblue.dao.entity.RbLostHost;
import com.cyberpeace.cr.redvsblue.service.context.NodeAscription;
import com.cyberpeace.cr.redvsblue.service.context.SingleNodeAscription;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.integratation.service.RemoteSceneService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Node;

import java.util.*;


public class BaseCombatLogHandler extends RbCombatLog {

	static Map<String, BaseCombatLogHandler> map = new HashMap<>();

	private Map<String, String> initData;

	public RbCombatLog combatLog;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private CrRoleMapper crRoleMapper;

	@Autowired
	private RemoteSceneService remoteSceneService;

	public static BaseCombatLogHandler get(String key){
		return map.get(key);
	}
	public BaseCombatLogHandler(Map<String, String> initMap) {
		this.initData = initMap;

		if (initMap.isEmpty()) {
			setAttr();
		}
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


		return null;
	}

	public Integer getDestGroup() {
		List<Integer> group = getNodeAscription().getAscription(getDestIp());
		if (group.size() != 1) {
			throw new BusinessException(CrBusinessErrorCode.DEST_GROUP_MISSING);
		} else {
			return group.get(0);
		}
	}

//		public void getDestName(){
//			JSONObject elementInfo = getNodeAscription().get("node_data");
//		}
//
//		public void getSourceName(){
//
//		}

	public String getRedVsBlue(){
		NodeAscription nodeAscription = getNodeAscription();
		return nodeAscription.getRoomId();

	}

	public NodeAscription getNodeAscription(List<String> nodeIpList) {
		NodeAscription nodeAscription = new NodeAscription();
		// 默认同时只会启动一个场景
		LocalScene localScene = localSceneMapper.selectOne();
		if (localScene == null) {
			return nodeAscription;
		}
		List<CrRole> crRoleList = crRoleMapper.getRolesByRoom(localScene.getResourceId(),
				BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
		List<JSONObject> nodeInfoList = remoteSceneService.getAllElements(localScene.getMpSceneId());
		List<SingleNodeAscription> singleNodeAscriptionList = new ArrayList<>();
		Map<String, String> nodeData = new HashMap<>();
		for (String nodeIp : nodeIpList) {
			List<Integer> ascription = new ArrayList<>();
			SingleNodeAscription singleNodeAscription = new SingleNodeAscription(nodeIp, ascription);
			for (JSONObject nodeInfo : nodeInfoList) {
				List<JSONObject> ports = nodeInfo.getObject("ports", List.class);
				for (JSONObject port : ports) {
					if (port.getString("ip")
							.equals(nodeIp.trim())) {

						for (CrRole crRole : crRoleList) {
							List<String> visibleNodes = JsonUtil.toJsonObjectArray(crRole.getNodes(), String.class);
							if (visibleNodes.contains(nodeInfo.getString("id"))) {
								ascription.add(crRole.getRoleGroup());
								nodeData.put(nodeIp, nodeInfo.getString("name"));
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
		if(rbDefenseEvent.getVisible()){
			return rbDefenseEvent;
		}
		return null;
	}

	public RbAttackEvent updateAttackEvent(RbAttackEvent rbAttackEvent, RbCombatLog rbCombatLog) {
		return rbAttackEvent;
	}

	public String getMachineName(String group, String ip, String name) {
		if(StringUtils.isNotBlank(group ) && StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(name)){
			return String.format("%s的%s(%s)", group, name, ip);
		} else if(StringUtils.isNotBlank(group) && StringUtils.isNotBlank(ip)){
			return String.format("%s(%s)", group, ip);
		} else if(StringUtils.isNotBlank(ip)){
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


}

