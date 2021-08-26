package com.cyberpeace.oj.rtks.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.dao.mapper.LevelMapper;
import com.cyberpeace.oj.rtks.service.LevelService;

@Service
public class LevelServiceImpl extends ServiceImpl<LevelMapper, Level> implements LevelService {

	@Autowired
	LevelMapper levelMapper;

	@Override
	public Level getOr404(String resourceId) {
		Level level = getById(resourceId);
		if (level == null) {
			throw new ResourceNotFoundException("层级" + resourceId + "未找到");
		}

		ensureNotFrame(level);
		return level;
	}

	@Override
	public IPage<Level> listEntity(Page<Level> page, String keyword) {
		// 层级的列表查询不被允许
		return null;
	}

	@Override
	public void ensureLevelNoChild(String levelId) {
		// 先确保层级存在
		getOr404(levelId);

		// 检查子层级
		List<Level> childLevels = levelMapper.getChildLevels(levelId);
		if (childLevels.size() > 0) {
			throw new InvalidParamException("层级" + levelId + "有子层级");
		}
	}

	@Override
	public List<Level> getLevelsByFrameId(String frameId) {
		return levelMapper.getLevelsByFrameId(frameId);
	}

	@Override
	public Level getFrame(Level level, int maxLoop) {
		if (maxLoop <= 0) {
			throw new InvalidParamException("层级层数超限");
		}

		String parentId = level.getParent();
		// 当parent为空时，停止迭代，已经找到框架对象了
		if (StringUtils.isBlank(parentId)) {
			ensureIsFrame(level);
			return level;
		}
		Level parentLevel = getLevelOrFrame(parentId);
		return getFrame(parentLevel, maxLoop - 1);
	}

	@Override
	public Level getLevelOrFrame(String resourceId) {
		Level level = getById(resourceId);
		if (level == null) {
			throw new ResourceNotFoundException("框架/层级" + resourceId + "不存在");
		}
		return level;
	}

	/**
	 * 框架的parent和frame均为空
	 */
	private void ensureIsFrame(Level frame) {
		if (!StringUtils.isBlank(frame.getParent())) {
			throw new InvalidParamException("脏数据:框架" + frame.getResourceId() + "parent字段应为空");

		}
		if (!StringUtils.isBlank(frame.getFrame())) {
			throw new InvalidParamException("脏数据:框架" + frame.getResourceId() + "frame字段应为空");
		}
	}

	@Override
	public void ensureNotFrame(Level level) {
		if (StringUtils.isBlank(level.getParent())) {
			throw new InvalidParamException("脏数据:层级" + level.getResourceId() + "parent字段不应为空");
		}
		if (StringUtils.isBlank(level.getFrame())) {
			throw new InvalidParamException("脏数据:层级" + level.getResourceId() + "frame字段不应为空");
		}
	}
}