package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 场景事件状态
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个枚举类 `SceneEventStatus`，用于表示场景事件的状态。以下是对代码的简要分析： 
 * 1. **枚举类定义**： 
 * - `SceneEventStatus` 是一个实现了接口 `GenericEnum` 的枚举类。 
 * - 枚举值包括 `INITIALIZE`, `START`, `PROCESS`, `PAUSE`, `FINISH`, 和 `FAILED`。 
 * 2. **字段与注解**： 
 * - `code` 和 `desc` 是枚举类的两个私有字段，分别表示状态的代号和描述。 
 * - 使用了 Lombok 的 `@Getter` 注解，自动生成这两个字段的 getter 方法。 
 * 3. **构造方法**： 
 * - 提供了一个私有的构造方法，用于初始化每个枚举值的 `code` 和 `desc`。 
 * 4. **功能**： 
 * - 该枚举类可以用来表示场景事件的不同状态，并且可以通过 `code` 或 `desc` 获取对应的状态信息。 
 * 总结：这是一个典型的枚举类实现，用于定义场景事件的状态，具有良好的封装性和可读性。 
**/
public enum SceneEventStatus implements GenericEnum {

	/**
	 * 初始化
	 */
	INITIALIZE(0, "初始化"),
	/**
	 * 开始
	 */
	START(1, "开始"),
	/**
	 * 过程
	 */
	PROCESS(2, "进行中"),
	/**
	 * 暂停
	 */
	PAUSE(3, "暂停"),
	/**
	 * 完成
	 */
	FINISH(4, "完成"),
	/**
	 * 失败
	 */
	FAILED(5, "失败");

	/**
	 * 代号
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	SceneEventStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}

