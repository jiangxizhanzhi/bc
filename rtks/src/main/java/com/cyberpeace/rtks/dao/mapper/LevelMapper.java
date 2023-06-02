package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.rtks.dao.entity.Level;

@Mapper
@Repository
public interface LevelMapper extends BaseMapper<Level> {

	IPage<Level> listFrame(Page<Level> page, String keyword);

	List<Level> getChildLevels(String levelId);

	List<Level> getLevelsByFrameId(String frameId);

	List<String> getLevelIdsByFrameIds(List<String> frameIds);

	void changeLevelId(String levelId, String target);

	void deleteAllLevels();

	void clearAll();

	void copyFromPreset();

	/**
	 * 根据level name获取level id
	 *
	 * @param levelName 层级名称
	 * @return {@code String}
	 */
	String getLevelIdByName(@Param("levelName") String levelName);
}
