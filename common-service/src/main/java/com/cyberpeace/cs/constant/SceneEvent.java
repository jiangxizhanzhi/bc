package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 场景事件
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个枚举类 `SceneEvent`，用于表示场景事件。以下是对代码的简要分析： 
 * 1. **枚举类定义**： 
 * - `SceneEvent` 是一个实现了 `GenericEnum` 接口的枚举类。 
 * - 枚举常量包括 `DEFAULT`, `INITIALIZE`, `CREATE`, `START`, `STOP`, `RESTART`, `PAUSE`, `RESUME`, `SNAPSHOOT`, `CLOSE`, 和 `UPDATE`。 
 * 2. **字段与注解**： 
 * - `code`：表示每个枚举常量的唯一标识符，使用 `@Getter` 注解，可以直接通过 `getCode()` 方法访问。 
 * - `desc`：表示每个枚举常量的描述信息，同样使用 `@Getter` 注解，可以通过 `getDesc()` 方法访问。 
 * 3. **构造方法**： 
 * - 私有构造方法 `SceneEvent(Integer code, String desc)` 用于初始化每个枚举常量的 `code` 和 `desc`。 
 * 4. **用途**： 
 * - 这个枚举类可以用于表示不同的场景事件，方便在代码中使用统一的事件标识和描述。 
 * 总结来说，这段代码提供了一种结构化的方式来管理场景事件，便于代码的可读性和维护性。 
**/
public enum SceneEvent implements GenericEnum {

	/**
	 * 违约
	 */
	DEFAULT(0, "默认"),
	/**
	 * 初始化
	 */
	INITIALIZE(1, "初始化"),
	/**
	 * 创造
	 */
	CREATE(2, "创建"),
	/**
	 * 开始
	 */
	START(3, "启动"),
	/**
	 * 停
	 */
	STOP(4, "停止"),
	/**
	 * 重新启动
	 */
	RESTART(5, "重启"),
	/**
	 * 暂停
	 */
	PAUSE(6, "暂停"),
	/**
	 * 恢复
	 */
	RESUME(7, "恢复"),
	/**
	 * 快照
	 */
	SNAPSHOOT(8, "快照"),
	/**
	 * 关闭
	 */
	CLOSE(9, "关闭"),
	/**
	 * 更新
	 */
	UPDATE(10, "修改");

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

	SceneEvent(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}

