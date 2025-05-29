package com.cyberpeace.common.mdparser.convert;


import com.cyberpeace.common.baseinterface.GenericEnum;

/**
 * 训练类型转换
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see MdConvertEnum
 * @see GenericEnum
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 功能概述： 
 * 该代码定义了一个枚举类 `TrainTypeConvert`，用于表示训练类型的转换。它实现了两个接口：`MdConvertEnum<Integer, String>` 和 `GenericEnum`，并提供了训练类型的代码（`Integer`）与描述（`String`）之间的相互转换。 
 * #### 主要特性： 
 * 1. **枚举值**： 
 * - 定义了五个枚举常量：`TEST`, `MAINTENANCE`, `SIMULATION`, `SCENES`, `OTHER`。 
 * - 每个枚举值包含两个属性：`trainType`（训练类型的整数值）和`desc`（描述字符串）。 
 * 2. **构造函数**： 
 * - 提供了一个私有的构造函数，用于初始化每个枚举值的`trainType`和`desc`。 
 * 3. **接口实现**： 
 * - 实现了`MdConvertEnum`接口的两个方法： 
 * - `to()`：返回训练类型的整数值。 
 * - `reTo()`：返回训练类型的描述字符串。 
 * - 实现了`GenericEnum`接口的两个方法： 
 * - `getCode()`：返回训练类型的整数值。 
 * - `getDesc()`：返回训练类型的描述字符串。 
 * 4. **用途**： 
 * - 通过该枚举类可以方便地在训练类型的代码和描述之间进行转换，适用于需要统一管理训练类型的应用场景。 
 * #### 总结： 
 * `TrainTypeConvert` 是一个功能清晰的枚举类，主要用于训练类型的管理和转换。它通过实现接口提供了标准化的方法来获取训练类型的代码和描述，便于在不同模块间使用。 
**/
public enum TrainTypeConvert implements MdConvertEnum<Integer, String> , GenericEnum {

	/**
	 * 测试
	 *
	 * @see TrainTypeConvert
	 */
	TEST(1, "渗透测试"),
	/**
	 * 保养
	 *
	 * @see TrainTypeConvert
	 */
	MAINTENANCE(2, "运维防护"),
	/**
	 * 模拟
	 *
	 * @see TrainTypeConvert
	 */
	SIMULATION(3, "系统仿真"),
	/**
	 * 场景
	 *
	 * @see TrainTypeConvert
	 */
	SCENES(4, "综合场景"),
	/**
	 * 其他
	 *
	 * @see TrainTypeConvert
	 */
	OTHER(5, "其他");

	/**
	 * 训练类型
	 *
	 * @see Integer
	 */
	private Integer trainType;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String desc;

	/**
	 * 训练类型转换
	 *
	 * @param trainType 训练类型
	 * @param desc      描述
	 */
	TrainTypeConvert(Integer trainType, String desc) {
		this.trainType = trainType;
		this.desc = desc;
	}

	/**
	 * 自
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer to() {
		return trainType;
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
		return trainType;
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

