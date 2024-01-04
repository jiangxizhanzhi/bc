package com.cyberpeace.scene.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.scene.dao.entity.NetworkEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author :   SN
 * @date :   2023/12/25 10:38
 * @desc :
 **/
@Mapper
public interface NetworkMapper extends BaseMapper<NetworkEntity> {

    List<String> selectBySceneId(@Param("sceneId") String sceneId);

    int deleteByResourceId(@Param("resourceId") String resourceId);
}
