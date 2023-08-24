package com.cyberpeace.common.context.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class OperationUserInfo implements Serializable {

	@ApiModelProperty("用户编号")
	private String id;

	@ApiModelProperty("用户姓名")
	private String name;

	@ApiModelProperty("用户名")
	private String username;

	@ApiModelProperty("组织信息")
	private List<List<String>> orgpaths;

}
