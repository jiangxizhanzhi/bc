package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 目录划分
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see GenericEnum
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `DumpModule` 的枚举类，该枚举类实现了 `GenericEnum` 接口。以下是对代码的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.ojbase.constant` 
 * - **导入**： 
 * - `com.cyberpeace.common.baseinterface.GenericEnum` 
 * - `lombok.Getter` 
 * ### 枚举定义 
 * - **枚举名称**：`DumpModule` 
 * - **实现接口**：`GenericEnum` 
 * ### 枚举值 
 * - `COURSE`：代表课程，代码为 `0`，描述为 `"course"` 
 * - `CTF_PRACTICE`：代表 CTF 实践，代码为 `1`，描述为 `"ctf"` 
 * ### 成员变量 
 * - `code`：表示枚举值的代号，类型为 `Integer`。 
 * - `desc`：表示枚举值的描述，类型为 `String`。 
 * ### 构造函数 
 * - 构造函数 `DumpModule(Integer code, String desc)` 初始化 `code` 和 `desc` 变量。 
 * ### 注解 
 * - `@Getter`：由 Lombok 提供，自动生成 `code` 和 `desc` 的 getter 方法。 
 * ### 文档注释 
 * - 类级别的文档注释描述了枚举的作用，并提供了作者、版本和日期信息。 
 * - 每个枚举值都有详细的文档注释，说明其用途。 
 * ### 总结 
 * 这段代码定义了一个用于表示目录划分的枚举类 `DumpModule`，其中包含两个枚举值 `COURSE` 和 `CTF_PRACTICE`。每个枚举值都具有一个代码和描述，这些信息可以通过 Lombok 自动生成的 getter 方法访问。 
**/
public enum DumpModule implements GenericEnum {

	/**
	 * 课程
	 *
	 * @see DumpModule
	 */
	COURSE(0, "course"),
	/**
	 * CTF 实践
	 *
	 * @see DumpModule
	 */
	CTF_PRACTICE(1, "ctf");


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


	DumpModule(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}

