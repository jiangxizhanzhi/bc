package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 课堂档案请求体
 *
 * @author SN
 * @see Serializable
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ClassroomArchiveReq` 的 Java 类，用于表示课堂档案请求体。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包声明**：类位于 `com.cyberpeace.oj.course.controller.req` 包中。 
 * - **导入语句**： 
 * - 导入了 Lombok 库的 `Getter` 和 `Setter` 注解，用于自动生成 getter 和 setter 方法。 
 * - 导入了 Swagger 注解 `ApiModel` 和 `ApiModelProperty`，用于生成 API 文档。 
 * - 导入了 Java 的 `Serializable` 接口，使对象可以序列化。 
 * ### 类注释 
 * - 类名为 `ClassroomArchiveReq`，注释说明这是一个课堂档案请求体。 
 * - 作者为 "SN"，版本号为 "1.0.0"，日期为 "2024/11/08"。 
 * ### 类属性 
 * - **classroomId** 
 * - 注释说明这是课堂ID。 
 * - 使用了 `@ApiModelProperty` 注解，标明在 API 文档中的名称为 "课堂编号"。 
 * - 使用了 `@NotBlank` 注解，表明该字段不能为空或空白字符串。 
 * - 数据类型为 `String`。 
 * - **groupArchiveId** 
 * - 注释说明这是群档案ID。 
 * - 使用了 `@ApiModelProperty` 注解，标明在 API 文档中的名称为 "分组模板编号"。 
 * - 使用了 `@NotBlank` 注解，表明该字段不能为空或空白字符串。 
 * - 数据类型为 `String`。 
 * ### 类功能 
 * - 类实现了 `Serializable` 接口，意味着它可以被序列化，便于在网络上传输或保存到文件中。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动为类的属性生成 getter 和 setter 方法，减少样板代码。 
 * ### 总结 
 * 这个类是一个用于表示课堂档案请求体的数据传输对象（DTO），包含两个必须填写的字符串属性：`classroomId` 和 `groupArchiveId`。通过使用 Swagger 注解，可以方便地生成相关的 API 文档。 
**/
@Getter
@Setter
@ApiModel
public class ClassroomArchiveReq implements Serializable {

	/**
     * 课堂id
     *
     * @see String
     */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String classroomId;

	/**
     * 群档案id
     *
     * @see String
     */
	@ApiModelProperty("分组模板编号")
	@NotBlank
	private String groupArchiveId;

}

