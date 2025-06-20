package com.cyberpeace.integratation.model;

import com.cyberpeace.common.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户分页上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `UserPageContext` 的 Java 类，该类用于表示用户分页上下文。以下是对此代码的简要分析： 
 * ### 类概述 
 * - **包声明**：`com.cyberpeace.integratation.model` 
 * - **类名**：`UserPageContext` 
 * - **实现接口**：`Serializable` 
 * ### 注解 
 * - 使用了 Lombok 提供的注解来简化代码： 
 * - `@Getter` 和 `@Setter`：为所有字段生成 getter 和 setter 方法。 
 * - `@Builder`：允许使用构建器模式创建对象。 
 * - `@AllArgsConstructor` 和 `@NoArgsConstructor`：分别为所有字段生成全参构造函数和无参构造函数。 
 * ### 字段 
 * - `username`：字符串类型，表示用户名。 
 * - `ids`：字符串类型，表示 ID 列表。 
 * - `perPage`：整数类型，表示每页显示的条目数。 
 * - `page`：整数类型，表示当前页码。 
 * ### 方法 
 * - `getPerPage()`：返回每页显示的条目数。如果 `perPage` 为 `null`，则默认返回 `CommonConstant.NINE_NINE_NINE`（假设这是一个常量）。 
 * - `getPage()`：返回当前页码。如果 `page` 为 `null`，则默认返回 `CommonConstant.ONE`（假设这是一个常量）。 
 * ### 总结 
 * - 这个类主要用于处理用户分页相关的数据，包括用户名、ID 列表、每页显示的条目数和当前页码。 
 * - 使用了 Lombok 注解来减少样板代码。 
 * - 默认值逻辑通过 `getPerPage()` 和 `getPage()` 方法实现，确保在这些值为 `null` 时有合理的默认值。 
 * 这个类的设计是简洁且功能明确的，适合用于分页查询场景。 
**/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPageContext implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 用户名
	 */
	private String username;

	/**
	 * id列表
	 */
	private String ids;

	/**
	 * 每分页
	 */
	private Integer perPage;

	/**
	 * 分页
	 */
	private Integer page;

	public Integer getPerPage() {
		return null == perPage ? CommonConstant.NINE_NINE_NINE : perPage;
	}

	public Integer getPage() {
		return null == page ? CommonConstant.ONE : page;
	}
}

