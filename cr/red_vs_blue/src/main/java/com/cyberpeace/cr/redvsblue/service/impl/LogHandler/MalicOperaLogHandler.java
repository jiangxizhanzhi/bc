package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import com.cyberpeace.cr.redvsblue.dao.entity.RbMaliceProcess;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Data
@Service
public class MalicOperaLogHandler extends CommonCombatLogHandler {

	Integer eventType = CrCommonConstants.EventType.MALIC_OPERA.getCode();

	@Override
	public String getCode() {
		return "malic_opera";
	}

	@Override
	public Integer getSourceGroup() {
		if (getEventCategory().equals(CrCommonConstants.EventCategory.DEFENSE.getCode())) {
			return null;
		}
		return super.getSourceGroup();
	}

	public Boolean isTrojan() {
		String rule = getInitData().get("detail.rule");
		if (rule.contains("清除") && rule.contains("木马")) {
			String cmd = getInitData().get("detail.cmd");
			String roomId = getNodeAscription().getRoomId();
			String machine_id = getInitData().get("agent_id");
			RbMaliceProcess queryset = getRbMaliceProcessMapper().getQueryset(roomId, machine_id, cmd.split(" "));
			if (queryset != null) {
				return true;
			}
		}
		return false;
	}

	public Boolean isFirewallEnable() {
		String rule = getInitData().get("detail.rule");
		if (rule.contains("开启") && rule.contains("防火墙")) {
			return true;
		}
		return false;
	}

	@Override
	public void setAttr() {
		if (isTrojan()) {
			setEventCategory(CrCommonConstants.EventCategory.DEFENSE.getCode());
			setDefenseAction(CrCommonConstants.DefenseAction.REMOVE_TROJAN_HORSES.getCode());
		} else if (isFirewallEnable()) {
			setEventCategory(CrCommonConstants.EventCategory.DEFENSE.getCode());
			setDefenseAction(CrCommonConstants.DefenseAction.HOST_HARDENING.getCode());
		} else if (StringUtils.isNotBlank(getSourceIp())) {
			setEventCategory(CrCommonConstants.EventCategory.ATTACK.getCode());
			setAttackPhase(CrCommonConstants.AttackPhase.EXPLOITATION.getCode());
			setAttackAction(CrCommonConstants.AttackAction.OTHER.getCode());
			setActionType(CrCommonConstants.ActionType.RECONNAISSANCE.getCode());
		}else {
			throw new BusinessException(CrBusinessErrorCode.IGNORED_DATA);
		}
	}

	@Override
	public String getAttackStageContent(RbCombatLog rbCombatLog) {
		return String.format("【远程操作】%s", rbCombatLog.getEventContent());
	}
}
