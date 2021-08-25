package com.cyberpeace.oj.rtks.service;

import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.dao.entity.Role;

import java.util.List;

public interface TreeService {

	/**
	 * 获取与当前role直接关联的level
	 * @param role
	 * @return
	 */
	Level getNearestLevelByRole(String role);

	/**
	 * 获取指定框架的第一层数据结构
	 * @param frameId
	 * @return
	 */
	List<Level> getFirstLevel(String frameId);

	/**
	 * 获取当前层级下的所有role
	 * @param levelId
	 * @return
	 */
	List<Role> roleUnderLevel(String levelId);
}
