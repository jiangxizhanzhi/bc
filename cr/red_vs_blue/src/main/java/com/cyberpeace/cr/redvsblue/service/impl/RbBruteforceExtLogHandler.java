package com.cyberpeace.cr.redvsblue.service.impl;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants.EventType;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants.ActionType;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants.AttackAction;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants.AttackPattern;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants.AttackPhase;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

// 外网暴力破解日志处理类
public class RbBruteforceExtLogHandler extends RbCommonCombatLogHandler {


	Integer eventType = EventType.BRUTEFORCE_EXT.getCode();
	Integer attackPhase = AttackPhase.DELIVERY.getCode();
	Integer attackAction = AttackAction.PASSWORD_BRUTE.getCode();
	Integer attackType = ActionType.BOUNDARY_BREAKTHROUGH.getCode();
	Integer attackPattern = AttackPattern.BRUTEFORCE_EXT.getCode();


//	public RbBruteforceExtLogHandler(Map<String, String> initMap) {
//		super(initMap);
//	}

//	public Integer getSourceGroup(){
//		Integer group = super.getSourceGroup();
//		if (null != group){
//			group = CrRole.RbRoleGroup.BLUE.getCode().equals(group) ? CrRole.RbRoleGroup.RED.getCode() :CrRole.RbRoleGroup.BLUE.getCode();
//		}
//		return group;
//
//	}

	public Integer getDestGroup(){
		Integer group = super.getDestGroup();
		if (null != group){
			group = CrRole.RbRoleGroup.BLUE.getCode().equals(group) ? CrRole.RbRoleGroup.RED.getCode() :CrRole.RbRoleGroup.BLUE.getCode();
		}
		return group;

	}


	private RbAttackEvent validateRedVsBlueInfo(RbAttackEvent rbDefenseEvent, RbCombatLog rbCombatLog) {

		String redVsBlue = super.getRedVsBlue();
		if(StringUtils.isBlank(redVsBlue)){
			throw new BusinessException("room not found");
		}
		return null;
	}

	public RbAttackEvent updateAttackEvent(RbAttackEvent rbAttackEvent, RbCombatLog rbCombatLog) {
		if(rbCombatLog.getDestGroup() == rbCombatLog.getSourceGroup()){
			String attackGroup = rbAttackEvent.getDestGroup();
			Integer group = rbCombatLog.getDestGroup() == CrRole.RbRoleGroup.BLUE.getCode() ? CrRole.RbRoleGroup.RED.getCode() : CrRole.RbRoleGroup.BLUE.getCode();
			String groupName = super.getGroupName(group);
			String sourceMachine = super.getMachineName(groupName, rbCombatLog.getSourceIp(), rbCombatLog.getSourceName());
			String destMachine = super.getMachineName(groupName, rbCombatLog.getDestIp(), rbCombatLog.getDestName());
			rbAttackEvent.setEventDescribe(String.format("%s通过%s对%S进行暴力破解", attackGroup, sourceMachine, destMachine));
		}

		return rbAttackEvent;
	}

	public RbAttackEvent updateDefenseEvent(RbAttackEvent defenseEvent, RbCombatLog combatLog){
		if(combatLog.getDestGroup().equals( combatLog.getSourceGroup())){
			return null;
		}
		return defenseEvent;
	}



}
