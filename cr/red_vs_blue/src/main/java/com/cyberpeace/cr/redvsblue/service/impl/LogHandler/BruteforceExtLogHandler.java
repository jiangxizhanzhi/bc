package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants.*;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbCombatRecordsAllDataRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackDefenseStage;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;

import lombok.Data;

// 外网暴力破解日志处理类
@Data
@Service
public class BruteforceExtLogHandler extends AttackCombatLogHandler {


	Integer eventType = EventType.BRUTEFORCE_EXT.getCode();
	Integer attackPhase = AttackPhase.DELIVERY.getCode();
	Integer attackAction = AttackAction.PASSWORD_BRUTE.getCode();
	Integer actionType = ActionType.BOUNDARY_BREAKTHROUGH.getCode();
	Integer attackPattern = AttackPattern.BRUTEFORCE_EXT.getCode();

	@Override
	public String getCode() {
		return "bruteforce_ext";
	}

	@Override
	public Integer getSourceGroup() {
		Integer group = super.getSourceGroup();
		if (null != group){
			group = CrRole.RbRoleGroup.RED.getCode().equals(group) ? CrRole.RbRoleGroup.RED.getCode() :CrRole.RbRoleGroup.BLUE.getCode();
		}
		return group;
	}

	@Override
	public Integer getDestGroup() {
		Integer group = super.getDestGroup();
		if (null != group){
			group = CrRole.RbRoleGroup.RED.getCode().equals(group) ? CrRole.RbRoleGroup.RED.getCode() :CrRole.RbRoleGroup.BLUE.getCode();
		}
		return group;
	}

	@Override
	public void validateRbInfo() {
		if(StringUtils.isBlank(getRedVsBlue())){
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_FOUND);
		}
	}

	@Override
	public RbCombatRecordsAllDataRsp addAttackDefenseStage(RbCombatRecordsAllDataRsp data,
			RbCombatLog rbCombatLog) {
		if (!rbCombatLog.getAttackPhase().equals(CrCommonConstants.AttackPhase.OTHER.getCode())) {

			Integer group = rbCombatLog.getSourceGroup();
			// 判断攻击方和防守方是否同组，如果同组，防守方为对方组
			if (rbCombatLog.getDestGroup().equals(rbCombatLog.getSourceGroup())) {
				group = rbCombatLog.getDestGroup().equals(CrRole.RbRoleGroup.BLUE.getCode())
						? CrRole.RbRoleGroup.RED.getCode()
						: CrRole.RbRoleGroup.BLUE.getCode();
			}

			RbAttackDefenseStage rbAttackDefenseStage = RbAttackDefenseStage.builder()
					.resourceId(rbCombatLog.getResourceId()).group(group)
					.attackPhase(rbCombatLog.getAttackPhase())
					.content(this.getAttackStageContent(rbCombatLog)).build();
			data.getRbAttackStages().add(rbAttackDefenseStage);
		}
		return data;
	}

	@Override
	public RbAttackEvent updateAttackEvent(RbAttackEvent rbAttackEvent, RbCombatLog rbCombatLog) {
		if(rbCombatLog.getDestGroup()
					  .equals(rbCombatLog.getSourceGroup())){
			String attackGroup = rbAttackEvent.getDestGroup();
			Integer group = rbCombatLog.getDestGroup().equals(CrRole.RbRoleGroup.BLUE.getCode()) ? CrRole.RbRoleGroup.RED.getCode() : CrRole.RbRoleGroup.BLUE.getCode();
			String groupName = CrCommonUtil.getGroupName(group);
			String sourceMachine = super.getMachineName(attackGroup, rbCombatLog.getSourceIp(), rbCombatLog.getSourceName());
			String destMachine = super.getMachineName(attackGroup, rbCombatLog.getDestIp(), rbCombatLog.getDestName());
			rbAttackEvent.setEventDescribe(String.format("%s通过%s对%S进行暴力破解", groupName, sourceMachine, destMachine));
			rbAttackEvent.setGroupType(String.format("%s%s",groupName, CrCommonConstants.EventCategory.ATTACK.getDesc()));
		}
		return rbAttackEvent;
	}

	@Override
	public RbAttackEvent updateDefenseEvent(RbAttackEvent defenseEvent, RbCombatLog combatLog) {
		if(combatLog.getDestGroup().equals( combatLog.getSourceGroup())){
			return null;
		}
		return defenseEvent;
	}

}
