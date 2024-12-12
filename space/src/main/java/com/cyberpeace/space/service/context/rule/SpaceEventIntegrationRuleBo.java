
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceEventIntegrationRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpaceEventIntegrationRuleBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;
    
    @ApiModelProperty(value = "统计周期")
	private Integer cycle;
    @ApiModelProperty(value = "告警阈值")
	private Integer threshold;

	public SpaceEventIntegrationRule bo2Entity() {
		SpaceEventIntegrationRule entity = BeanUtils.copy(this, SpaceEventIntegrationRule.class);
		return entity;
	}

}
    