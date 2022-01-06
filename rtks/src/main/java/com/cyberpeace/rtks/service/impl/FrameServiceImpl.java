package com.cyberpeace.rtks.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.mapper.LevelMapper;
import com.cyberpeace.rtks.service.FrameService;
import com.cyberpeace.rtks.service.LevelService;

@Service
public class FrameServiceImpl extends ServiceImpl<LevelMapper, Level> implements FrameService {

	@Autowired
	LevelMapper levelMapper;

	@Autowired
	LevelService levelService;
	@Override
	public IPage<Level> listEntity(Page<Level> page, String keyword) {
		return levelMapper.listFrame(page, keyword);
	}

	@Override
	public LevelTreeOutput getTree(String frameId) {
		// {父级层级id:[子层级对象]}
		Map<String, List<LevelTreeOutput>> levelTreeOutputMap = new HashMap<>();
		// 所有层级输出对象
		List<LevelTreeOutput> levelTreeOutputs = new ArrayList<>();

		// 当前框架下所有的层级(不包括框架本身，框架本身的frame字段为null)
		List<Level> levels = levelMapper.getLevelsByFrameId(frameId);
		for (Level level : levels) {
			String parentId = level.getParent();
			assert parentId != null;

			if (!levelTreeOutputMap.containsKey(parentId)) {
				levelTreeOutputMap.put(parentId, new ArrayList<>());
			}

			LevelTreeOutput levelTreeOutput = getLevelTreeOutput(level);
			levelTreeOutputMap.get(parentId).add(levelTreeOutput);
			levelTreeOutputs.add(levelTreeOutput);
		}

		// 加入框架本身
		Level frame = getOr404(frameId);
		LevelTreeOutput frameTree = getLevelTreeOutput(frame);
		levelTreeOutputs.add(frameTree);

		// 遍历框架+层级，赋予children值
		for (LevelTreeOutput levelTreeOutput : levelTreeOutputs) {
			List<LevelTreeOutput> childLevels = levelTreeOutputMap
					.getOrDefault(levelTreeOutput.getResourceId(), null);
			levelTreeOutput.setChildren(childLevels);
		}

		return frameTree;
	}

	private LevelTreeOutput getLevelTreeOutput(Level level) {
		LevelTreeOutput output = new LevelTreeOutput();

		output.setResourceId(level.getResourceId());
		output.setName(level.getName());
		output.setDescription(level.getDescription());

		return output;
	}


	@Override
	public Level getOr404(String frameId) {
		Level frame = levelService.getLevelOrFrameOr404(frameId);
		if (frame == null) {
			throw new ResourceNotFoundException("找不到" + frameId + "对应的框架");
		}

		ensureIsFrame(frame);

		return frame;
	}

	/**
	 * 给定level对象，确保其为框架，否则报错
	 */
	private void ensureIsFrame(Level level) {
		if (StringUtils.isNotBlank(level.getParent())) {
			throw new InvalidParamException(level.getResourceId() + "不是框架而是层级");
		}
	}

	@Override
	public List<String> checkFrameIds(List<String> ids) {
		List<Level> frames = levelMapper.selectBatchIds(ids);

		List<String> ret = new ArrayList<>();
		for (Level frame : frames) {
			ensureIsFrame(frame);
			ret.add(frame.getResourceId());
		}

		return ret;
	}
}