package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalStepGuideDetailRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalStepGuideListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalStep;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EvalStepMapper extends BaseMapper<EvalStep> {

	List<EvalStepGuideListRsp> selectStepGuideListByStepIds(@Param("stepIds") List<String> stepIds);

	EvalStepGuideDetailRsp selectGuideDetailByStepId(@Param("stepId") String stepId);

}