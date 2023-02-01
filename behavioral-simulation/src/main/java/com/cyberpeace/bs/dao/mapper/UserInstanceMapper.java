package com.cyberpeace.bs.dao.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.cyberpeace.bs.dao.entity.UserInstance;

@Mapper
@Repository
public interface UserInstanceMapper {

	void insertUserInstanceBatch(@Param("userInstanceAll") List<UserInstance> userInstanceAll);

	List<UserInstance> getBatchBySimulatorId(@Param("simulatorId") String simulatorId);

	void deleteBySimulatorIds(@Param("simulatorIds") Collection<String> simulatorIds);

	void deleteBySimulatorId(@Param("simulatorId") String simulatorId);

	List<UserInstance>
			getBatchBySimulatorIds(@Param("simulatorIds") Collection<String> simulatorIds);
}
