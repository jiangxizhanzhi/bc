package com.cyberpeace.common.model;

import com.cyberpeace.common.baseinterface.GenericEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * auth 表
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class AuthTable extends BaseTable {

	/**
	 * 实操认证类型
	 */
	@ApiModelProperty("授权操作类型 1=共享 2=自定义")
	private Integer operationAuthType;

	/**
	 * 实操认证id列表
	 */
	@ApiModelProperty("授权操作用户编号 ,为编号分割符")
	private String operationAuthIds;

	/**
	 * 读取身份验证类型
	 */
	@ApiModelProperty("授权访问类型 1=共享 2=自定义")
	private Integer readAuthType;

	/**
	 * 读认证id列表
	 */
	@ApiModelProperty("授权访问组织编号 ,为分割符")
	private String readAuthIds;

	/**
	 * 身份验证类型
	 *
	 * @author SN
	 * @date 2024/11/07
	 */
	public enum AuthType implements GenericEnum {

		/**
		 * 所有共享模式
		 */
		ALL_SHARE_MODE(1, "授权所有"),
		/**
		 * 自定义共享模式
		 */
		CUSTOM_SHARE_MODE(2, "自定义");

		/**
		 * 代号
		 */
		@Getter
		private Integer code;

		/**
		 * 描述
		 */
		@Getter
		private String desc;

		/**
		 * 身份验证类型
		 *
		 * @param code 代号
		 * @param desc 描述
		 */
		AuthType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
