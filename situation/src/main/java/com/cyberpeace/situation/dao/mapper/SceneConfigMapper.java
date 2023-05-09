package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.SceneConfig;

/**
 * 场景配置映射器
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Mapper
public interface SceneConfigMapper extends BaseMapper<SceneConfig> {

	/**
	 * 根据场景id查询
	 *
	 * @param sceneId 场景标识
	 * @return {@code SceneConfig}
	 */
	SceneConfig selectBySceneId(String sceneId);

	/**
	 * 根据场景id删除
	 *
	 * @param sceneId 场景标识
	 */
	void deleteBySceneId(String sceneId);

}
