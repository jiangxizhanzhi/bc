package com.cyberpeace.cr.crbase.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 雷达标签
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `RadarLabel` 的 Java 类，属于 `com.cyberpeace.cr.crbase.dao.entity` 包。以下是代码的主要分析： 
 * 1. **类的功能**： 
 * - 这是一个实体类，用于表示雷达标签（`RadarLabel`）。 
 * - 它主要用于与数据库表 `cr_radar_label` 进行映射。 
 * 2. **依赖的库**： 
 * - 使用了 `Lombok` 注解 (`@Getter`, `@Setter`) 来自动生成 getter 和 setter 方法。 
 * - 使用了 `MyBatis-Plus` 提供的注解 (`@TableName`, `@TableId`) 来简化 MyBatis 的配置。 
 * 3. **字段说明**： 
 * - `resourceId`: 数据库中的主键字段，使用 `@TableId` 注解指定其类型为自增（`IdType.AUTO`），对应的数据库字段名为 `resource_id`。 
 * - `label`: 表示标签的字符串字段，对应数据库表中的 `label` 字段。 
 * 4. **实现的接口**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以序列化，通常用于支持分布式系统中的对象传输或缓存。 
 * 5. **注解功能**： 
 * - `@TableName("cr_radar_label")`: 指定该类与数据库表 `cr_radar_label` 对应。 
 * - `@TableId(type = IdType.AUTO, value = "resource_id")`: 指定 `resourceId` 字段为主键，并且是自动增长的。 
 * 总结：这是一个简单的实体类，用于与数据库表 `cr_radar_label` 进行映射，主要包含资源 ID 和标签两个字段，并通过注解简化了 MyBatis-Plus 的配置。 
**/
@Getter
@Setter
@TableName("cr_radar_label")
public class RadarLabel implements Serializable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.AUTO, value = "resource_id")
	private Integer resourceId;

	/**
	 * 标签
	 */
	private String label;

}

