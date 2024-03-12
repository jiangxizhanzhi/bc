package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalActionTemplate;
import com.cyberpeace.cr.evaluation.service.context.EvalRuleTemplateCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface EvalActionTemplateMapper extends BaseMapper<EvalActionTemplate> {

	List<EvalRuleTemplateCount> selectCountByRuleIds(@Param("ruleIds") List<String> ruleIds);

	void deleteByRuleIds(@Param("ruleIds") List<String> ruleIds);

	List<EvalActionTemplate> selectByRuleId(@Param("ruleId") String ruleId);

	void deleteByCloudTemplateIds(@Param("cloudTemplateIds") List<String> cloudTemplateIds);

}




