package com.cyberpeace.bs.service.bo.user;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户类型数据
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class UserTypeBo extends UserTypeAddBo implements Serializable {

	/**
	 * id
	 */
	@ApiModelProperty(value = "uuid")
	private String resourceId;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
}
