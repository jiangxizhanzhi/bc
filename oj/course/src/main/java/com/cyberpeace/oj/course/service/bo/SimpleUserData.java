package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class SimpleUserData implements Serializable {

	@ApiModelProperty("用户编号")
	private String userId;

	@ApiModelProperty("用户名称")
	private String userName;

	@ApiModelProperty("用户昵称")
	private String name;

	@ApiModelProperty("组织编号")
	private String organizationId;

}
