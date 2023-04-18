package com.cyberpeace.situation.constant;

import java.util.Arrays;

import lombok.Getter;

public enum PublicStatus {

	/**
	 * 大屏状态（想定）
	 */

	OTHERS(0, "其它", false),
	PREPARATION(1, "拟制中", true),
	COMPLETE(2, "拟制完成", false),
	REVIEW(3, "评审中", false),
	PASS(4, "评审通过", false),
	NOPASS(5, "评审不通过", false),
	MODIFICATION(6, "修改完成", false),
	ESCALATION(7, "方案完成上报", false);

	@Getter
	private Integer code;

	@Getter
	private String name;

	@Getter
	private Boolean edit;

	PublicStatus(Integer code, String name, Boolean edit) {
		this.code = code;
		this.name = name;
		this.edit = edit;
	}

	public static PublicStatus getPublicStatusByCode(Integer code) {
		return Arrays.stream(PublicStatus.class.getEnumConstants())
				.filter(t -> t.getCode().equals(code)).findFirst().orElse(PublicStatus.OTHERS);
	}

}
