package com.cyberpeace.integratation.model.scene;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 流原木分页上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `FlowLogsPageContext` 的 Java 类，该类用于表示流原木（可能是指日志或记录）的分页上下文。以下是对其主要特性和功能的简要分析： 
 * ### 包声明与导入 
 * - **包声明**：`com.cyberpeace.integratation.model.scene` 
 * - 表明这个类位于 `com.cyberpeace.integratation.model.scene` 包中。 
 * - **导入**： 
 * - `lombok.Getter` 和 `lombok.Setter`：这两个注解由 Lombok 库提供，可以自动生成属性的 getter 和 setter 方法，从而减少样板代码。 
 * - `java.io.Serializable`：表明该类实现了 `Serializable` 接口，这意味着它可以被序列化，通常用于对象在网络上传输或者持久化存储。 
 * ### 类声明 
 * - **类名**：`FlowLogsPageContext` 
 * - 这个类的主要作用是封装与分页相关的查询参数。 
 * - **实现接口**：`Serializable` 
 * - 说明该类的对象可以被序列化，这在分布式系统中非常有用，因为对象可以被序列化后在网络上传输。 
 * ### 属性 
 * - `private String id;` 
 * - 唯一标识符。 
 * - `private String startTime;` 
 * - 查询数据的开始时间。 
 * - `private String endTime;` 
 * - 查询数据的结束时间。 
 * - `private Integer perPage;` 
 * - 每页显示的数据条数。 
 * - `private Integer page;` 
 * - 当前的分页号。 
 * - `private String sort;` 
 * - 数据排序规则。 
 * ### 注解 
 * - `@Getter` 和 `@Setter`：由 Lombok 提供，会自动生成这些属性的 getter 和 setter 方法，因此不需要显式地编写这些方法。 
 * ### 总结 
 * 这个类主要用于构建分页查询时的请求参数，包含了分页信息、时间范围以及排序等信息。通过使用 Lombok 的注解，减少了样板代码的编写，使得代码更加简洁易读。此外，由于实现了 `Serializable` 接口，这个类的对象可以在需要时被序列化和反序列化，增加了其在分布式系统中的适用性。 
**/
@Getter
@Setter
public class FlowLogsPageContext implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * 开始时间
	 */
	private String startTime;

	/**
	 * 结束时间
	 */
	private String endTime;

	/**
	 * 每分页
	 */
	private Integer perPage;

	/**
	 * 分页
	 */
	private Integer page;

	/**
	 * 排序
	 */
	private String sort;

}

