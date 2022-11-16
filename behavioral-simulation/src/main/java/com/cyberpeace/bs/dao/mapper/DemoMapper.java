package com.cyberpeace.bs.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cyberpeace.bs.service.bo.demo.TaskParam;

@Mapper
@Repository
public interface DemoMapper {

	void saveParam(@Param("deviceId") String deviceId, @Param("environmentId") String environmentId,
			@Param("projectId") String projectId);

	TaskParam selectLatest();
}
