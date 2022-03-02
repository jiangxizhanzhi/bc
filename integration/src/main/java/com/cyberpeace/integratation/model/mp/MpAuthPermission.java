package com.cyberpeace.integratation.model.mp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("用户菜单权限")
public class MpAuthPermission implements Serializable {

	private Integer pk;

	private String id;

	private Date createTime;

	private Date updateTime;

	@ApiModelProperty("父级菜单")
	private String parent;

	@ApiModelProperty("菜单标识符")
	private String identifier;

	private String tenant;

	@ApiModelProperty("菜单名称")
	private String name;

	private String path;

	private String type;

	@ApiModelProperty("菜单事发可见")
	private String visible;
}
