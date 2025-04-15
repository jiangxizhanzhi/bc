
package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceActionFrameTargetType;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpaceActionFrameTargetTypeBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	public SpaceActionFrameTargetType bo2Entity() {
		SpaceActionFrameTargetType entity = BeanUtils.copy(this, SpaceActionFrameTargetType.class);
		return entity;
	}

}
    