package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.SceneConfig;

/**
 * 场景配置Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface SceneConfigMapper extends BaseMapper<SceneConfig> {

	/**
	 * 查询由场景id
	 *
	 * @param sceneId 场景id
	 * @return {@code SceneConfig }
	 * @throws
	 * @see String
	 * @see SceneConfig
	 */
	SceneConfig selectBySceneId(String sceneId);

	/**
	 * 删除由场景id
	 *
	 * @param sceneId 场景id
	 * @throws
	 * @see String
	 */
	void deleteBySceneId(String sceneId);

}
