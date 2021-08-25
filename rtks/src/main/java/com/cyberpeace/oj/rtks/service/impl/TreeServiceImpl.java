package com.cyberpeace.oj.rtks.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.dao.entity.Role;
import com.cyberpeace.oj.rtks.service.TreeService;

@Service
public class TreeServiceImpl implements TreeService {

	@Override
	public Level getNearestLevelByRole(String role) {
		// TODO
		return null;
	}

	@Override
	public List<Level> getFirstLevel(String frameId) {
		// TODO
		return null;
	}

	@Override
	public List<Role> roleUnderLevel(String levelId) {
		// TODO
		// 遍历当前节点的所有子节点
		// 返回当前节点及子节点下所有role数据
		return null;
	}
}
