package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 这段代码定义了一个名为 `Indicator` 的 Java 类，该类是用于表示某个数据库表 (`p_indicator`) 的实体。下面是对此代码的详细分析： 
 * 1. **包声明**: 
 * ```java 
 * package com.cyberpeace.oj.ojbase.dao.entity; 
 * ``` 
 * 这行代码声明了类所在的包名，即 `com.cyberpeace.oj.ojbase.dao.entity`。 
 * 2. **导入语句**: 
 * ```java 
 * import com.baomidou.mybatisplus.annotation.IdType; 
 * import com.baomidou.mybatisplus.annotation.TableId; 
 * import com.baomidou.mybatisplus.annotation.TableName; 
 * import lombok.Data; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * ``` 
 * - `com.baomidou.mybatisplus.annotation.*`：这些是 MyBatis-Plus 框架提供的注解，用于简化 MyBatis 的使用。 
 * - `IdType.AUTO` 用于指定主键生成策略为自动增长。 
 * - `TableId` 用于标记字段为主键。 
 * - `TableName` 用于指定与该实体关联的数据库表名。 
 * - `lombok.*`：Lombok 是一个 Java 库，通过注解减少样板代码。这里用到的注解： 
 * - `@Data`：包含 `@Getter`, `@Setter`, `@ToString`, `@EqualsAndHashCode` 和 `@RequiredArgsConstructor`。 
 * - `@Getter`：为所有字段生成 getter 方法。 
 * - `@Setter`：为所有字段生成 setter 方法。 
 * 3. **类声明**: 
 * ```java 
 * @Data 
 * @Getter 
 * @Setter 
 * @TableName("p_indicator") 
 * public class Indicator { 
 * ``` 
 * - `@Data`：此注解会自动生成 getter 和 setter 方法、`toString()` 方法、`equals()` 和 `hashCode()` 方法。 
 * - `@Getter` 和 `@Setter`：这两个注解在 Lombok 中是冗余的，因为 `@Data` 已经包含了它们的功能。 
 * - `@TableName("p_indicator")`：指定这个实体对应的数据库表名为 `p_indicator`。 
 * 4. **字段声明**: 
 * ```java 
 * private Integer id; 
 * private String name; 
 * private Integer indicatorType; 
 * ``` 
 * - `id`: 主键字段，类型为整数（`Integer`）。 
 * - `name`: 名称字段，类型为字符串（`String`）。 
 * - `indicatorType`: 指标类型字段，类型为整数（`Integer`）。 
 * 5. **主键配置**: 
 * ```java 
 * @TableId(type = IdType.AUTO) 
 * private Integer id; 
 * ``` 
 * - `@TableId(type = IdType.AUTO)`：这个注解指定了 `id` 字段为主键，并且使用自动增长的方式生成主键值。 
 * 总结： 
 * - `Indicator` 类映射到 `p_indicator` 表。 
 * - `id` 字段被标记为主键，并且使用自动增长方式。 
 * - `name` 和 `indicatorType` 字段没有特别的注解，所以它们只是简单的数据库字段。 
 * - 使用 Lombok 注解简化了类的实现，减少了样板代码。 
**/
@Data
@Getter
@Setter
@TableName("p_indicator")
public class Indicator {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private String name;

	private Integer indicatorType;
}

