package com.cyberpeace.oj.rtks.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.exception.InvalidParamException;
import com.cyberpeace.oj.common.exception.ResourceNotFoundException;
import com.cyberpeace.oj.rtks.controller.res.LevelTreeOutput;
import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.dao.mapper.LevelMapper;
import com.cyberpeace.oj.rtks.service.LevelService;

@Service
public class LevelServiceImpl extends ServiceImpl<LevelMapper, Level> implements LevelService {

	@Override
	public IPage<Level> getFrames(Page<Level> page, String keyword) {
		return this.getBaseMapper().listFrame(page, keyword);
	}

	@Override
	public void addLevel(Level level) {
		beforeSaveLevel(level);

		save(level);
	}

	@Override
	public void modifyLevel(Level level) {
		beforeSaveLevel(level);

		updateById(level);
	}

	@Override
	public LevelTreeOutput getLevelTree(String frameId) {
		// {父级层级id:[子层级对象]}
		Map<String, List<LevelTreeOutput>> levelTreeOutputMap = new HashMap<>();
		// 所有层级输出对象
		List<LevelTreeOutput> levelTreeOutputs = new ArrayList<>();

		// 当前框架下所有的层级(不包括框架本身)
		List<Level> levels = getLevelsByFrameId(frameId);
		for (Level level : levels) {
			String parentId = level.getParent();
			ensureNotFrame(level);

			if (!levelTreeOutputMap.containsKey(parentId)) {
				levelTreeOutputMap.put(parentId, new ArrayList<>());
			}

			LevelTreeOutput levelTreeOutput = new LevelTreeOutput(level);
			levelTreeOutputMap.get(parentId).add(levelTreeOutput);
			levelTreeOutputs.add(levelTreeOutput);
		}

		// 加入框架本身
		Level frame = getFrameById(frameId);
		LevelTreeOutput frameTree = new LevelTreeOutput(frame);
		levelTreeOutputs.add(frameTree);

		// 遍历框架+层级，赋予children值
		for (LevelTreeOutput levelTreeOutput : levelTreeOutputs) {
			List<LevelTreeOutput> childLevels = levelTreeOutputMap
					.getOrDefault(levelTreeOutput.getResourceId(), null);
			levelTreeOutput.setChildren(childLevels);
		}

		return frameTree;
	}

	@Override
	public void ensureLevelIdValid(String levelId) {
		Level level = getById(levelId);
		if (level == null) {
			throw new ResourceNotFoundException("层级" + levelId + "未找到");
		}
	}

	/**
	 * 指定框架id 获取所属所有层级
	 */
	private List<Level> getLevelsByFrameId(String frameId) {
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("frame", frameId);
		return listByMap(columnMap);
	}

	/**
	 * 指定框架id 如果不是框架(Frame)，抛出异常，如果是则返回该框架
	 */
	private Level getFrameById(String frameId) {
		Level level = getById(frameId);
		if (level == null) {
			throw new ResourceNotFoundException("找不到" + frameId + "对应的框架");
		}

		if (level.getParent() != null) {
			// level有父级，不是Frame
			throw new InvalidParamException(frameId + "不是框架id");
		}

		return level;
	}

	/**
	 * 给定层级(非框架)，设定其frame字段
	 */
	private void beforeSaveLevel(Level level) {
		// 因为level是层级而不是框架，所以首先要校验其parent不为空
		Level parentLevel = getParent(level);

		// 迭代获取当前层级所属框架
		int maxLoop = 10;
		level.setFrame(getFrame(parentLevel, maxLoop - 1).getResourceId());
	}

	/**
	 * 获取给定层级(非框架)的父级，如果父级不存在则报错
	 */
	private Level getParent(Level level) {
		String parentId = level.getParent();
		if (StringUtils.isBlank(parentId)) {
			throw new InvalidParamException("层级" + level.getResourceId() + "的parent为空");
		}

		Level parentLevel = getById(parentId);
		if (parentLevel == null) {
			throw new ResourceNotFoundException("框架/层级" + parentId + "不存在");
		}
		return parentLevel;
	}

	/**
	 * 给定层级(含框架)获取其顶级的框架对象
	 */
	private Level getFrame(Level level, int maxLoop) {
		if (maxLoop <= 0) {
			throw new InvalidParamException("层级层数超限");
		}

		// 当parent为空时，停止迭代，已经找到框架对象了
		if (StringUtils.isBlank(level.getParent())) {
			ensureIsFrame(level);
			return level;
		}
		Level parentLevel = getParent(level);
		return getFrame(parentLevel, maxLoop - 1);
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

	/**
	 * 层级(非框架)的parent和frame均不为空
	 */
	private void ensureNotFrame(Level level) {
		if (StringUtils.isBlank(level.getParent())) {
			throw new InvalidParamException("脏数据:层级" + level.getResourceId() + "parent字段不应为空");
		}
		if (StringUtils.isBlank(level.getFrame())) {
			throw new InvalidParamException("脏数据:层级" + level.getResourceId() + "frame字段不应为空");
		}
	}
}