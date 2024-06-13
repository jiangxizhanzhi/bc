package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalGuideListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalGuideScheme;
import com.cyberpeace.cr.evaluation.service.context.EvalGuideSceneContext;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EvalGuideMapper extends BaseMapper<EvalGuideScheme> {

	List<EvalGuideListRsp> selectListByInstanceId(@Param("instanceId") String instanceId);

	String selectTestIdByGuideId(@Param("guideId") String guideId);

	String selectSceneIdByGuideId(@Param("guideId") String guideId);

	void deleteByStepId(@Param("stepId") String stepId);

	List<EvalGuideSceneContext> batchSelectSceneIdByGuideId(@Param("guideIds") List<String> guideIds);
}