package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Getter;
import lombok.Setter;


/**
 * 训练计划
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `TrainPlan` 的 Java 类，该类位于 `com.cyberpeace.oj.course.dao.entity` 包中。这个类是 `BaseTable` 类的一个子类，并且使用了 Lombok 库来简化 getter 和 setter 方法的生成。 
 * ### 主要特性： 
 * 1. **继承与注解**： 
 * - `TrainPlan` 类继承自 `BaseTable` 类。 
 * - 使用了 `@TableName` 注解指定数据库表名 (`oj_train_plan`)。 
 * - 使用了 `@TableId` 注解指定主键字段 (`resource_id`)，并且指定了主键类型为 UUID。 
 * 2. **字段定义**： 
 * - `resourceId`：资源 ID，类型为 `String`，用作主键。 
 * - `name`：训练计划的名称，类型为 `String`。 
 * - `createUserId`：创建用户的 ID，类型为 `String`。 
 * 3. **静态常量**： 
 * - `TASK_SET`：表示任务设置的字符串常量。 
 * - `TASK_SET_ID`：表示任务设置 ID 的字符串常量。 
 * ### 总结： 
 * - 这个类代表一个训练计划实体，用于在数据库中存储和检索训练计划的相关信息。 
 * - 它通过继承 `BaseTable` 类来获取一些通用的数据库操作方法，并且使用了 MyBatis Plus 的注解来简化与数据库的交互。 
 * - 字段 `resourceId` 是主键，且类型为 UUID。 
 * - 其他字段如 `name` 和 `createUserId` 分别表示训练计划的名称和创建者的用户 ID。 
 * - 类中定义了两个静态常量 `TASK_SET` 和 `TASK_SET_ID`，可能用于某些配置或标识用途。 
 * ### 适用场景： 
 * 这个类适用于需要管理训练计划的应用场景，例如在线学习平台、教育管理系统等。通过继承 `BaseTable` 和使用 MyBatis Plus 注解，可以方便地进行数据库操作，而无需编写大量的 CRUD 代码。 
**/
@Getter
@Setter
@TableName("oj_train_plan")
public class TrainPlan extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	private String createUserId;

	/**
	 * 任务设置
	 *
	 * @see String
	 */
	public static String TASK_SET = "Task Set";

	/**
	 * 任务设置id
	 *
	 * @see String
	 */
	public static String TASK_SET_ID = "task_set_id";
}

