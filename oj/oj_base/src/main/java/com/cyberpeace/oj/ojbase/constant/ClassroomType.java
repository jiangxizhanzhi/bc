/**
 * 这段代码定义了一个名为 `ClassroomType` 的枚举类，用于表示不同类型的教室或竞赛类型。以下是对其主要特性和功能的简要分析： 
 * ### 主要特性 
 * 1. **继承关系**: 
 * - `ClassroomType` 实现了 `GenericEnum` 接口，这表明它是一个泛型枚举，可能包含一些通用的枚举方法。 
 * 2. **字段**: 
 * - `code`: 表示每个枚举值的整数编码。 
 * - `desc`: 表示每个枚举值的描述字符串。 
 * 3. **注释**: 
 * - 每个枚举常量都有详细的注释，说明其用途和对应的描述。 
 * 4. **构造函数**: 
 * - 构造函数接受两个参数：`code` 和 `desc`，并将它们分别赋值给相应的字段。 
 * 5. **Lombok 注解**: 
 * - 使用了 Lombok 的 `@Getter` 注解，自动生成 `code` 和 `desc` 字段的 getter 方法。 
 * ### 枚举常量 
 * - `THEORY` (1, "理论赛") 
 * - `OM` (2, "运维赛") 
 * - `CTF` (3, "夺旗赛") 
 * - `AD` (4, "攻防赛") 
 * - `PERMEATE` (5, "渗透赛") 
 * - `CLASS` (8, "课程表") 
 * - `TEAM` (9, "团队训练") 
 * - `RB` (10, "红蓝对抗") 
 * ### 功能 
 * - 通过枚举常量来表示不同的教室类型或竞赛类型。 
 * - 提供了 `code` 和 `desc` 的访问器方法，方便外部获取这些信息。 
 * - 枚举常量与特定的 `code` 和 `desc` 关联，便于在系统中进行类型识别和描述。 
 * ### 总结 
 * 这个枚举类 `ClassroomType` 提供了一种清晰且结构化的方式来表示和管理不同的教室类型或竞赛类型。通过实现 `GenericEnum` 接口和使用 Lombok 的 `@Getter` 注解，简化了代码并增强了可读性。 
**/
package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 教室类型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see GenericEnum
 * @since 1.0.0
 */
public enum ClassroomType implements GenericEnum {

	/**
	 * 房间类型
	 *
	 * @see ClassroomType
	 */
	THEORY(1, "理论赛"),
	/**
	 * OM
	 *
	 * @see ClassroomType
	 */
	OM(2, "运维赛"),
	/**
	 * CTF
	 *
	 * @see ClassroomType
	 */
	CTF(3, "夺旗赛"),
	/**
	 * 竞赛
	 *
	 * @see ClassroomType
	 */
	AD(4, "攻防赛"),
	/**
	 * 渗透
	 *
	 * @see ClassroomType
	 */
	PERMEATE(5, "渗透赛"),
	/**
	 * 类
	 *
	 * @see ClassroomType
	 */
	CLASS(8, "课程表"),
	/**
	 * 团队
	 *
	 * @see ClassroomType
	 */
	TEAM(9, "团队训练"),
	/**
	 * RB
	 *
	 * @see ClassroomType
	 */
	RB(10, "红蓝对抗");

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@Getter
	private String desc;

	ClassroomType(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
