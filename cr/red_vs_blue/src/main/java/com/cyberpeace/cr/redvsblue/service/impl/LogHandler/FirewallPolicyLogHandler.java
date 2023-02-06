package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants.DefenseAction;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackDefenseEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Data
@Service
public class FirewallPolicyLogHandler extends DefenseCombatLogHandler {

	Integer defenseAction = CrCommonConstants.DefenseAction.CONFIGURE_FIREWALL_POLICY.getCode();
	Integer eventType = CrCommonConstants.EventType.FIREWALL_POLICY.getCode();

	@Override
	public String getCode() {
		return "firewall_policy";
	}

	@Override
    public String getDestIp() {
		String destAddr = getInitData().get("destAddrIp");
		if (StringUtils.isBlank(destAddr)) {
			return destAddr.split("/")[0];
		}
		return null;
	}

	@Override
	public String getSourceIp() {
		String srcAddr = getInitData().get("srcAddrIp");
		if (StringUtils.isBlank(srcAddr)) {
			return srcAddr.split("/")[0];
		}
		return null;
	}

	@Override
	public Integer getSourceGroup() {
		// todo 123
		return null;
	}

	@Override
	public Integer getDestGroup() {
		return null;
	}

	@Override
	public Date getEventTime() {
		// todo 123
		getInitData().get("cTime");
		return null;
	}

	@Override
	public String getEventContent() {
		String groupName = getGroupName(getSourceGroup());
		String action = "ACCEPT".equals(getInitData().get("action"))?"允许" : "禁止";
		return String.format("%s配置策略为：%s%s到%s的网络访问", groupName, action, getSourceIp(), getDestIp());
	}

	public RbAttackDefenseEvent addAttackDefenseEvent(RbAttackDefenseEvent data) {
		RbCombatLog rbCombatLog = getInstance();
		String action = DefenseAction.getEnumByCode(rbCombatLog.getDefenseAction(), DefenseAction.class).getDesc();
		// todo 123
		return null;
	}
}
