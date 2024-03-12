package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalStepTemplate;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskTemplateContext;
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

	List<EvalTaskTemplateContext> selectIdNameByCloudTemplateId(@Param("cloudTemplateId") String cloudTemplateId);

}




