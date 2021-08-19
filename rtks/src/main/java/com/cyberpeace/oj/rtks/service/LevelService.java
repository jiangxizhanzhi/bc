package com.cyberpeace.oj.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.rtks.dao.entity.Level;

public interface LevelService extends IService<Level> {

	/**
	 * 获取所有的框架对象
	 */
	List<Level> getFrames();

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
	Level getLevelTree(String frameId);
}
