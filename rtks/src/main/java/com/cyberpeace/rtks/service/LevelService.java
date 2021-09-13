package com.cyberpeace.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.rtks.dao.entity.Level;

public interface LevelService extends IService<Level> {

	/**
	 * 给定层级id获取该层级的子层级列表
	 */
	List<Level> getChildLevelsByLevelId(String levelId);

	/**
	 * 指定框架id 获取所属所有层级(不包括框架本身)
	 */
	List<Level> getLevelsByFrameId(String frameId);

	/**
	 * 给定层级(含框架)获取其顶级的框架对象
	 */
	Level getFrame(Level level);

	/**
	 * 给定id获取框架或层级 不存在则报错
	 * */
	Level getLevelOrFrameOr404(String resourceId);

	/**
	 * 给定层级id 返回其完整路径
	 * */
	String getFullPathById(String levelId);

	/**
	 * 给定框架id列表 返回这些框架所属的层级(不包含框架)列表
	 * */
	List<String> getLevelIdsByFrameIds(List<String> frameIds);

	/**
	 * 给定一个数组，返回对应的层级(非框架)数组，如果里面有框架则报错
	 * */
	List<Level> getLevelsByIds(List<String> ids);

	/**
	 * 修改levelId字段
	 * @param levelId 原levelId
	 * @param target 新levelId
	 * */
	void changeLevelId(String levelId, String target);

	Level getOr404(String resourceId);

	/**
	 * 删除所有层级 保留框架
	 * */
	void removeAllLevels();

	/**
	 * 清空level表并导入预置数据
	 * */
	void initDefaultFromPreset();
}
