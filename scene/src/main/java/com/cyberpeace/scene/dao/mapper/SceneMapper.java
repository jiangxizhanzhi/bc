package com.cyberpeace.scene.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author :   SN
 * @date :   2023/12/20 16:57
 * @desc :
 **/
@Mapper
public interface SceneMapper extends BaseMapper<SceneEntity> {

    int selectSceneCount(@Param("sceneStatus") List<Integer> sceneStatus, @Param("userId") String userId);

    int updateSceneStatus(@Param("sceneId") String sceneId, @Param("sceneStatus") Integer sceneStatus);

    SceneEntity selectByResourceId(@Param("resourceId") String resourceId);

    SceneEntity selectByInstanceId(@Param("instanceId") String instanceId, @Param("userId") String userId, @Param("sceneStatus") List<Integer> sceneStatus);

    int updateMeta(@Param("sceneId") String sceneId, @Param("meta") String meta);
}
