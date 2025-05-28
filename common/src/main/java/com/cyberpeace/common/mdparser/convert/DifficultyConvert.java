/**
 * 该代码定义了一个枚举类 `DifficultyConvert`，用于表示不同难度等级的转换。以下是代码的主要分析： 
 * 1. **功能概述**： 
 * - `DifficultyConvert` 是一个实现了 `MdConvertEnum<Integer, String>` 和 `GenericEnum` 接口的枚举类。 
 * - 它主要用于将难度的数值（`Integer`）与对应的描述（`String`）进行相互转换。 
 * 2. **枚举常量**： 
 * - 枚举类定义了三个常量：`PRIMARY`、`INTERMEDIATE` 和 `ADVANCED`，分别代表初级、中级和高级难度。 
 * - 每个常量都有两个属性：`difficulty`（难度值）和 `desc`（描述）。 
 * 3. **构造方法**： 
 * - 提供了一个私有的构造方法，用于初始化 `difficulty` 和 `desc` 属性。 
 * 4. **接口实现**： 
 * - 实现了 `MdConvertEnum<Integer, String>` 接口的方法： 
 * - `to()`：返回难度值（`difficulty`）。 
 * - `reTo()`：返回描述（`desc`）。 
 * - 实现了 `GenericEnum` 接口的方法： 
 * - `getCode()`：返回难度值（`difficulty`）。 
 * - `getDesc()`：返回描述（`desc`）。 
 * 5. **用途**： 
 * - 该枚举类可以方便地在程序中使用，提供了一种类型安全的方式来处理难度等级及其对应的描述。 
 * 总结来说，这段代码通过枚举类实现了难度等级的规范化管理，提供了数值与描述之间的双向转换功能。 
**/
package com.cyberpeace.common.mdparser.convert;

import com.cyberpeace.common.baseinterface.GenericEnum;

/**
 * 难度转换
 *
 * @author chenzhuo@date 2024/11/08@see Enum
 * @version 1.0.0
 * @date 2024/12/04
 * @see MdConvertEnum
 * @see GenericEnum
 * @since 1.0.0
 */
public enum DifficultyConvert implements MdConvertEnum<Integer, String>, GenericEnum {

	/**
	 * 难度值
	 *
	 * @see DifficultyConvert
	 */
	PRIMARY(0, "初级"),
	/**
	 * 中间
	 *
	 * @see DifficultyConvert
	 */
	INTERMEDIATE(1, "中级"),
	/**
	 * 高深
	 *
	 * @see DifficultyConvert
	 */
	ADVANCED(2, "高级");

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	private Integer difficulty;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String desc;

	/**
	 * 难度转换
	 *
	 * @param difficulty 难度
	 * @param desc       描述
	 */
	DifficultyConvert(Integer difficulty, String desc) {
		this.difficulty = difficulty;
		this.desc = desc;
	}

	/**
	 * 自
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer to() {
		return difficulty;
	}

	/**
	 * re 更改为
	 *
	 * @return {@code String }
	 */
	@Override
	public String reTo() {
		return desc;
	}


	/**
	 * 获取代号
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer getCode() {
		return difficulty;
	}

	/**
	 * 获取描述
	 *
	 * @return {@code String }
	 */
	@Override
	public String getDesc() {
		return desc;
	}
}
