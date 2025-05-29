package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cs.dao.entity.LocalScene;

import lombok.Getter;
import lombok.Setter;

/**
 * eval task ops 上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTaskOpsContext` 的 Java 类，位于 `com.cyberpeace.cr.evaluation.service.context` 包中。这个类的主要目的是封装与评估任务操作相关的上下文信息。以下是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.cr.evaluation.service.context` 
 * - **类名**：`EvalTaskOpsContext` 
 * - **访问修饰符**：`public` 
 * ### 类注释 
 * - 注释描述了这个类的作用是“评估任务操作上下文”。 
 * - 提供了作者（`@author SN`）和创建日期（`@date 2024/11/08`）。 
 * ### 类属性 
 * - **私有属性**： 
 * - `private LocalScene localScene;`：用于存储本地场景对象。 
 * - `private EvalTask evalTask;`：用于存储评估任务对象。 
 * ### Getter 和 Setter 方法 
 * - 使用 Lombok 注解 `@Getter` 和 `@Setter` 自动生成了 `localScene` 和 `evalTask` 属性的 getter 和 setter 方法。 
 * ### 总结 
 * - 这个类主要用于封装评估任务操作所需的上下文信息，包括本地场景和评估任务。 
 * - 通过使用 Lombok 的 `@Getter` 和 `@Setter` 注解，减少了样板代码，使代码更加简洁。 
 * - 这个类可以被其他组件或服务用来传递和管理评估任务相关的数据。 
**/
@Getter
@Setter
public class EvalTaskOpsContext {

	/**
	 * 当地场景
	 */
	private LocalScene localScene;

	/**
	 * 评估任务
	 */
	private EvalTask evalTask;

}

