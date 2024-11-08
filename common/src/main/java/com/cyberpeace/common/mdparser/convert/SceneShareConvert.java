package com.cyberpeace.common.mdparser.convert;

/**
 * 场景共享转换
 *
 * @author SN
 * @date 2024/11/08
 */
public enum SceneShareConvert implements MdConvertEnum<Integer, String> {

	PRIVATE(1, "否"),
	SHARE(2, "是");

	private Integer code;

	private String mode;

	/**
	 * 场景共享转换
	 *
	 * @param code 代号
	 * @param mode 模式
	 */
	SceneShareConvert(Integer code, String mode) {
		this.code = code;
		this.mode = mode;
	}

	/**
	 * 自
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer to() {
		return code;
	}

	/**
	 * re 更改为
	 *
	 * @return {@code String }
	 */
	@Override
	public String reTo() {
		return mode;
	}
}
