package com.cyberpeace.rtks.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.rtks.dao.entity.DailyMatchLog;
import com.cyberpeace.rtks.dao.mapper.DailyMatchLogMapper;
import com.cyberpeace.rtks.service.RtksDailyMatchLogService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RtksDailyMatchLogServiceImpl implements RtksDailyMatchLogService {

	@Autowired
	private DailyMatchLogMapper dailyMatchLogMapper;

	@Override
	public void log(String userId, String roleId, Integer match) {
		DailyMatchLog log = new DailyMatchLog();
		log.setUserId(userId);
		log.setRoleId(roleId);
		log.setMatch(match);

		dailyMatchLogMapper.insert(log);
	}
}
