package com.cyberpeace.rtks.service;

import java.util.List;

import com.cyberpeace.rtks.dao.entity.Level;

public interface LevelService extends BaseService<Level> {

	/**
	 * 检查输入的level_id是否合法(存在且没有子层级)
	 */
	void ensureLevelNoChild(String levelId);

	/**
	 * 指定框架id 获取所属所有层级
	 */
	List<Level> getLevelsByFrameId(String frameId);

	/**
	 * 层级(非框架)的parent和frame均不为空
	 */
	void ensureNotFrame(Level level);

	/**
	 * 给定层级(含框架)获取其顶级的框架对象
	 */
	Level getFrame(Level level, int maxLoop);

	/**
	 * 给定id获取框架或层级
	 * */
	Level getLevelOrFrame(String resourceId);
}
