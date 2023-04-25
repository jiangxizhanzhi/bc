package com.cyberpeace.situation.constant;

import java.util.Arrays;

import lombok.Getter;

public enum PublicStatus {


	OTHERS(0, "其它", false, "-", "-", "-", "-", false),
	ZERO(1, "未开始", false, "-", "-", "-", "-", false),
	PREPARATION(2, "拟制中", true, "拟制完成", "-", "拟制完成", "-", false),
	COMPLETE(3, "拟制完成", false, "-", "-", "-", "重新拟制", true),
	REVIEW(4, "评审中", false, "-", "-", "-", "-", true),
	PASS(5, "评审通过", true, "修改完成", "-", "修改完成", "-", false),
	NOPASS(6, "评审不通过", true, "修改完成", "-", "修改完成", "-", false),
	MODIFICATION(7, "修改完成", false, "-", "-", "-", "-", true),
	ESCALATION(8, "方案完成上报", false, "-", "-", "-", "-", true);

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
