package com.cyberpeace.common.util;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 这段代码定义了一个名为 `ModelB` 的 Java 类，位于包 `com.cyberpeace.common.util` 中。以下是对其功能和特性的简要分析： 
 * 1. **类的定义**： 
 * - 类名：`ModelB` 
 * - 包名：`com.cyberpeace.common.util` 
 * - 实现了接口 `Serializable`，表示该类的对象可以被序列化。 
 * 2. **注解**： 
 * - 使用了 Lombok 提供的 `@Data` 注解，它自动生成了类的 getter、setter、toString、equals 和 hashCode 方法。 
 * 3. **字段**： 
 * - `name`：字符串类型，用于存储名称。 
 * - `num`：整数类型，用于存储数字。 
 * - `time`：日期类型，用于存储时间信息。 
 * - `b`：字符串类型，具体用途未在代码中说明。 
 * 4. **用途**： 
 * - 该类可能是一个数据模型类，用于存储和传输包含上述字段的数据。 
 * 总结：这是一个简单的 Java 数据模型类，通过 Lombok 简化了代码编写，支持序列化操作。 
**/
@Data
public class ModelB implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
    private String name;

    private Integer num;

    private Date time;

    private String b;

}

