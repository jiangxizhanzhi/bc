package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceActionTarget;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SpaceActionTargetBo extends SpaceFrameBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "所属行动框架")
	private List<IdAndName> frame;

	@ApiModelProperty(value = "创建时间，前端不要传")
	private Date createTime;

	public SpaceActionTarget bo2Entity() {
		SpaceActionTarget entity = BeanUtils.copy(this, SpaceActionTarget.class);
		return entity;
	}

}
