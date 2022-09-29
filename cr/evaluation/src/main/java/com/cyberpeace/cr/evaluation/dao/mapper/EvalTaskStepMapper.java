package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTaskStep;

@Mapper
@Repository
public interface EvalTaskStepMapper extends BaseMapper<EvalTaskStep> {

	List<String> selectStepIdsByTaskId(@Param("taskId") String taskId);

	List<EvalTaskStep> selectSteps(String taskId, String userId);

	Boolean isNotFinished(String taskId, Integer stage);

}
