package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.controller.req.EvalRuleTemplateListReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRuleTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface EvalRuleTemplateMapper extends BaseMapper<EvalRuleTemplate> {

	List<EvalRuleTemplate> selectAllByCloudTemplateId(@Param("cloudTemplateId") String cloudTemplateId);

	List<EvalRuleTemplate> listEvalRuleTemplate(@Param("evalRuleTemplateListReq") EvalRuleTemplateListReq evalRuleTemplateListReq);

	void deleteByCloudTemplateIds(@Param("cloudTemplateIds") List<String> cloudTemplateIds);

}




