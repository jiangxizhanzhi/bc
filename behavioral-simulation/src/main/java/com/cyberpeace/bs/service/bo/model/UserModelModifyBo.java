package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户行为模拟修改体
 */
@Data
public class UserModelModifyBo extends UserModelAddBo implements Serializable {

	@ApiModelProperty(value = "用户行为编号")
	private String resourceId;
}
