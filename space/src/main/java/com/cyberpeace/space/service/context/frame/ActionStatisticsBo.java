/**
 * 这段代码定义了一个名为 `ActionStatisticsBo` 的 Java 类，用于表示“行动统计学”的业务对象（BO）。以下是简要分析： 
 * 1. **类的功能**： 
 * - 这是一个简单的数据传输对象（DTO），主要用于存储与行动统计相关的数据。 
 * 2. **继承与接口**： 
 * - 类实现了 `Serializable` 接口，表明它可以被序列化，通常用于在分布式系统中传递对象或持久化数据。 
 * 3. **字段**： 
 * - `type`: 表示某种类型的整数值。 
 * - `date`: 表示日期的字符串。 
 * 4. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，简化了代码。 
 * 5. **文档**： 
 * - 类上有 Javadoc 注释，说明了类的用途、作者、版本等信息。 
 * 总结：这是一个简单的数据类，用于封装行动统计的相关信息，并支持序列化。 
**/
package com.cyberpeace.space.service.context.frame;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 行动统计学Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class ActionStatisticsBo implements Serializable {

	private Integer type;

	private String date;

}