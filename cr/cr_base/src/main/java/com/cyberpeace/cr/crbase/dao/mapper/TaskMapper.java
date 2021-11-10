package com.cyberpeace.cr.crbase.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.Task;

@Mapper
@Repository
public interface TaskMapper extends BaseMapper<Task> {

	Task selectByMissionIdAndUserId(@Param("mission_id") String missionId,
			@Param("user_id") String userId);
}
