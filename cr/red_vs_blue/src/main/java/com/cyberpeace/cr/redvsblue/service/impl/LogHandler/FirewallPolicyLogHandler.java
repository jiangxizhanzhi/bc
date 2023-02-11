package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants.DefenseAction;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbCombatRecordsAllDataRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

// 定义防火墙策略日志处理类
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
		if (StringUtils.isNotBlank(destAddr)) {
			return destAddr.split("/")[0];
		}
		return null;
	}

	@Override
	public String getSourceIp() {
		String srcAddr = getInitData().get("srcAddrIp");
		if (StringUtils.isNotBlank(srcAddr)) {
			return srcAddr.split("/")[0];
		}
		return null;
	}

	@Override
	public Integer getSourceGroup() {
		Map<String, Integer> groupMap = new HashMap<>();
		groupMap.put("20.20.20.19", CrCommonConstants.PermissionGroup.RED.getCode());
		groupMap.put("192.168.30.18", CrCommonConstants.PermissionGroup.BLUE.getCode());
		return groupMap.get(getInitData().get("utmIp"));
	}

	@Override
	public Integer getDestGroup() {
		return null;
	}

	@Override
	public String getEventTime() {
		return getInitData().get("cTime");
	}

	@Override
	public String getEventContent() {
		String groupName = CrCommonUtil.getGroupName(getSourceGroup());
		String action = "ACCEPT".equals(getInitData().get("action"))?"允许" : "禁止";
		return String.format("%s配置策略为：%s%s到%s的网络访问", groupName, action, getSourceIp(), getDestIp());
	}

	@Override
	public RbCombatRecordsAllDataRsp addAttackDefenseEvent(RbCombatRecordsAllDataRsp data, RbCombatLog rbCombatLog) {
		String action = DefenseAction.getEnumByCode(rbCombatLog.getDefenseAction(), DefenseAction.class).getDesc();
		RbAttackEvent event = RbAttackEvent.builder().visible(true).resourceId(rbCombatLog.getResourceId()).group(null)
				.type(CrCommonConstants.EventCategory.DEFENSE.getDesc()).groupType(CrCommonUtil.getGroupName(rbCombatLog.getDestGroup()) + CrCommonConstants.EventCategory.DEFENSE.getDesc())
				.sourceGroup(String.valueOf(rbCombatLog.getSourceGroup())).sourceIp(rbCombatLog.getSourceIp()).sourceName(rbCombatLog.getSourceName())
				.destName(rbCombatLog.getDestName()).destGroup(String.valueOf(rbCombatLog.getDestGroup())).destIp(rbCombatLog.getDestIp())
				.action(action).eventTime(TimeUtil.format1(rbCombatLog.getEventTime())).eventDescribe(rbCombatLog.getEventContent()).build();
		data.getRbAttackDefenseEvents().add(event);
		return data;
	}
}
