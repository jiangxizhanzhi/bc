package com.cyberpeace.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 名称计数
 *
 * @author SN
 * @date 2023/05/10
 */
/**
 * 该代码定义了一个名为 `NameCount` 的 Java 类，用于表示名称及其对应的计数。以下是代码的主要分析： 
 * 1. **类的功能**： 
 * - `NameCount` 是一个简单的数据模型类，主要用于存储名称（`name`）和与其关联的计数（`count`）。 
 * - 它实现了 `Serializable` 接口，表明它可以被序列化，可能用于网络传输或持久化。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段添加元信息，便于文档生成和接口测试。 
 * 3. **构造方法**： 
 * - 提供了两个构造方法： 
 * - 无参构造方法：用于框架或工具自动实例化对象。 
 * - 带参构造方法：允许在创建对象时直接初始化 `name` 和 `count`。 
 * 4. **方法功能**： 
 * - `add()` 方法用于将 `count` 字段的值加 1，实现计数递增。 
 * 5. **适用场景**： 
 * - 该类适用于需要统计某个名称出现次数的场景，例如统计用户输入的关键词频率、日志记录等。 
 * 总结：这是一个轻量级的数据模型类，结构清晰，功能单一，适合用作 DTO（数据传输对象）。 
**/
@Getter
@Setter
public class NameCount implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 计数
	 */
	@ApiModelProperty(value = "计数")
	private Integer count;

	public NameCount() {}

	public NameCount(String name, Integer count) {
		this.name = name;
		this.count = count;
	}

	public void add() {
		this.count += 1;
	}
}

