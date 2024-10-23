
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceAptThreatDetectRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SpaceAptThreatDetectRuleBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;
    

	@ApiModelProperty(value = "情报类型")
	private String type;

	@ApiModelProperty(value = "情报对象")
	private String object;

	@ApiModelProperty(value = "信誉等级")
	private Integer level;


	public SpaceAptThreatDetectRule bo2Entity() {
		SpaceAptThreatDetectRule entity = BeanUtils.copy(this, SpaceAptThreatDetectRule.class);
		return entity;
	}

}
    