package com.cyberpeace.common.mdparser.convert;

/**
 * 场景共享转换
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see MdConvertEnum
 * @since 1.0.0
 */
public enum SceneShareConvert implements MdConvertEnum<Integer, String> {

	/**
	 * 私人
	 *
	 * @see SceneShareConvert
	 */
	PRIVATE(1, "否"),
	/**
	 * 共享
	 *
	 * @see SceneShareConvert
	 */
	SHARE(2, "是");

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	private Integer code;

	/**
	 * 模式
	 *
	 * @see String
	 */
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
