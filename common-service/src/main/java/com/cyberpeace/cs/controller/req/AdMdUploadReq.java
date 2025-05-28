/**
 * 这段代码定义了一个名为 `AdMdUploadReq` 的 Java 类，用于处理广告 Markdown 文件上传的请求体。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个请求体类，主要用于接收通过 HTTP 请求上传的文件（Markdown 文件）。 
 * 2. **关键特性**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段添加了描述信息，便于 API 文档生成。 
 * 3. **主要字段**： 
 * - `file`: 类型为 `MultipartFile`，用于接收上传的文件。`MultipartFile` 是 Spring 提供的接口，用于处理 HTTP 请求中的文件上传。 
 * 4. **注释**： 
 * - 类上有详细的注释，说明了该类的作用是处理广告 Markdown 文件上传请求。 
 * 总结：这是一个简单的请求体类，专为处理文件上传设计，结合了 Lombok 和 Swagger 的功能，提高了代码的简洁性和可维护性。 
**/
package com.cyberpeace.cs.controller.req;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 广告Markdown上传请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class AdMdUploadReq implements Serializable {

	/**
	 * 文件
	 */
	@ApiModelProperty("文件")
	private MultipartFile file;
}
