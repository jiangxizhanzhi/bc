package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants.DefenseAction;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbCombatRecordsAllDataRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
		String groupName = getGroupName(getSourceGroup());
		String action = "ACCEPT".equals(getInitData().get("action"))?"允许" : "禁止";
		return String.format("%s配置策略为：%s%s到%s的网络访问", groupName, action, getSourceIp(), getDestIp());
	}

	@Override
	public RbCombatRecordsAllDataRsp addAttackDefenseEvent(RbCombatRecordsAllDataRsp data, RbCombatLog rbCombatLog) {
		String action = DefenseAction.getEnumByCode(rbCombatLog.getDefenseAction(), DefenseAction.class).getDesc();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("visible", true);
		jsonObject.put("resource_id", rbCombatLog.getResourceId());
		jsonObject.put("group", null);
		jsonObject.put("type", CrCommonConstants.EventCategory.DEFENSE.getCode());
		jsonObject.put("group-type", getGroupName(rbCombatLog.getDestGroup()) + CrCommonConstants.EventCategory.DEFENSE.getDesc());
		jsonObject.put("source_group", rbCombatLog.getSourceGroup());
		jsonObject.put("source_ip", rbCombatLog.getSourceIp());
		jsonObject.put("source_name", rbCombatLog.getSourceName());
		jsonObject.put("dest_group", getGroupName(rbCombatLog.getDestGroup()));
		jsonObject.put("dest_ip", rbCombatLog.getDestIp());
		jsonObject.put("dest_name", rbCombatLog.getDestName());
		jsonObject.put("action", action);
		jsonObject.put("event_time", TimeUtil.format1(rbCombatLog.getEventTime()));
		jsonObject.put("event_describe", rbCombatLog.getEventContent());
		Map<Integer, JSONObject> other = data.getOther();
		other.put(CrCommonConstants.RedVsBlueDockingWebsocket.ATTACK_DEFENSE_EVENT, jsonObject);
		data.setOther(other);
		return data;
	}
}
