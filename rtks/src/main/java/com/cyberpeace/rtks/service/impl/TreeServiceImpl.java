package com.cyberpeace.rtks.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.mapper.LevelMapper;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.service.TreeService;

/**
 * 树Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
public class TreeServiceImpl implements TreeService {

	/**
	 * 角色Mapper
	 */
	@Autowired
	private RoleMapper roleMapper;

	/**
	 * 水平Mapper
	 */
	@Autowired
	private LevelMapper levelMapper;

	/**
	 * 获取最近水平由角色
	 *
	 * @param role 角色
	 * @return {@code Level }
	 */
	@Override
	public Level getNearestLevelByRole(String role) {
		Role existRole = roleMapper.selectById(role);
		if (null == existRole) {
			return null;
		}
		Level level = levelMapper.selectById(existRole.getLevelId());
		return level;
	}

	/**
	 * 获取第一水平
	 *
	 * @param frameId 框架id
	 * @return {@code List<Level> }
	 */
	@Override
	public List<Level> getFirstLevel(String frameId) {
		List<Level> levels = levelMapper.getChildLevels(frameId);
		return levels;
	}

	/**
	 * 级别下角色
	 *
	 * @param levelId 水平id
	 * @return {@code List<Role> }
	 */
	@Override
	public List<Role> roleUnderLevel(String levelId) {
		// 遍历当前节点的所有子节点
		List<Level> childLevels = levelMapper.getChildLevels(levelId);

		if (CollectionUtils.isEmpty(childLevels)) {
			// 最后一级节点数据
			return roleMapper.getRolesUnderLevel(levelId);

		} else {
			// 遍历子节点数据
			List<Role> roles = childLevels.stream()
					.map(level -> roleUnderLevel(level.getResourceId())).flatMap(Collection::stream)
					.collect(Collectors.toList());

			return roles;
		}
	}
}
