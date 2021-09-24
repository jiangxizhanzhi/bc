package com.cyberpeace.rtks.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.mapper.LevelMapper;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.service.LevelService;

@Service
public class LevelServiceImpl extends ServiceImpl<LevelMapper, Level> implements LevelService {

	@Autowired
	LevelMapper levelMapper;

	@Autowired
	RoleMapper roleMapper;

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
	public void removeAllLevels() {
		levelMapper.deleteAllLevels();
	}

	@Override
	public void initDefaultFromPreset() {
		levelMapper.truncateLevels();
		levelMapper.copyFromPreset();
	}

	@Override
	public void ensureLessThanTwoLevel(Level level) {
		int maxLoop = 2;
		Consumer<Level> collector = level1 -> {};
		findFrame(level, maxLoop, collector);
	}

	@Override
	public List<Level> getChildLevelsByLevelId(String levelId) {
		return levelMapper.getChildLevels(levelId);
	}

	@Override
	public Level getFrame(Level level) {
		return findFrame(level, level1 -> {});
	}

	private Level findFrame(Level level, Consumer<Level> collector) {
		// 最多层级数(不包括顶层框架)
		int maxLoop = 9;
		return findFrame(level, maxLoop, collector);
	}

	private Level findFrame(Level level, int maxLoop, Consumer<Level> collector) {
		if (maxLoop <= 0) {
			throw new InvalidParamException("层级层数超限");
		}

		collector.accept(level);

		String parentId = level.getParent();
		// 当parent为空时，停止迭代，已经找到框架对象了
		if (StringUtils.isBlank(parentId)) {
			ensureIsFrame(level);
			return level;
		}
		Level parentLevel = getLevelOrFrameOr404(parentId);
		return findFrame(parentLevel, maxLoop - 1, collector);
	}

	@Override
	public Level getLevelOrFrameOr404(String resourceId) {
		Level level = getById(resourceId);
		if (level == null) {
			throw new ResourceNotFoundException("框架/层级" + resourceId + "不存在");
		}
		return level;
	}

	@Override
	public String getFullPathById(String levelId) {
		Level level = getById(levelId);
		if (level == null) {
			return null;
		}
		List<String> levelNames = new ArrayList<>();
		findFrame(level, level1 -> levelNames.add(0, level1.getName()));
		return StringUtils.join(levelNames, "/");
	}

	@Override
	public List<String> getLevelIdsByFrameIds(List<String> frameIds) {
		if (CollectionUtils.isEmpty(frameIds)) {
			return new ArrayList<>();
		}
		return levelMapper.getLevelIdsByFrameIds(frameIds);
	}

	@Override
	public List<Level> getLevelsByIds(List<String> ids) {
		List<Level> levels = listByIds(ids);
		for (Level level : levels) {
			ensureNotFrame(level);
		}
		return levels;
	}

	@Override
	public void changeLevelId(String levelId, String target) {
		levelMapper.changeLevelId(levelId, target);
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

	private void ensureNotFrame(Level level) {
		if (StringUtils.isBlank(level.getParent())) {
			throw new InvalidParamException("脏数据:层级" + level.getResourceId() + "parent字段不应为空");
		}
		if (StringUtils.isBlank(level.getFrame())) {
			throw new InvalidParamException("脏数据:层级" + level.getResourceId() + "frame字段不应为空");
		}
	}
}