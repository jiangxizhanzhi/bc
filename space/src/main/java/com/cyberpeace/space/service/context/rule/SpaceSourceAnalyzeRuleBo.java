
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceSourceAnalyzeRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpaceSourceAnalyzeRuleBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;
    

	@ApiModelProperty(value = "规则列表")
	private String rule;

	public SpaceSourceAnalyzeRule bo2Entity() {
		SpaceSourceAnalyzeRule entity = BeanUtils.copy(this, SpaceSourceAnalyzeRule.class);
		return entity;
	}

}
    