package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.TaskStep;

@Mapper
@Repository
public interface TaskStepMapper extends BaseMapper<TaskStep> {

	List<TaskStep> selectStepsByTaskId(@Param("taskId") String taskId);

}
