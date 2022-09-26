package com.cyberpeace.cs.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cs.service.context.AuthUser;

@Data
public class ReadAuthInfo implements Serializable {

	@ApiModelProperty("授权访问类型 1=共享 2=自定义")
	private Integer readAuthType;

	@ApiModelProperty("授权访问组织编号")
	private List<String> readAuthIds;

	@ApiModelProperty("授权用户详细信息")
	private List<AuthUser> readAuthUsers;
}
