package com.cyberpeace.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.dao.entity.Level;

/**
 * 层级Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see IService
 * @since 1.0.0
 */
public interface LevelService extends IService<Level> {

	/**
	 * 给定层级id获取该层级的子层级列表
	 *
	 * @param levelId 层级id
	 * @return {@code List<Level> }
	 * @throws
	 * @see String
	 * @see List<Level>
	 */
	List<Level> getChildLevelsByLevelId(String levelId);


	/**
	 * 给定层级(含框架)获取其顶级的框架对象
	 *
	 * @param level 层级
	 * @return {@code Level }
	 * @throws
	 * @see Level
	 * @see Level
	 */
	Level getFrame(Level level);

	/**
	 * 给定id获取框架或层级 不存在则报错
	 *
	 * @param resourceId 资源id
	 * @return {@code Level }
	 * @throws
	 * @see String
	 * @see Level
	 */
	Level getLevelOrFrameOr404(String resourceId);

	/**
	 * 给定层级id 返回其完整路径
	 *
	 * @param levelId 层级id
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 */
	String getFullPathById(String levelId);

	/**
	 * 给定框架id列表 返回这些框架所属的层级(不包含框架)列表
	 *
	 * @param frameIds 框架id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> getLevelIdsByFrameIds(List<String> frameIds);

	/**
	 * 给定一个数组，返回对应的层级(非框架)数组，如果里面有框架则报错
	 *
	 * @param ids id列表
	 * @return {@code List<Level> }
	 * @throws
	 * @see List<String>
	 * @see List<Level>
	 */
	List<Level> getLevelsByIds(List<String> ids);

	/**
	 * 修改levelId字段
	 *
	 * @param levelId 原levelId
	 * @param target  新levelId
	 * @throws
	 * @see String
	 * @see String
	 */
	void changeLevelId(String levelId, String target);

	/**
	 * 获取OR404
	 *
	 * @param resourceId 资源id
	 * @return {@code Level }
	 * @throws
	 * @see String
	 * @see Level
	 */
	Level getOr404(String resourceId);

	/**
	 * 删除所有层级 保留框架
	 *
	 * @throws
	 */
	void removeAllLevels();

	/**
	 * 清空level表并导入预置数据
	 *
	 * @throws
	 */
	void initDefaultFromPreset();

	/**
	 * 确保少比二层级
	 *
	 * @param parentLevel 父母层级
	 * @throws
	 * @see Level
	 */
	void ensureLessThanTwoLevel(Level parentLevel);

	/**
	 * 检查具有岗位
	 *
	 * @param levelTreeOutput 层级树输出
	 * @return boolean
	 * @throws
	 * @see LevelTreeOutput
	 * @see boolean
	 */
	boolean checkHasRole(LevelTreeOutput levelTreeOutput);

	/**
	 * 查看完整
	 *
	 * @throws
	 */
	void checkFull();
}
