package com.cyberpeace.oj.rtks.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.rtks.controller.res.LevelTreeOutput;
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
	 * 给定框架id, 返回树状层级结构
	 */
	LevelTreeOutput getLevelTree(String frameId);

	/**
	 * 检查输入的level_id是否合法
	 */
	void ensureLevelIdValid(String levelId);
}
