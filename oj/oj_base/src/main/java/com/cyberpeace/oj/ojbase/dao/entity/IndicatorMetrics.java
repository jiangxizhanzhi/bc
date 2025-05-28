/**
 * 这段代码定义了一个名为 `IndicatorMetrics` 的 Java 类，该类用于表示数据库中的 `p_metrics_indicator` 表。以下是对代码的详细分析： 
 * 1. **包声明**： 
 * ```java 
 * package com.cyberpeace.oj.ojbase.dao.entity; 
 * ``` 
 * 这行代码声明了类所在的包路径。 
 * 2. **导入依赖**： 
 * ```java 
 * import com.baomidou.mybatisplus.annotation.IdType; 
 * import com.baomidou.mybatisplus.annotation.TableId; 
 * import com.baomidou.mybatisplus.annotation.TableName; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * ``` 
 * 导入了 MyBatis-Plus 框架相关的注解和 Lombok 库的注解。 
 * 3. **使用 Lombok 注解**： 
 * ```java 
 * @Setter 
 * @Getter 
 * ``` 
 * 使用 Lombok 库的 `@Getter` 和 `@Setter` 注解自动生成属性的 getter 和 setter 方法。 
 * 4. **表映射注解**： 
 * ```java 
 * @TableName("p_metrics_indicator") 
 * ``` 
 * 使用 MyBatis-Plus 的 `@TableName` 注解将该类与数据库中的 `p_metrics_indicator` 表进行映射。 
 * 5. **类定义**： 
 * ```java 
 * public class IndicatorMetrics { 
 * ``` 
 * 定义了一个名为 `IndicatorMetrics` 的公共类。 
 * 6. **属性定义及注解**： 
 * ```java 
 * @TableId(type = IdType.AUTO) 
 * private Integer id; 
 * ``` 
 * 使用 MyBatis-Plus 的 `@TableId` 注解标记 `id` 字段为主键，并指定主键生成策略为自动增长（`IdType.AUTO`）。 
 * 7. **其他属性**： 
 * ```java 
 * private Integer metrics_id; 
 * private Integer indicatorId; 
 * private Integer parentId; 
 * ``` 
 * 定义了其他四个整型属性：`metrics_id`、`indicatorId` 和 `parentId`。 
 * ### 总结 
 * - 该类 `IndicatorMetrics` 映射到数据库表 `p_metrics_indicator`。 
 * - 使用了 Lombok 库简化了 getter 和 setter 方法的编写。 
 * - 主键 `id` 使用了 MyBatis-Plus 提供的 `@TableId` 注解，并设置了自增策略。 
 * - 其他字段包括 `metrics_id`、`indicatorId` 和 `parentId`，这些字段的具体含义需要根据业务逻辑来确定。 
**/
package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@TableName("p_metrics_indicator")
public class IndicatorMetrics {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private Integer metrics_id;

	private Integer indicatorId;

	private Integer parentId;
}
