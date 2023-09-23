package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户简要数据
 */
@Data
public class UserData implements Serializable {

	@ApiModelProperty(value = "头像")
	private String logo;

	@ApiModelProperty(value = "姓名")
	private String repName;

	@ApiModelProperty(value = "编号")
	private String resourceId;
}
