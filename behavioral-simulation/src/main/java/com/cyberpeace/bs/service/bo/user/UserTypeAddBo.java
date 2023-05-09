package com.cyberpeace.bs.service.bo.user;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户类型新增请求体
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class UserTypeAddBo implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;
}
