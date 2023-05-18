package com.cyberpeace.rtks.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.mapper.LevelMapper;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.service.LevelService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@Slf4j
public class LevelServiceImpl extends ServiceImpl<LevelMapper, Level> implements LevelService {

	@Autowired
	LevelMapper levelMapper;

	@Autowired
	RoleMapper roleMapper;

	@Override
	public Level getOr404(String levelId) {
		Level level = getInternationalById(levelId);
		if (level == null) {
			log.error("level not exist！levelId=[{}]", levelId);
			throw new BusinessException(RtksBusinessErrorCode.LEVEL_NOT_EXIST);
		}

		ensureNotFrame(level);
		return level;
	}

	private Level getInternationalById(String levelId) {
		Level level = getById(levelId);
		if (level != null && "default".equals(level.getResourceId())
				&& !CommonContext.isChinese()) {
			level.setName(Level.DefaultName.ENGLISH.getDesc());
		}
		return level;
	}

	@Override
	public void removeAllLevels() {
		levelMapper.deleteAllLevels();
	}

	@Override
	public void initDefaultFromPreset() {
		levelMapper.clearAll();
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
			log.error("level Loop is less than zero！levelId=[{}]", level.getResourceId());
			throw new BusinessException(RtksBusinessErrorCode.INVALID_LEVEL);
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
	public Level getLevelOrFrameOr404(String levelId) {
		Level level = getInternationalById(levelId);
		if (level == null) {
			log.error("level not exist！levelId=[{}]", levelId);
			throw new BusinessException(RtksBusinessErrorCode.LEVEL_NOT_EXIST);
		}
		return level;
	}

	@Override
	public String getFullPathById(String levelId) {
		Level level = getInternationalById(levelId);
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
			log.error("parent field of the frame should be empty ,frameId is [{}]",
					frame.getResourceId());
			throw new BusinessException(RtksBusinessErrorCode.INVALID_LEVEL);

		}
		if (!StringUtils.isBlank(frame.getFrame())) {
			log.error("frame field of the frame should be empty ,frameId is [{}]",
					frame.getResourceId());
			throw new BusinessException(RtksBusinessErrorCode.INVALID_LEVEL);
		}
	}

	private void ensureNotFrame(Level level) {
		if (StringUtils.isBlank(level.getParent())) {
			log.error("parent field of the level should not be empty ,levelId is [{}]",
					level.getResourceId());
			throw new BusinessException(RtksBusinessErrorCode.INVALID_LEVEL);
		}
		if (StringUtils.isBlank(level.getFrame())) {
			log.error("frame field of the level should not be empty ,levelId is [{}]",
					level.getResourceId());
			throw new BusinessException(RtksBusinessErrorCode.INVALID_LEVEL);
		}
	}

	@Override
	public boolean checkHasRole(LevelTreeOutput levelTreeChild) {

		if (levelTreeChild.getChildren() == null) {
			List<String> levelIds = Lists.newArrayList();
			levelIds.add(levelTreeChild.getResourceId());
			List<String> roleIds = roleMapper.getRoleIdsByLevelIds(levelIds);
			if (roleIds.size() > 0) {
				return true;
			}
		} else {
			for (LevelTreeOutput element : levelTreeChild.getChildren()) {

				if (element.getChildren() == null) {
					// 处理叶子结点
					List<String> levelIds = Lists.newArrayList();
					levelIds.add(element.getResourceId());
					List<String> roleIds = roleMapper.getRoleIdsByLevelIds(levelIds);
					if (roleIds.size() > 0) {
						return true;
					}
				} else {
					return checkHasRole(element);
				}
			}
		}
		return false;

	}

	@Override
	public void checkFull(){
		List<Level> childLevels = levelMapper.getChildLevels("default");
		String maxSize = ConfigUtil.getContextProperty("rtks.level.size");
		if(childLevels.size() >= Integer.parseInt(maxSize)){
			log.error("The second-floor frame is full of [{}] floors! floors = [{}]", ConfigUtil.getContextProperty("rtks.level.size"), childLevels.size());
			throw new BusinessException(CrBusinessErrorCode.SECOND_FLOOR_FRAME_IS_FULL,maxSize);
		}
	}

}