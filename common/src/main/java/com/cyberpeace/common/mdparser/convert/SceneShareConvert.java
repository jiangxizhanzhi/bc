package com.cyberpeace.common.mdparser.convert;

/**
 * 现场分享转换
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public enum SceneShareConvert implements MdConvertEnum<Integer, String> {

	PRIVATE(1, "否"), SHARE(2, "是");

	private Integer code;

	private String mode;

	/**
	 * 现场分享转换
	 *
	 * @param code 代码
	 * @param mode 模式
	 */
	SceneShareConvert(Integer code, String mode) {
		this.code = code;
		this.mode = mode;
	}

	@Override
	public Integer to() {
		return code;
	}

	@Override
	public String reTo() {
		return mode;
	}
}
