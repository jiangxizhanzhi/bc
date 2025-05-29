package com.cyberpeace.cr.crbase.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 训练记录，记录训练房间的创建和销毁
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `TrainRecord` 的 Java 类，用于表示训练记录实体。以下是代码的简要分析： 
 * 1. **类名与功能**： 
 * - 类名为 `TrainRecord`，位于包 `com.cyberpeace.cr.crbase.dao.entity` 中。 
 * - 注释说明该类用于记录训练房间的创建和销毁。 
 * 2. **注解与特性**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 MyBatis-Plus 的 `@TableName` 注解，指定数据库表名为 `cr_train_record`。 
 * - 使用了 MyBatis-Plus 的 `@TableId` 注解，定义主键字段 `id`，并设置为主键生成策略为 `AUTO`（自动增长）。 
 * 3. **字段说明**： 
 * - `id`: 主键，自增整数。 
 * - `businessType`: 业务类型，整数类型。 
 * - `businessTemplateId`: 业务模板ID，字符串类型。 
 * - `userId`: 用户ID，字符串类型。 
 * - `startTime`: 记录开始时间，`Date` 类型。 
 * - `endTime`: 记录结束时间，`Date` 类型。 
 * - `roomId`: 房间ID，字符串类型。 
 * - `roomName`: 房间名称，字符串类型。 
 * - `extra`: 额外信息，字符串类型。 
 * 4. **实现接口**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以序列化，便于在网络中传输或持久化存储。 
 * 总结：这是一个典型的实体类，用于映射数据库中的 `cr_train_record` 表，提供了数据存储和操作的基础结构。 
**/
@Getter
@Setter
@TableName("cr_train_record")
public class TrainRecord implements Serializable {

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 业务模板id
	 */
	private String businessTemplateId;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 房间名称
	 */
	private String roomName;

	/**
	 * 额外
	 */
	private String extra;

}

