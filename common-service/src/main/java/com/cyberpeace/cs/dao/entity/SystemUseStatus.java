package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用状态
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `SystemUseStatus` 的 Java 类，用于表示系统使用状态的实体类。以下是代码的主要分析： 
 * 1. **包与依赖**： 
 * - 该类位于 `com.cyberpeace.cs.dao.entity` 包中。 
 * - 使用了 `mybatis-plus` 和 `lombok` 库： 
 * - `@TableName` 注解表明该类与数据库表 `system_use_status` 对应。 
 * - `@Getter` 和 `@Setter` 注解由 Lombok 提供，自动生成属性的 getter 和 setter 方法。 
 * 2. **类的功能**： 
 * - 该类表示系统使用状态的信息，包括 CPU、RAM 和磁盘等硬件资源的状态。 
 * 3. **字段说明**： 
 * - `id`：主键，使用 `@TableId` 注解，类型为自动增长（`IdType.AUTO`）。 
 * - `alterTime`：更改时间，使用 `@TableField` 注解，会在插入数据时自动填充当前时间。 
 * - `cpu`、`ram`、`disk`：分别表示中央处理器、内存和磁盘的状态信息。 
 * 4. **实现接口**： 
 * - 实现了 `Serializable` 接口，支持序列化，便于在需要时将对象转换为字节流进行传输或存储。 
 * 5. **注释**： 
 * - 类和字段都有详细的注释，描述了其用途和功能，符合良好的编码规范。 
 * 总结：这是一个典型的 MyBatis-Plus 实体类，结合了 Lombok 的便捷注解，用于数据库操作和持久化管理。 
**/
@Getter
@Setter
@TableName("system_use_status")
public class SystemUseStatus implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 更改时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date alterTime;

	/**
	 * 中央处理器
	 */
	private String cpu;

	/**
	 * 公羊
	 */
	private String ram;

	/**
	 * 磁盘
	 */
	private String disk;

}

