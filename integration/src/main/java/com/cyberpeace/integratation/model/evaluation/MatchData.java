package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 匹配数据
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MatchData` 的 Java 类，位于包 `com.cyberpeace.integratation.model.evaluation` 中。这个类的主要目的是存储匹配数据，并提供相应的访问方法。以下是对该代码的详细分析： 
 * ### 主要组成部分 
 * 1. **导入语句** 
 * - `java.io.Serializable`: 表示 `MatchData` 类实现了 `Serializable` 接口，这意味着它可以被序列化（通常用于在网络上传输对象或持久化对象）。 
 * - `java.util.List`: 用于表示 `data` 属性的数据类型。 
 * - `lombok.Getter` 和 `lombok.Setter`: 这是 Lombok 库提供的注解，用于自动生成 getter 和 setter 方法。 
 * 2. **类定义** 
 * - `@Getter` 和 `@Setter`: 这些注解由 Lombok 提供，会为类中的所有属性生成 getter 和 setter 方法。 
 * - `public class MatchData implements Serializable`: 定义了一个公共类 `MatchData`，它实现了 `Serializable` 接口。 
 * 3. **属性** 
 * - `private Long baseTime;`: 一个长整型变量，表示基准时间。 
 * - `private List<MatchItemData> data;`: 一个泛型列表，包含 `MatchItemData` 对象，表示数据。 
 * 4. **文档注释** 
 * - `/** ... `: 类级别的 Javadoc 注释，提供了类的基本描述和作者信息。
 * ### 总结 
 * - **功能**: `MatchData` 类用于存储匹配数据，包括基准时间和一系列的匹配项数据。 
 * - **实现**: 使用 Lombok 的注解来简化代码，避免手动编写 getter 和 setter 方法。 
 * - **用途**: 适用于需要序列化和存储匹配数据的应用场景，例如在网络通信或数据库操作中。 
 * 这段代码简洁且功能明确，利用了 Lombok 来减少样板代码，提高了可读性和维护性。 
**/
@Getter
@Setter
public class MatchData implements Serializable {

	/**
	 * 基准时间
	 */
	private Long baseTime;

	/**
	 * 数据
	 */
	private List<MatchItemData> data;
}

