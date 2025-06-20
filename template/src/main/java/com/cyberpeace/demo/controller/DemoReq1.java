package com.cyberpeace.demo.controller;

import java.io.Serializable;

import lombok.Data;

/**
 * 演示 req1
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 该代码定义了一个名为 `DemoReq1` 的 Java 类，用于演示目的。以下是简要分析： 
 * 1. **类的功能**： 
 * - `DemoReq1` 是一个实现了 `Serializable` 接口的类，通常用于需要序列化的场景（如网络传输或持久化）。 
 * - 它包含两个字段：`htmlFilePath` 和 `pdfFilePath`，分别表示 HTML 文件路径和 PDF 文件路径。 
 * 2. **注解与特性**： 
 * - 使用了 Lombok 的 `@Data` 注解，自动生成了 getter、setter、toString、equals 和 hashCode 方法，减少了冗余代码。 
 * 3. **文档说明**： 
 * - 类的注释说明了其用途为“演示 req1”。 
 * - 字段的注释清晰地描述了其含义，并标注了类型为 `String`。 
 * 4. **适用场景**： 
 * - 此类可能用于接收或传递包含 HTML 和 PDF 文件路径的请求数据，常见于文件处理相关的业务逻辑中。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装 HTML 和 PDF 文件路径信息，便于后续操作或存储。 
**/
@Data
public class DemoReq1 implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * HTML 文件路径
	 *
	 * @see String
	 */
	private String htmlFilePath;

	/**
	 * PDF 文件路径
	 *
	 * @see String
	 */
	private String pdfFilePath;
}

