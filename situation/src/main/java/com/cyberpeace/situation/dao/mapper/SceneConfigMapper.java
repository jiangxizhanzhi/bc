package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.SceneConfig;

@Mapper
public interface SceneConfigMapper extends BaseMapper<SceneConfig> {

	/**
	 * 获取场景配置
	 * @param sceneId 场景编号
	 * @return 场景配置数据
	 */
	SceneConfig selectBySceneId(String sceneId);

	/**
	 * 根据场景编号删除场景配置
	 * @param sceneId 场景编号
	 */
	void deleteBySceneId(String sceneId);

}
