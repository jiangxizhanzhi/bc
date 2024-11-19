package com.cyberpeace.rtks.service;

import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.entity.Role;

import java.util.List;

/**
 * 树Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public interface TreeService {

	/**
	 * 获取与当前role直接关联的level
	 *
	 * @param role
	 * @return
	 * @throws
	 * @see String
	 * @see Level
	 */
	Level getNearestLevelByRole(String role);

	/**
	 * 获取指定框架的第一层数据结构
	 *
	 * @param frameId
	 * @return
	 * @throws
	 * @see String
	 * @see List<Level>
	 */
	List<Level> getFirstLevel(String frameId);

	/**
	 * 获取当前层级下的所有role
	 *
	 * @param levelId
	 * @return
	 * @throws
	 * @see String
	 * @see List<Role>
	 */
	List<Role> roleUnderLevel(String levelId);
}
