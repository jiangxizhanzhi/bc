package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalStepRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalStepTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface EvalStepTemplateMapper extends BaseMapper<EvalStepTemplate> {

	void deleteByCloudTemplateIds(@Param("cloudTemplateIds") List<String> cloudTemplateIds);

	List<EvalStepTemplate> selectStepByCloudTemplateId(@Param("cloudTemplateId") String cloudTemplateId);

	String selectIdListByCloudTemplateId(@Param("cloudTemplateId") String cloudTemplateId);

	Integer select1ByCloudTemplateId(@Param("cloudTemplateId") String cloudTemplateId);

	List<EvalStepRsp> selectRspByIds(@Param("ids") List<String> ids);

}




