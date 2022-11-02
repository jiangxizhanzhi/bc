package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户行为模拟修改体
 */
@Data
public class UserBehaviorModifyBo extends UserBehaviorAddBo implements Serializable {

	@ApiModelProperty(value = "用户行为编号")
	private String resourceId;
}
