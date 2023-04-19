package com.cyberpeace.situation.constant;

import java.util.Arrays;

import lombok.Getter;

public enum PublicStatus {

	/**
	 * 大屏状态（想定）
	 */

	OTHERS(0, "其它", false, "-", "-", "-", "-"),
	ZERO(1, "未开始", false, "-", "-", "-", "-"),
	EDIT(2, "编辑中", true, "拟制完成", "-", "拟制完成", "-"),
	COMPLETE(3, "已完成", false, "-", "-", "-", "重新拟制"),
	ISSUE(4, "已下发", false, "-", "-", "-", "-"),
	DIRECTING(5, "导调中", false, "-", "-", "-", "-"),
	DIRECT_COMPLETE(6, "导调完成", false, "-", "-", "-", "-"),
	REVIEW(7, "评审中", false, "-", "-", "-", "-"),
	REVIEW_COMPLETE(8, "评审完成", false, "-", "-", "-", "-");

	@Getter
	private Integer code;

	@Getter
	private String name;

	@Getter
	private Boolean edit;

	@Getter
	private String finishButtonXd;

	@Getter
	private String resetButtonXd;

	@Getter
	private String finishButton;

	@Getter
	private String resetButton;

	PublicStatus(Integer code, String name, Boolean edit, String finishButtonXd,
			String resetButtonXd, String finishButton, String resetButton) {
		this.code = code;
		this.name = name;
		this.edit = edit;
		this.finishButtonXd = finishButtonXd;
		this.resetButtonXd = resetButtonXd;
		this.finishButton = finishButton;
		this.resetButton = resetButton;
	}

	public static PublicStatus getPublicStatusByCode(Integer code) {
		return Arrays.stream(PublicStatus.class.getEnumConstants())
				.filter(t -> t.getCode().equals(code)).findFirst().orElse(PublicStatus.OTHERS);
	}

}
