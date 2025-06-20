package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 加载课程请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LoadCourseReq` 的 Java 类，用于表示加载课程请求的请求体。以下是对其主要特性和功能的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.oj.course.controller.req` 
 * - **类名**: `LoadCourseReq` 
 * - **继承**: 实现了 `Serializable` 接口，意味着该类的对象可以被序列化。 
 * ### 主要注解 
 * - **Lombok 注解**: 
 * - `@Getter`: 自动生成所有字段的 getter 方法。 
 * - `@Setter`: 自动生成所有字段的 setter 方法。 
 * - **Swagger 注解**: 
 * - `@ApiModel`: 用于描述这个类作为一个模型，通常与 Swagger 文档生成工具一起使用。 
 * - `@ApiModelProperty`: 用于描述类中的某个字段，同样用于 Swagger 文档生成。 
 * ### 字段 
 * - **file**: 类型为 `MultipartFile`，用于存储上传的文件。该字段使用了以下注解： 
 * - `@NotNull`: 表示该字段不能为空。 
 * - `@ApiModelProperty`: 描述了该字段是“数据文件”。 
 * ### 总结 
 * `LoadCourseReq` 类主要用于接收加载课程请求的数据。它包含一个必填的文件字段，允许客户端通过 HTTP 请求上传文件来加载课程内容。这个类设计简洁明了，并且利用 Lombok 和 Swagger 注解提高了代码的可读性和文档生成的便利性。 
**/
@Getter
@Setter
@ApiModel
public class LoadCourseReq implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 文件
	 *
	 * @see MultipartFile
	 */
	@ApiModelProperty("数据文件")
	@NotNull
	private MultipartFile file;

}

