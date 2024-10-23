
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceTrafficExtractionRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpaceTrafficExtractionRuleBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;
    

	@ApiModelProperty(value = "顺序")
	private String order;

	@ApiModelProperty(value = "文件名")
	private String filename;

	@ApiModelProperty(value = "路径")
	private String route;


	public SpaceTrafficExtractionRule bo2Entity() {
		SpaceTrafficExtractionRule entity = BeanUtils.copy(this, SpaceTrafficExtractionRule.class);
		return entity;
	}

}
    