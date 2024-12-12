package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;
import com.cyberpeace.space.model.SpaceFrameBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SpaceActionMethodBo extends SpaceFrameBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "标签")
	private List<String> tag;

	// @ApiModelProperty(value = "所属作战目标")
	// private List<IdAndName> target;
	@ApiModelProperty(value = "创建时间，前端不要传")
	private Date createTime;

	public SpaceActionMethod bo2Entity() {
		SpaceActionMethod entity = BeanUtils.copy(this, SpaceActionMethod.class);
		entity.setTag(JsonUtil.arrayToString(this.tag));
		return entity;
	}

}
