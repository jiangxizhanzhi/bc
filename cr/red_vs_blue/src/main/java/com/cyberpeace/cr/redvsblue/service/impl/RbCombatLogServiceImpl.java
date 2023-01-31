package com.cyberpeace.cr.redvsblue.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbCombatLogMapper;
import com.cyberpeace.cr.redvsblue.service.RbCombatLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RbCombatLogServiceImpl extends ServiceImpl<RbCombatLogMapper, RbCombatLog> implements RbCombatLogService {

	private List<String> REPEATED_DATA = new ArrayList<>();

	private Integer FLAG_LENGTH = 0;

	@Autowired
	private RbCombatLogMapper rbCombatLogMapper;

	@Override
	public void handlerLog(String syslog) {
		Map<String, String> initData = new HashMap<>();
		if (isZrSyslog(syslog)) {
			initData = getInitDataFromZrLog(syslog);
			if (Arrays.asList("bruteforce_ext", "bruteforce_inter")
					  .contains(initData.get("event_type"))) {
				Map<String, String> bruteforceData = new HashMap<>();
				bruteforceData.put("source_ip", initData.get("detail.src_ip"));
				bruteforceData.put("dest_ip", initData.get("agent_ip"));
				REPEATED_DATA.add(bruteforceData.toString());
				int dataSize = new HashSet<>(REPEATED_DATA).size();
				if (FLAG_LENGTH != dataSize){
					FLAG_LENGTH = dataSize;
				}else {
					throw new BusinessException(CrBusinessErrorCode.LOG_REPEATED);
				}
			}
		} else if (isSbSyslog(syslog)) {
			initData = getInitDataFromSbLog(syslog);
		} else {
			throw new BusinessException(CrBusinessErrorCode.UNRECOGNIZED_DATA_SOURCE);
		}
		if (initData.get("event_type") == null){
			throw new BusinessException(CrBusinessErrorCode.UNRECOGNIZED_DATA_SOURCE);
		}
		System.out.println(initData.toString());
	}

	@Override
	public Boolean isZrSyslog(String syslog) {
		if (syslog.contains("event_type")) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean isSbSyslog(String syslog) {
		if (syslog.contains("{")) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, String> getInitDataFromZrLog(String syslog) {
		List<String> stringList = new ArrayList<>(new HashSet<>(LocalStringUtil.getMatchStr(syslog, "(?=[ ]*)[0-9a-zA-Z_.]+=\"[^ ]*\"(?=[ ]*)")));
		Map<String, String> initMap = new HashMap<>();
		for (String str : stringList) {
			String[] spStr = str.split("=");
			if (spStr.length == 1) {
				System.out.println(spStr);
			} else {
				initMap.put(spStr[0], spStr[1].replace("\"",""));
			}
		}
		initMap.put("original_log", syslog);
		return initMap;
	}

	public Map<String, String> getInitDataFromSbLog(String syslog) {
		Map<String, String> initMap = new HashMap<>();
		List<String> stringList = new ArrayList<>(new HashSet<>(LocalStringUtil.getMatchStr(syslog, "\\{.*\\}")));
		if (stringList.size() == 0){
			return new HashMap<>();
		}else if (stringList.size() ==1){
			String originalLog = stringList.get(0);
			RbCombatLog rbCombatLog = rbCombatLogMapper.selectOneByOriginalLog(originalLog);
			if (rbCombatLog != null){
				throw new BusinessException(CrBusinessErrorCode.LOG_REPEATED);
			}
			JSONObject jsonObject = JSONObject.parseObject(originalLog);
			for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
				initMap.put(entry.getKey(), entry.getValue().toString().replace("\"",""));
			}
			if (initMap.get("srcAddrIp") != null && initMap.get("destAddrIp") != null) {
				initMap.put("event_type", "firewall_policy");
				initMap.put("original_log", syslog);
				return initMap;
			}else {
				return new HashMap<>();
			}
		}else {
			throw new BusinessException(CrBusinessErrorCode.UNRECOGNIZED_DATA_SOURCE);
		}
	}


}
