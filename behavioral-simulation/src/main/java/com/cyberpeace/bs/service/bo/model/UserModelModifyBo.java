package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户行为模拟修改体
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class UserModelModifyBo extends UserModelAddBo implements Serializable {

	/**
	 * 用户行为编号
	 */
	@ApiModelProperty(value = "用户行为编号")
	private String resourceId;
}
