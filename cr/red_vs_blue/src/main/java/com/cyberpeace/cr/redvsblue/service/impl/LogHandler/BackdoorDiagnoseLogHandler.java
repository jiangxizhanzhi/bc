package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import com.cyberpeace.cr.redvsblue.dao.entity.RbMaliceProcess;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbMaliceProcessMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//	定义Linux后门检测日志处理类
@Data
@Service
public class BackdoorDiagnoseLogHandler extends AttackCombatLogHandler {

	Integer eventType = CrCommonConstants.EventType.BACKDOOR_DIAGNOSE.getCode();

	@Autowired
	private RbMaliceProcessMapper rbMaliceProcessMapper;

	@Override
	public String getCode() {
		return "backdoor_diagnose";
	}

	public void setSpringboardAttr() {
		setAttackPhase(CrCommonConstants.AttackPhase.EXPLOITATION.getCode());
		setAttackAction(CrCommonConstants.AttackAction.SPRINGBOARD.getCode());
		setActionType(CrCommonConstants.ActionType.BOUNDARY_BREAKTHROUGH.getCode());
		setIsLostHostSign(true);
	}

	public void setDeliveryAttr() {
		setAttackPhase(CrCommonConstants.AttackPhase.DELIVERY.getCode());
		setAttackAction(CrCommonConstants.AttackAction.DELIVERY.getCode());
		setActionType(CrCommonConstants.ActionType.OTHER.getCode());
		setIsLostHostSign(false);
	}

	@Override
	public void setAttr() {
		if (isSpringboard()) {
			setSpringboardAttr();
		}else {
			setDeliveryAttr();
		}
	}

	@Override
	public String getEventContent() {
		if (CrCommonConstants.AttackAction.SPRINGBOARD.getCode().equals(getAttackAction())) {
			return String.format("在%s上建立跳板", getDestName());
		}else if (getAttackAction().equals(CrCommonConstants.AttackAction.DELIVERY.getCode())) {
			return String.format("对%s进行文件投递", getDestName());
		}
		return getInitData().get("event_content");
	}

	@Override
	public String getAttackStageContent(RbCombatLog rbCombatLog) {
		if (CrCommonConstants.ActionType.DEFENSIVE_CONFRONTATION.getCode().equals(rbCombatLog.getActionType())) {
			return String.format("【文件投递】%s", rbCombatLog.getEventContent());
		}
		return rbCombatLog.getEventContent();
	}

	@Override
	public RbAttackEvent updateAttackEvent(RbAttackEvent rbAttackEvent, RbCombatLog rbCombatLog) {
		String sourceGroup = rbAttackEvent.getSourceGroup();
		String destMachine = rbAttackEvent.getDestMachine();
		String action = rbAttackEvent.getAction();
		if (CrCommonConstants.AttackAction.SPRINGBOARD.getCode().equals(rbCombatLog.getAttackAction())) {
			rbAttackEvent.setEventDescribe(String.format("%s在%s上【建立跳板，实现端口转发】", sourceGroup, destMachine));
		}else {
			rbAttackEvent.setEventDescribe(String.format("%s对%s进行了【%s】", sourceGroup, destMachine, action));
		}
		return rbAttackEvent;
	}

	@Override
	public void save() {
		super.save();
		saveTrojanProcess();
	}

	public void saveTrojanProcess() {
		String redVsBlue = getRedVsBlue();
		String machineId = getInitData().get("agent_id");
		String process = getInitData().get("detail.pid");
		if (StringUtils.isNotBlank(process)) {
			RbMaliceProcess rbMaliceProcess = new RbMaliceProcess(redVsBlue, machineId, process);
			rbMaliceProcessMapper.insert(rbMaliceProcess);
		}
	}
}
