package com.cyberpeace.oj.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.rtks.dao.entity.Level;

public interface LevelService extends IService<Level> {

	/**
	 * 获取所有的框架对象
	 */
	IPage<Level> getFrames(Page<Level> page, String keyword);

	/**
	 * 给定一个层级对象，先校验其parent是否合法，再自动补全frame 再保存
	 */
	void addLevel(Level level);

	/**
	 * 给定一个层级对象，先校验其parent是否合法，再自动补全frame 再更新
	 */
	void modifyLevel(Level level);

	/**
	 * 检查输入的level_id是否合法(存在且没有子层级)
	 */
	void ensureLevelNoChild(String levelId);

	/**
	 * 指定框架id 获取所属所有层级
	 */
	List<Level> getLevelsByFrameId(String frameId);

	/**
	 * 指定框架id 如果不是框架(Frame)，抛出异常，如果是则返回该框架
	 */
	Level getFrameById(String frameId);

	/**
	 * 层级(非框架)的parent和frame均不为空
	 */
	void ensureNotFrame(Level level);
}
