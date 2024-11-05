package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceEventIntegrationRuleBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("space_event_integration_rule")
public class SpaceEventIntegrationRule extends SpaceBaseTable {
    @ApiModelProperty(value = "统计周期")
	private Integer cycle;
    @ApiModelProperty(value = "告警阈值")
	private Integer threshold;


	public SpaceEventIntegrationRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceEventIntegrationRuleBo bo = BeanUtils.copy(this, SpaceEventIntegrationRuleBo.class);
		return bo;
	}
}
