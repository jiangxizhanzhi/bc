/**
 * 这段代码定义了一个名为 `YxUserInfo` 的 Java 类，位于包 `com.cyberpeace.integratation.service.bo` 下。这个类使用了 Lombok 库来简化样板代码。 
 * ### 主要特点： 
 * 1. **Lombok 注解**： 
 * - `@Getter`：为所有字段生成 getter 方法。 
 * - `@Setter`：为所有字段生成 setter 方法。 
 * 2. **字段**： 
 * - `id`：一个整数类型的字段，用于存储用户 ID。 
 * - `username`：一个字符串类型的字段，用于存储用户名。 
 * - `name`：一个字符串类型的字段，用于存储用户的姓名。 
 * ### 总结： 
 * - 这个类表示一个用户的信息，包含用户的 ID、用户名和姓名。 
 * - 使用 Lombok 可以减少手动编写 getter 和 setter 方法的工作量，使代码更简洁。 
 * - 这个类可能用于与用户相关的业务逻辑处理中，例如在服务层或数据访问层。 
 * 如果你有任何进一步的问题或需要更详细的分析，请告诉我！ 
**/
package com.cyberpeace.integratation.service.bo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YxUserInfo {

	private Integer id;

	private String username;

	private String name;
}
