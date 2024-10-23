
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SpaceLoadedFileThreatRuleBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;
    

	@ApiModelProperty(value = "规则内容")
	private String content;

	@ApiModelProperty(value = "规则描述")
	private String description;

	public SpaceLoadedFileThreatRule bo2Entity() {
		SpaceLoadedFileThreatRule entity = BeanUtils.copy(this, SpaceLoadedFileThreatRule.class);
		return entity;
	}

}
    