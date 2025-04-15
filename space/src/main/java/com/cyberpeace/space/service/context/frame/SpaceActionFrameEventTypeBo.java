
package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceActionFrameEventType;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpaceActionFrameEventTypeBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	public SpaceActionFrameEventType bo2Entity() {
		SpaceActionFrameEventType entity = BeanUtils.copy(this, SpaceActionFrameEventType.class);
		return entity;
	}

}
    