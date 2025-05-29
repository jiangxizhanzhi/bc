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
/**
 * 这段代码定义了一个枚举类 `SceneShareConvert`，用于表示场景共享的不同状态。以下是代码的简要分析： 
 * 1. **枚举定义**： 
 * - 枚举类 `SceneShareConvert` 实现了 `MdConvertEnum<Integer, String>` 接口。 
 * - 定义了两个枚举常量：`PRIVATE` 和 `SHARE`，分别表示私人场景和共享场景。 
 * 2. **字段**： 
 * - `code`：表示场景的状态码（整数类型）。 
 * - `mode`：表示场景的模式（字符串类型）。 
 * 3. **构造方法**： 
 * - 提供了一个私有的构造方法，用于初始化 `code` 和 `mode` 字段。 
 * 4. **接口方法实现**： 
 * - `to()` 方法返回枚举实例的 `code` 值。 
 * - `reTo()` 方法返回枚举实例的 `mode` 值。 
 * 5. **用途**： 
 * - 这个枚举类可以用于在不同的场景中表示和转换状态，例如在用户界面或数据处理中使用。 
 * 总结来说，这个枚举类提供了一种类型安全的方式来管理场景共享的状态，并且可以通过实现的接口方法方便地进行状态转换。 
**/
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

