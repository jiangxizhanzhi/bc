package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import lombok.Data;
import org.springframework.stereotype.Service;

// 定义linux web命令执行日志处理类
@Data
@Service
public class WebCommandLogHandler extends PrivilegeEscalationLogHandler {

	Integer eventType = CrCommonConstants.EventType.WEB_COMMAND.getCode();
	Integer actionType = CrCommonConstants.ActionType.COMMAND_CONTROL.getCode();
	Integer attackPattern = CrCommonConstants.AttackPattern.WEB_COMMAND.getCode();

	@Override
	public String getCode() {
		return "web_command";
	}

	@Override
	public String getAttackStageContent(RbCombatLog rbCombatLog) {
		String destMachineName = getMachineName(getGroupName(rbCombatLog.getDestGroup()), rbCombatLog.getDestIp(), rbCombatLog.getDestName());
		return String.format("对%s发起了【%s】", destMachineName, EnumUtils.getEnumDescByCode(rbCombatLog.getEventType(), CrCommonConstants.EventType.class));
	}

}
