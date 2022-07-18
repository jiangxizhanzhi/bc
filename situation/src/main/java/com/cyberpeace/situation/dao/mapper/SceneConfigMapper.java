package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.SceneConfig;

@Mapper
public interface SceneConfigMapper extends BaseMapper<SceneConfig> {

	SceneConfig selectBySceneId(String sceneId);

	void deleteBySceneId(String sceneId);

	void clearCodeBySceneId(String sceneId);
}
