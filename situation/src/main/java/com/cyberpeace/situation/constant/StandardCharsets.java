package com.cyberpeace.situation.constant;

import com.cyberpeace.common.model.CommonContext;
import lombok.Getter;

public enum StandardCharsets {

	/**
	 * 颜色
	 */

	ISO("iso-8859-1", "iso", "iso");

	@Getter
	private String code;

	@Getter
	private String desc;

	@Getter
	private String enDesc;

	StandardCharsets(String code, String desc, String enDesc) {
		this.code = code;
		this.desc = desc;
		this.enDesc = enDesc;
	}

	public String getMsg() {
		return CommonContext.isChinese() ? desc : enDesc;
	}
}
