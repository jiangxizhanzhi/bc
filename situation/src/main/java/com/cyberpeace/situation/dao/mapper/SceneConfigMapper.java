package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.SceneConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SceneConfigMapper extends BaseMapper<SceneConfig> {

    SceneConfig selectBySceneId(String sceneId);

    void deleteBySceneId(String sceneId);
}
