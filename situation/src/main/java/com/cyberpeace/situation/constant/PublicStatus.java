package com.cyberpeace.situation.constant;

import java.util.Arrays;

import lombok.Getter;

public enum PublicStatus {

	/**
	 * 大屏状态（想定）
	 */

	OTHERS(0, "其它", false, "-", "-", "-", "-", false),
	ZERO(1, "未开始", false, "-", "-", "-", "-", false),
	EDIT(2, "编辑中", true, "拟制完成", "-", "拟制完成", "-", false),
	COMPLETE(3, "已完成", false, "-", "-", "-", "重新拟制", true),
	ISSUE(4, "已下发", false, "-", "-", "-", "-", true),
	DIRECTING(5, "导调中", false, "-", "-", "-", "-", true),
	DIRECT_COMPLETE(6, "导调完成", false, "-", "-", "-", "-", true),
	REVIEW(7, "评审中", false, "-", "-", "-", "-", true),
	REVIEW_COMPLETE(8, "评审完成", false, "-", "-", "-", "-", true);

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

	@Getter
	private Boolean preview;

	PublicStatus(Integer code, String name, Boolean edit, String finishButtonXd,
			String resetButtonXd, String finishButton, String resetButton, Boolean preview) {
		this.code = code;
		this.name = name;
		this.edit = edit;
		this.finishButtonXd = finishButtonXd;
		this.resetButtonXd = resetButtonXd;
		this.finishButton = finishButton;
		this.resetButton = resetButton;
		this.preview = preview;
	}

	public static PublicStatus getPublicStatusByCode(Integer code) {
		return Arrays.stream(PublicStatus.class.getEnumConstants())
				.filter(t -> t.getCode().equals(code)).findFirst().orElse(PublicStatus.OTHERS);
	}

	public static PublicStatus getPublicStatusByName(String name) {
		return Arrays.stream(PublicStatus.class.getEnumConstants())
				.filter(t -> t.getName().equals(name)).findFirst().orElse(PublicStatus.OTHERS);
	}

}
