package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * 注意附件请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `NoteAttachmentReq` 的 Java 类，用于处理与附件相关的请求体。以下是该类的主要特性和功能： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.oj.ojbase.controller.req` 
 * - **类名**：`NoteAttachmentReq` 
 * - **实现接口**：`Serializable` 
 * ### 主要特性 
 * 1. **注解** 
 * - 使用了 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 `@ApiModel` 注解，表示这是一个 Swagger API 模型。 
 * - 使用了 `@ApiModelProperty` 注解，为模型中的字段提供描述。 
 * 2. **属性** 
 * - `noteType`: 一个 `Integer` 类型的变量，表示注释类型，可能的值为 1（笔记）或 2（实验报告）。 
 * - `resourceType`: 一个 `Integer` 类型的变量，表示资源类型，可能的值为 1（课程）、2（课时）、3（练习）或 4（课后练习）。 
 * - `file`: 一个 `MultipartFile` 类型的变量，用于上传文件。 
 * 3. **方法** 
 * - `setNote_type(Integer noteType)`: 设置 `noteType` 属性的方法。 
 * - `setResource_type(Integer resourceType)`: 设置 `resourceType` 属性的方法。 
 * ### 总结 
 * - `NoteAttachmentReq` 类主要用于处理与附件相关的请求体，包括注释类型、资源类型和上传的文件。 
 * - 通过使用 Lombok 的注解，简化了 getter 和 setter 方法的编写。 
 * - 使用 Swagger 注解，便于生成 API 文档。 
 * - 该类实现了 `Serializable` 接口，意味着它可以被序列化，可以在网络中传输或保存到磁盘。 
 * 这个类的设计是为了方便在控制器层接收与附件相关的请求参数，并且可以与前端进行良好的交互。 
**/
@Getter
@Setter
@ApiModel
public class NoteAttachmentReq implements Serializable {

	/**
	 * 注释类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(name = "note_type", value = "笔记类型 笔记类型 1=笔记 2=实验报告")
	private Integer noteType;

	/**
	 * 资源类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(name = "resource_type", value = "资源类型 1=课程 2=课时 3=练习 4=课后练习")
	private Integer resourceType;

	/**
	 * 文件
	 *
	 * @see MultipartFile
	 */
	@ApiModelProperty(name = "file", value = "附件内容")
	private MultipartFile file;

	public void setNote_type(Integer noteType) {
		this.noteType = noteType;
	}

	public void setResource_type(Integer resourceType) {
		this.resourceType = resourceType;
	}
}

