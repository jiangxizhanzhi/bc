package com.cyberpeace.common.model;

import com.cyberpeace.common.baseinterface.GenericEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * 身份验证表
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class AuthTable extends BaseTable {

	@ApiModelProperty("授权操作类型 1=共享 2=自定义")
	private Integer operationAuthType;

	@ApiModelProperty("授权操作用户编号 ,为编号分割符")
	private String operationAuthIds;

	@ApiModelProperty("授权访问类型 1=共享 2=自定义")
	private Integer readAuthType;

	@ApiModelProperty("授权访问组织编号 ,为分割符")
	private String readAuthIds;

	/**
	 * 身份验证类型
	 *
	 * @author cyberpeace
	 * @date 2023/05/06
	 */
	public enum AuthType implements GenericEnum {

		ALL_SHARE_MODE(1, "授权所有"), CUSTOM_SHARE_MODE(2, "自定义");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		AuthType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
