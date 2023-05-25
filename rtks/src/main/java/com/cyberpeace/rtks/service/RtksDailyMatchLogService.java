package com.cyberpeace.rtks.service;

public interface RtksDailyMatchLogService {

	/**
	 * 记录匹配度
	 *
	 * @param userId 用户id
	 * @param roleId 角色id
	 * @param match  匹配
	 */
	void log(String userId, String roleId, Integer match);
}
