/**
 * 这段代码定义了一个名为 `FileCommonUploadReq` 的 Java 类，用于处理文件上传的请求体。以下是代码的主要特点和功能分析： 
 * 1. **类的功能**： 
 * - 这是一个用于接收文件上传请求的类，支持通过 Spring MVC 的 `MultipartFile` 接收文件数据。 
 * - 包含两个字段：`file`（上传的文件）和 `type`（上传的类型）。 
 * 2. **依赖与注解**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段提供元信息，便于生成 API 文档。 
 * - 实现了 `Serializable` 接口，表明该类可以被序列化，可能用于网络传输或持久化。 
 * 3. **字段说明**： 
 * - `file`: 用于接收上传的文件，类型为 `MultipartFile`，这是 Spring 提供的用于处理多部件文件上传的类。 
 * - `type`: 表示上传的类型，整数类型，具体含义需根据业务逻辑确定。 
 * 4. **设计目的**： 
 * - 该类旨在作为文件上传接口的请求参数模型，方便前端通过 HTTP 请求传递文件及附加信息。 
 * 5. **适用场景**： 
 * - 适用于需要接收文件并附带额外信息（如类型）的文件上传场景，结合 Spring Boot 和 Swagger 使用。 
 * 总结：这是一个简洁且功能明确的文件上传请求体类，通过 Lombok 减少了样板代码，并使用 Swagger 注解增强了可读性和文档生成能力。 
**/
package com.cyberpeace.cs.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * 文件常见上传请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class FileCommonUploadReq implements Serializable {

	/**
	 * 文件
	 */
	@ApiModelProperty("文件")
	private MultipartFile file;

	/**
	 * 类型
	 */
	@ApiModelProperty("上传类型 ")
	private Integer type;

}
