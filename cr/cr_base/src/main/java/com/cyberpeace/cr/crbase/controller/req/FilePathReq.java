package com.cyberpeace.cr.crbase.controller.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 这段代码定义了一个名为 `FilePathReq` 的 Java 类，位于包 `com.cyberpeace.cr.crbase.controller.req` 下。以下是简要分析： 
 * 1. **类功能**： 
 * - 该类主要用于接收文件路径相关的请求参数。 
 * - 它包含一个私有成员变量 `path`，用于存储文件路径。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Setter` 和 `@Getter` 注解： 
 * - 自动生成 `setPath` 和 `getPath` 方法。 
 * - 简化了手动编写 getter 和 setter 方法的工作。 
 * 3. **用途**： 
 * - 通常作为控制器层（Controller）中的请求参数类，用于接收前端传递的文件路径信息。 
 * 4. **设计特点**： 
 * - 单一职责：只关注文件路径的存储与获取。 
 * - 使用 Lombok 提高代码简洁性。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装文件路径信息，便于在系统中传递和使用。 
**/
@Setter
@Getter
public class FilePathReq {
    private String path;
}

