package com.cyberpeace.bs.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.bs.dao.entity.UserSimulator;

@Mapper
@Repository
public interface UserSimulatorMapper extends BaseMapper<UserSimulator> {

	List<UserSimulator> selectAll();

	Integer countAll();

	Integer countByStatus(@Param("statusCode") Integer statusCode);

	IPage<UserSimulator> page(@Param("page") Page page);

    List<String> getIdsBySceneId(@Param("sceneId") String sceneId);

	void deleteBySceneId(@Param("sceneId") String sceneId);
}
