package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 保存课程类别请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SaveCourseCategoryReq` 的 Java 类，用于处理保存课程类别的请求。以下是对该代码的简要分析： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.oj.course.controller.req` 
 * - **功能**：该类表示保存课程类别请求的数据模型。 
 * - **继承**：实现了 `Serializable` 接口，意味着它可以被序列化以便在网络上传输。 
 * ### 类注解 
 * - **`@Getter` 和 `@Setter`**：Lombok 注解，自动生成属性的 getter 和 setter 方法。 
 * - **`@ApiModel`**：Swagger 注解，用于描述 API 模型对象。 
 * - **`@ApiModelProperty`**：Swagger 注解，用于描述模型属性。 
 * ### 属性 
 * 1. **`name`** 
 * - **类型**：`String` 
 * - **注解**： 
 * - `@ApiModelProperty("课程分类名称")`：描述该字段是课程分类名称。 
 * - `@NotBlank(groups = { Create.class })`：验证该字段在创建时不能为空白。 
 * - **方法**：重写了 `getName()` 方法，确保返回的字符串不包含前导或尾随空格。 
 * 2. **`parentId`** 
 * - **类型**：`String` 
 * - **注解**：`@ApiModelProperty("父级分类编号 第一级则不传值")`：描述该字段是父级分类编号，第一级分类不需要传递此字段。 
 * 3. **`courseType`** 
 * - **类型**：`Integer` 
 * - **注解**：`@ApiModelProperty(value = "课程类型", hidden = true)`：描述该字段是课程类型，但在 Swagger 文档中隐藏。 
 * - **默认值**：`CourseCategory.CourseType.BASE.getCode()`，表示默认为基本类型的课程。 
 * ### 总结 
 * - **主要用途**：处理保存课程类别请求的数据模型。 
 * - **数据校验**：通过 `@NotBlank` 注解确保创建新课程类别时名称字段不为空。 
 * - **格式处理**：通过重写 `getName()` 方法去除名称中的空白字符。 
 * - **默认值**：提供课程类型的默认值。 
 * - **文档说明**：使用 Swagger 注解描述字段信息。 
 * 这段代码主要用于接收前端发送的保存课程类别的请求，并进行必要的数据验证和格式处理。 
**/
@Getter
@Setter
@ApiModel
public class SaveCourseCategoryReq implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程分类名称")
	@NotBlank(groups = { Create.class })
	private String name;

	/**
	 * 父母id
	 *
	 * @see String
	 */
	@ApiModelProperty("父级分类编号 第一级则不传值")
	private String parentId;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "课程类型", hidden = true)
	private Integer courseType = CourseCategory.CourseType.BASE.getCode();

	public String getName() {
		return StringUtils.isNotBlank(name) ? name.trim() : name;
	}
}

