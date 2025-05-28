/**
 * 这段代码定义了一个名为 `IdReq` 的 Java 类，位于包 `com.cyberpeace.space.model.req` 下。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于接收包含 `resourceId` 字段的请求数据。 
 * - 它是一个简单的 POJO（Plain Old Java Object），用于传输资源 ID。 
 * 2. **依赖与注解**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，避免了手动编写这些方法的冗余代码。 
 * 3. **字段说明**： 
 * - `private String resourceId;`：表示一个字符串类型的字段，用于存储资源 ID。 
 * 4. **适用场景**： 
 * - 通常用于 Web 请求中接收参数或作为 DTO（Data Transfer Object）传递数据。 
 * 总结：这是一个简单的数据传输对象类，使用 Lombok 简化了代码，主要用于接收包含 `resourceId` 的请求数据。 
**/
package com.cyberpeace.space.model.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdReq {

	private String resourceId;
}
