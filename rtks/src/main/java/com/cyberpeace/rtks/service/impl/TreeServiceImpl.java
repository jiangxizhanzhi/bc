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

@Service
public class TreeServiceImpl implements TreeService {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private LevelMapper levelMapper;

	@Override
	public Level getNearestLevelByRole(String role) {
		Role existRole = roleMapper.selectById(role);
		if (null == existRole) {
			return null;
		}
		return levelMapper.selectById(existRole.getLevelId());
	}

	@Override
	public List<Level> getFirstLevel(String frameId) {
		return levelMapper.getChildLevels(frameId);
	}

	@Override
	public List<Role> roleUnderLevel(String levelId) {
		// 遍历当前节点的所有子节点
		List<Level> childLevels = levelMapper.getChildLevels(levelId);

		if (CollectionUtils.isEmpty(childLevels)) {
			// 最后一级节点数据
			return roleMapper.getRolesUnderLevel(levelId);

		} else {
			// 遍历子节点数据

			return childLevels.stream()
					.map(level -> roleUnderLevel(level.getResourceId())).flatMap(Collection::stream)
					.collect(Collectors.toList());
		}
	}
}
