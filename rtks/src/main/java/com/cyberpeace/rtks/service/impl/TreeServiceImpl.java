package com.cyberpeace.rtks.service.impl;

import java.util.List;

import com.cyberpeace.rtks.dao.mapper.LevelMapper;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.entity.Role;
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
		Level level = levelMapper.selectById(existRole.getLevelId());
		return level;
	}

	@Override
	public List<Level> getFirstLevel(String frameId) {
		List<Level> levels = levelMapper.getLevelsByFrameId(frameId);
		return levels;
	}

	@Override
	public List<Role> roleUnderLevel(String levelId) {
		// TODO
		// 遍历当前节点的所有子节点
		List<Role> underRoles = roleMapper.getRolesUnderLevel(levelId);
		// 返回当前节点及子节点下所有role数据
		return underRoles;
	}
}
