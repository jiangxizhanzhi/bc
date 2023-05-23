package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据库访问权限添加请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationDbAccessAuthorityAddReq implements Serializable {

	/**
	 * 动画名称
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "系统名称", required = true)
	private String name;

	/**
	 * 数据类型
	 */
	@ApiModelProperty(value = "数据类型")
	private String dbType;

	/**
	 * 系统ip
	 */
	@ApiModelProperty(value = "白名单IP")
	private String sysIp;

}
