package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalDimensionTemplate;
import com.cyberpeace.cr.evaluation.service.context.EvalRuleTemplateCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface EvalDimensionTemplateMapper extends BaseMapper<EvalDimensionTemplate> {

	List<EvalRuleTemplateCount> selectCountByRuleIds(@Param("ruleIds") List<String> ruleIds);

	void deleteByRuleIds(@Param("ruleIds") List<String> ruleIds);

	void deleteByCloudTemplateIds(@Param("cloudTemplateIds") List<String> cloudTemplateIds);

	List<EvalDimensionTemplate> selectByRuleId(@Param("ruleId") String ruleId);

	List<EvalDimensionTemplate> selectByCloudTemplateId(@Param("cloudTemplateId") String cloudTemplateId);

}




