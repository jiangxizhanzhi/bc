package com.cyberpeace.rtks.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		dailyMatchLogMapper.insertLog(userId, roleId, match);
	}
}
