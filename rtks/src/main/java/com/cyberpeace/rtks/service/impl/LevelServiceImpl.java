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

/**
 * 层级Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see ServiceImpl
 * @see LevelService
 * @since 1.0.0
 */
@Service
@Slf4j
public class LevelServiceImpl extends ServiceImpl<LevelMapper, Level> implements LevelService {

	/**
	 * 层级Mapper
	 *
	 * @see LevelMapper
	 */
	@Autowired
	LevelMapper levelMapper;

	/**
	 * 岗位Mapper
	 *
	 * @see RoleMapper
	 */
	@Autowired
	RoleMapper roleMapper;

	/**
	 * 获取OR404
	 *
	 * @param levelId 层级id
	 * @return {@code Level }
	 * @throws
	 * @see String
	 * @see Level
	 */
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

	/**
	 * 获取国际由id
	 *
	 * @param levelId 层级id
	 * @return {@code Level }
	 * @throws
	 * @see String
	 * @see Level
	 */
	private Level getInternationalById(String levelId) {
		Level level = getById(levelId);
		if (level != null && "default".equals(level.getResourceId())
				&& !CommonContext.isChinese()) {
			level.setName(Level.DefaultName.ENGLISH.getDesc());
		}
		return level;
	}

	/**
	 * 删除全部水平
	 *
	 * @throws
	 */
	@Override
	public void removeAllLevels() {
		levelMapper.deleteAllLevels();
	}

	/**
	 * 初始化默认从预设
	 *
	 * @throws
	 */
	@Override
	public void initDefaultFromPreset() {
		levelMapper.clearAll();
		String columns = levelMapper.getTableColumns();
		levelMapper.copyFromPreset(columns);
	}

	/**
	 * 确保少比二层级
	 *
	 * @param level 层级
	 * @throws
	 * @see Level
	 */
	@Override
	public void ensureLessThanTwoLevel(Level level) {
		int maxLoop = 2;
		Consumer<Level> collector = level1 -> {};
		findFrame(level, maxLoop, collector);
	}

	/**
	 * 获取孩子水平由层级id
	 *
	 * @param levelId 层级id
	 * @return {@code List<Level> }
	 * @throws
	 * @see String
	 * @see List<Level>
	 */
	@Override
	public List<Level> getChildLevelsByLevelId(String levelId) {
		return levelMapper.getChildLevels(levelId);
	}

	/**
	 * 获取框架
	 *
	 * @param level 层级
	 * @return {@code Level }
	 * @throws
	 * @see Level
	 * @see Level
	 */
	@Override
	public Level getFrame(Level level) {
		return findFrame(level, level1 -> {});
	}

	/**
	 * 找到框架
	 *
	 * @param level     层级
	 * @param collector 收藏家
	 * @return {@code Level }
	 * @throws
	 * @see Level
	 * @see Consumer<Level>
	 * @see Level
	 */
	private Level findFrame(Level level, Consumer<Level> collector) {
		// 最多层级数(不包括顶层框架)
		int maxLoop = 9;
		return findFrame(level, maxLoop, collector);
	}

	/**
	 * 找到框架
	 *
	 * @param level     层级
	 * @param maxLoop   最大圈
	 * @param collector 收藏家
	 * @return {@code Level }
	 * @throws
	 * @see Level
	 * @see int
	 * @see Consumer<Level>
	 * @see Level
	 */
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

	/**
	 * 获取层级或框架OR404
	 *
	 * @param levelId 层级id
	 * @return {@code Level }
	 * @throws
	 * @see String
	 * @see Level
	 */
	@Override
	public Level getLevelOrFrameOr404(String levelId) {
		Level level = getInternationalById(levelId);
		if (level == null) {
			log.error("level not exist！levelId=[{}]", levelId);
			throw new BusinessException(RtksBusinessErrorCode.LEVEL_NOT_EXIST);
		}
		return level;
	}

	/**
	 * 获取满路径由id
	 *
	 * @param levelId 层级id
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 */
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

	/**
	 * 获取层级id列表由框架id列表
	 *
	 * @param frameIds 框架id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	@Override
	public List<String> getLevelIdsByFrameIds(List<String> frameIds) {
		if (CollectionUtils.isEmpty(frameIds)) {
			return new ArrayList<>();
		}
		return levelMapper.getLevelIdsByFrameIds(frameIds);
	}

	/**
	 * 获取水平由id列表
	 *
	 * @param ids id列表
	 * @return {@code List<Level> }
	 * @throws
	 * @see List<String>
	 * @see List<Level>
	 */
	@Override
	public List<Level> getLevelsByIds(List<String> ids) {
		List<Level> levels = listByIds(ids);
		for (Level level : levels) {
			ensureNotFrame(level);
		}
		return levels;
	}

	/**
	 * 改变层级id
	 *
	 * @param levelId 层级id
	 * @param target  目标
	 * @throws
	 * @see String
	 * @see String
	 */
	@Override
	public void changeLevelId(String levelId, String target) {
		levelMapper.changeLevelId(levelId, target);
	}

	/**
	 * 框架的parent和frame均为空
	 *
	 * @param frame 框架
	 * @throws
	 * @see Level
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

	/**
	 * 确保不框架
	 *
	 * @param level 层级
	 * @throws
	 * @see Level
	 */
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

	/**
	 * 检查具有岗位
	 *
	 * @param levelTreeChild 层级树孩子
	 * @return boolean
	 * @throws
	 * @see LevelTreeOutput
	 * @see boolean
	 */
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

	/**
	 * 查看完整
	 *
	 * @throws
	 */
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