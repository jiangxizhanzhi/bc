package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalHostTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface EvalHostTemplateMapper extends BaseMapper<EvalHostTemplate> {

	List<EvalHostTemplate> selectByRuleId(@Param("ruleId") String ruleId);

	void deleteByCloudTemplateIds(@Param("cloudTemplateIds") List<String> cloudTemplateIds);

}




