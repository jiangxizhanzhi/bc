package com.cyberpeace.scene.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author :   SN
 * @date :   2023/12/20 16:57
 * @desc :
 **/
@Mapper
public interface SceneMapper extends BaseMapper<SceneEntity> {

    int selectSceneCount(@Param("sceneStatus") Integer sceneStatus, @Param("userId") String userId);
}
