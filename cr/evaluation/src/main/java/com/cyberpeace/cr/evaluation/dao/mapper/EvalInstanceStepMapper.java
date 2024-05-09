package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalInstanceStep;
import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EvalInstanceStepMapper extends BaseMapper<EvalInstanceStep> {

	List<EvalInstanceStep> selectByInstanceId(@Param("instanceId") String instanceId);

	EvalInstanceStep selectByStepId(@Param("stepId") String stepId);

	void deleteByInstanceId(@Param("instanceId") String instanceId);

	List<String> selectRunningGuideIds();

	List<EvalTimeLineContext> selectTimeNodes(@Param("instanceId") String instanceId);

	List<CodeNameRsp> getAllStages(@Param("instanceId") String instanceId);

	Integer selectAllStepCount(@Param("instanceId") String instanceId);

}