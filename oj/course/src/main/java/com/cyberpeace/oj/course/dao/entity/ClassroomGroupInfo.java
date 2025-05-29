package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Getter;
import lombok.Setter;


/**
 * 课堂群组信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassroomGroupInfo` 的 Java 类，该类继承自 `BaseTable`。这个类用于表示课堂群组的信息，并使用 MyBatis-Plus 进行数据库操作。以下是代码的主要分析： 
 * ### 包声明和导入 
 * - `com.cyberpeace.oj.course.dao.entity`: 该类所在的包名。 
 * - 导入了必要的 MyBatis-Plus 注解 (`@TableId`, `@TableName`) 和 Lombok 注解 (`@Getter`, `@Setter`)。 
 * ### 类注释 
 * - 类注释说明了 `ClassroomGroupInfo` 是一个课堂群组信息实体类。 
 * - 作者是 SN，版本是 1.0.0，最后更新日期是 2024 年 11 月 8 日。 
 * - 该类扩展了 `BaseTable` 类。 
 * ### 类属性 
 * - `resourceId`: 唯一标识符（UUID 类型），用于资源 ID。 
 * - `classroomId`: 课堂 ID。 
 * - `groupNo`: 群组编号。 
 * - `groupUserIds`: 群组用户 ID 列表，通常是一个逗号分隔的字符串。 
 * - `destroyDelay`: 群组销毁延迟时间。 
 * - `sceneShareMode`: 场景共享模式。 
 * - `lessonId`: 课程 ID。 
 * - `topoId`: 拓扑 ID。 
 * ### MyBatis-Plus 注解 
 * - `@TableName("oj_classroom_group_info")`: 表明该类对应的数据库表名为 `oj_classroom_group_info`。 
 * - `@TableId(type = IdType.UUID, value = "resource_id")`: 表明 `resourceId` 字段是主键，并且类型为 UUID。 
 * ### Lombok 注解 
 * - `@Getter` 和 `@Setter`: 自动生成所有字段的 getter 和 setter 方法。 
 * ### 总结 
 * - `ClassroomGroupInfo` 类主要用于表示课堂群组的信息，并且通过 MyBatis-Plus 提供的注解与数据库表 `oj_classroom_group_info` 对应。 
 * - 该类包含多个字段，用于存储课堂群组的各种信息，如群组编号、用户 ID 列表、销毁延迟等。 
 * - 使用 Lombok 的注解减少了样板代码，提高了代码的可读性和简洁性。 
 * 这个类的设计使得它可以方便地进行数据库操作，同时保持代码的简洁和清晰。 
**/
@Getter
@Setter
@TableName("oj_classroom_group_info")
public class ClassroomGroupInfo extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	private String classroomId;

	/**
	 * 组号
	 *
	 * @see Integer
	 */
	private Integer groupNo;

	/**
	 * 群用户id列表
	 *
	 * @see String
	 */
	private String groupUserIds;

	/**
	 * 销毁延迟
	 *
	 * @see Integer
	 */
	private Integer destroyDelay;

	/**
	 * 场景共享模式
	 *
	 * @see Integer
	 */
	private Integer sceneShareMode;

	/**
	 * 课id
	 *
	 * @see String
	 */
	private String lessonId;

	/**
	 * 拓扑id
	 *
	 * @see String
	 */
	private String topoId;

}

