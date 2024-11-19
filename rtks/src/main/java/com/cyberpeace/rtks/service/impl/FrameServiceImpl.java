package com.cyberpeace.rtks.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.mapper.LevelMapper;
import com.cyberpeace.rtks.service.FrameService;
import com.cyberpeace.rtks.service.LevelService;

/**
 * 框架Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see ServiceImpl
 * @see FrameService
 * @since 1.0.0
 */
@Service
@Slf4j
public class FrameServiceImpl extends ServiceImpl<LevelMapper, Level> implements FrameService {

	/**
	 * 层级Mapper
	 *
	 * @see LevelMapper
	 */
	@Autowired
	LevelMapper levelMapper;

	/**
	 * 层级Service
	 *
	 * @see LevelService
	 */
	@Autowired
	LevelService levelService;

	/**
	 * 列表实体
	 *
	 * @param page    分页
	 * @param keyword 关键词
	 * @return {@code IPage<Level> }
	 * @throws
	 * @see Page<Level>
	 * @see String
	 * @see IPage<Level>
	 */
	@Override
	public IPage<Level> listEntity(Page<Level> page, String keyword) {
		return levelMapper.listFrame(page, keyword);
	}

	/**
	 * 获取树
	 *
	 * @param frameId 框架id
	 * @return {@code LevelTreeOutput }
	 * @throws
	 * @see String
	 * @see LevelTreeOutput
	 */
	@Override
	public LevelTreeOutput getTree(String frameId) {
		// 父级层级id:[子层级对象]
		Map<String, List<LevelTreeOutput>> levelTreeOutputMap = new HashMap<>();
		// 所有层级输出对象
		List<LevelTreeOutput> levelTreeOutputs = new ArrayList<>();

		// 当前框架下所有的层级(不包括框架本身，框架本身的frame字段为null)
		List<Level> levels = levelMapper.getLevelsByFrameId(frameId);
		for (Level level : levels) {
			String parentId = level.getParent();
			if (StringUtils.isBlank(parentId)) {
				continue;
			}
			levelTreeOutputMap.computeIfAbsent(parentId, item -> new ArrayList<>());
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

	/**
	 * 获取层级树输出
	 *
	 * @param level 层级
	 * @return {@code LevelTreeOutput }
	 * @throws
	 * @see Level
	 * @see LevelTreeOutput
	 */
	private LevelTreeOutput getLevelTreeOutput(Level level) {
		LevelTreeOutput output = new LevelTreeOutput();

		output.setResourceId(level.getResourceId());
		output.setName(level.getName());
		output.setDescription(level.getDescription());

		return output;
	}


	/**
	 * 获取OR404
	 *
	 * @param frameId 框架id
	 * @return {@code Level }
	 * @throws
	 * @see String
	 * @see Level
	 */
	@Override
	public Level getOr404(String frameId) {
		Level frame = levelService.getLevelOrFrameOr404(frameId);
		if (frame == null) {
			log.error("frame not exist！frameId=[{}]", frameId);
			throw new BusinessException(RtksBusinessErrorCode.LEVEL_NOT_EXIST);
		}

		ensureIsFrame(frame);

		return frame;
	}

	/**
	 * 给定level对象，确保其为框架，否则报错
	 *
	 * @param level 层级
	 * @throws
	 * @see Level
	 */
	private void ensureIsFrame(Level level) {
		if (StringUtils.isNotBlank(level.getParent())) {
			log.error("level is not valid Frame！levelId=[{}]", level.getResourceId());
			throw new BusinessException(RtksBusinessErrorCode.INVALID_LEVEL);
 		}
	}

	/**
	 * 检查框架id列表
	 *
	 * @param ids id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
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