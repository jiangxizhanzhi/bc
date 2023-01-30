package com.cyberpeace.bs.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.dao.entity.UserSimulator;
import com.cyberpeace.bs.service.bo.simulator.SceneIdName;

@Mapper
@Repository
public interface UserSimulatorMapper extends BaseMapper<UserSimulator> {

	List<UserSimulator> selectAll();

	Integer countAll();

	Integer countByStatus(@Param("statusCode") Integer statusCode);

	IPage<UserSimulator> page(@Param("page") Page page, @Param("sceneId") String sceneId);

	List<String> getIdsBySceneId(@Param("sceneId") String sceneId);

	void deleteBySceneId(@Param("sceneId") String sceneId);

	List<SceneIdName> getSceneList();

	List<String> getIdsBySceneIdList(@Param("sceneIdList") List<String> sceneIdList);

	void deleteBySceneIdList(@Param("sceneIdList") List<String> sceneIdList);

	UserSimulator selectByNodeId(@Param("nodeId") String nodeId);
}
