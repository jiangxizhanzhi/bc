package com.cyberpeace.scene.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.scene.dao.entity.ConnectionEntity;

@Mapper
@Repository
public interface ConnectionMapper extends BaseMapper<ConnectionEntity> {

	ConnectionEntity selectByNodeId(@Param("nodeId") String nodeId);

	List<ConnectionEntity> selectBySceneId(@Param("sceneId") String sceneId);

	void deleteBySceneId(@Param("sceneId") String sceneId);
}
