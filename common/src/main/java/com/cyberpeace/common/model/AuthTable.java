package com.cyberpeace.common.model;

import lombok.Data;
import lombok.Getter;

@Data
public class AuthTable extends BaseTable {

	private Integer operationAuthType;

	private String operationAuthIds;

	private Integer readAuthType;

	private String readAuthIds;

	public enum AuthType {

		ALL_SHARE_MODE(1, "授权所有"), CUSTOM_SHARE_MODE(2, "自定义");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private AuthType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
