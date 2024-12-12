package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceSourceAnalyzeRuleBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("space_source_analyze_rule")
public class SpaceSourceAnalyzeRule extends SpaceBaseTable {

	@ApiModelProperty(value = "规则列表")
	private String rule;


	public SpaceSourceAnalyzeRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceSourceAnalyzeRuleBo bo = BeanUtils.copy(this, SpaceSourceAnalyzeRuleBo.class);
		return bo;
	}
}
