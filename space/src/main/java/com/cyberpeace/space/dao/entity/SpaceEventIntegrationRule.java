package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceEventIntegrationRuleBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@TableName("space_event_integration_rule")
public class SpaceEventIntegrationRule extends SpaceBaseTable {

	@ApiModelProperty(value = "检测类型")
	private String type;

	@ApiModelProperty(value = "内容简介")
	private String description;

	@ApiModelProperty(value = "内容详情")
	private String content;

	@ApiModelProperty(value = "应用工具")
	private String tool;

	public SpaceEventIntegrationRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceEventIntegrationRuleBo bo = BeanUtils.copy(this, SpaceEventIntegrationRuleBo.class);
		return bo;
	}
}
