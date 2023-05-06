package com.cyberpeace.situation.constant.dashboard;

import java.util.Arrays;

import lombok.Getter;

/**
 * 想定方案状态
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum PublicStatus {

	/**
	 * 其它
	 */
	OTHERS(0, "其它", false, "-", "-", "-", "-", false),

	/**
	 * 未开始
	 */
	ZERO(1, "未开始", false, "-", "-", "-", "-", false),

	/**
	 * 拟制中
	 */
	PREPARATION(2, "拟制中", true, "拟制完成", "-", "拟制完成", "-", false),

	/**
	 * 拟制完成
	 */
	COMPLETE(3, "拟制完成", false, "-", "-", "-", "重新拟制", true),

	/**
	 * 评审中
	 */
	REVIEW(4, "评审中", false, "-", "-", "-", "-", true),

	/**
	 * 评审通过
	 */
	PASS(5, "评审通过", true, "修改完成", "-", "修改完成", "-", false),

	/**
	 * 评审不通过
	 */
	NOPASS(6, "评审不通过", true, "修改完成", "-", "修改完成", "-", false),

	/**
	 * 修改完成
	 */
	MODIFICATION(7, "修改完成", false, "-", "-", "-", "-", true),

	/**
	 * 方案完成上报
	 */
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
