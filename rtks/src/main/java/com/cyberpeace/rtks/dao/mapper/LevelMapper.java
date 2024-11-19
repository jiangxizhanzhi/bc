package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.rtks.dao.entity.Level;

/**
 * 水平Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface LevelMapper extends BaseMapper<Level> {

	/**
	 * 列表框架
	 *
	 * @param page    分页
	 * @param keyword 关键词
	 * @return {@code IPage<Level> }
	 * @throws
	 * @see Page<Level>
	 * @see String
	 * @see IPage<Level>
	 */
	IPage<Level> listFrame(Page<Level> page, String keyword);

	/**
	 * 获取孩子水平
	 *
	 * @param levelId 水平id
	 * @return {@code List<Level> }
	 * @throws
	 * @see String
	 * @see List<Level>
	 */
	List<Level> getChildLevels(String levelId);

	/**
	 * 获取水平由框架id
	 *
	 * @param frameId 框架id
	 * @return {@code List<Level> }
	 * @throws
	 * @see String
	 * @see List<Level>
	 */
	List<Level> getLevelsByFrameId(String frameId);

	/**
	 * 获取水平id列表由框架id列表
	 *
	 * @param frameIds 框架id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> getLevelIdsByFrameIds(List<String> frameIds);

	/**
	 * 改变水平id
	 *
	 * @param levelId 水平id
	 * @param target  目标
	 * @throws
	 * @see String
	 * @see String
	 */
	void changeLevelId(String levelId, String target);

	/**
	 * 删除全部水平
	 *
	 * @throws
	 */
	void deleteAllLevels();

	/**
	 * 清除全部
	 *
	 * @throws
	 */
	void clearAll();

	/**
	 * 从预设复制
	 *
	 * @throws
	 */
	void copyFromPreset();

	/**
	 * 根据level name获取level id
	 *
	 * @param levelName 层级名称
	 * @return {@code String}
	 * @throws
	 * @see String
	 * @see String
	 */
	String getLevelIdByName(@Param("levelName") String levelName);
}
