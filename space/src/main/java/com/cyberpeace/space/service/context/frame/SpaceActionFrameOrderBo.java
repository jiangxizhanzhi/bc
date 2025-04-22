
package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceActionFrameOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SpaceActionFrameOrderBo {

	@ApiModelProperty("id")
	private String resourceId;

	@ApiModelProperty("框架id")
	private String applicationId;

	@ApiModelProperty("框架名称")
	private String frameName;

	@ApiModelProperty("步骤")
	private String step;

	@ApiModelProperty("战法id")
	private String tacticId;

	@ApiModelProperty("战法类型")
	private Integer tacticType;

	@ApiModelProperty("战法名称")
	private String tacticName;

	@ApiModelProperty("用户id")
	private String userId;

	@ApiModelProperty("用户名称")
	private String userName;

	@ApiModelProperty("下发人")
	private String sender;

	@ApiModelProperty("下发人名称")
	private String senderName;

	@ApiModelProperty("状态，1完成2未完成 ")
	private Integer status;

	@ApiModelProperty("是否下发")
	private Boolean released = false;

	private Date createTime;

	public SpaceActionFrameOrder bo2Entity() {
		SpaceActionFrameOrder entity = BeanUtils.copy(this, SpaceActionFrameOrder.class);
		return entity;
	}

}
