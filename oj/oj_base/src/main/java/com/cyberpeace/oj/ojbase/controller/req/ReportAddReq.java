package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 报告新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ReportAddReq` 的 Java 类，该类用于处理报告新增请求的数据传输对象（DTO）。以下是对其主要特性和功能的简要分析： 
 * 1. **包与导入**: 
 * - 该类位于 `com.cyberpeace.oj.ojbase.controller.req` 包中。 
 * - 使用了 `io.swagger.annotations.ApiModelProperty` 注解来描述 API 中模型属性的元数据。 
 * - 使用了 `lombok` 库中的 `@Getter` 和 `@Setter` 注解，以自动生成属性的 getter 和 setter 方法。 
 * 2. **实现接口**: 
 * - 实现了 `Serializable` 接口，这意味着该类的对象可以被序列化，以便在网络上传输或保存到文件中。 
 * 3. **属性**: 
 * - `content`: 一个 `String` 类型的变量，表示实验报告的内容。通过 `@ApiModelProperty` 注解描述了其在 API 文档中的用途。 
 * - `businessId`: 一个 `String` 类型的变量，表示课时资源编号，并且必须是不为空的字符串（使用 `@NotBlank` 注解）。 
 * - `operate`: 一个 `Boolean` 类型的变量，表示操作类型，默认为 `false` 表示存草稿，`true` 表示提交。 
 * 4. **方法**: 
 * - `getContent()`: 一个重写的方法，返回 `content` 属性的值。如果 `content` 为 `null`，则返回空字符串 `""`。 
 * 5. **注释与文档**: 
 * - 类和属性上有详细的 Javadoc 注释，说明了每个属性的作用及其用途。 
 * 6. **设计意图**: 
 * - 该类作为一个 DTO，主要用于从前端接收报告新增请求的数据，并进行相应的验证和处理。 
 * 总体来说，这段代码是一个结构清晰、注释详尽的数据传输对象类，适用于处理实验报告新增请求的数据。 
**/
@Getter
@Setter
public class ReportAddReq implements Serializable {

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("实验报告内容")
	private String content;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	@ApiModelProperty("课时资源编号")
	@NotBlank
	private String businessId;

	/**
	 * 操作
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "操作：存草稿=false,提交=true")
	private Boolean operate;

	public String getContent() {
		return null == content ? "" : content;
	}
}

