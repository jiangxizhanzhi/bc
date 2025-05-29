package com.cyberpeace.cr.crbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 模板请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为 `TemplateReq` 的 Java 类，用于表示模板请求体。以下是代码的主要特点和功能分析： 
 * 1. **继承关系**： 
 * - `TemplateReq` 继承自 `PageReqBase`，表明它可能包含了分页相关的属性或方法。 
 * 2. **注解说明**： 
 * - 使用了 `@ApiModel` 注解，表示这是一个 API 模型类，通常用于 Swagger 文档生成。 
 * - 各字段使用了 `@ApiModelProperty` 注解，为每个字段提供了描述信息，方便接口文档生成。 
 * 3. **字段说明**： 
 * - `type`：类别 ID，用于标识模板的类型。 
 * - `search`：检索参数，用于搜索模板。 
 * - `name`：模板名称，用于标识模板的名称。 
 * - `difficult`：难度，用于表示模板的难度等级。 
 * 4. **Lombok 注解**： 
 * - 使用了 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，简化了代码。 
 * 5. **用途**： 
 * - 该类主要用于接收前端传来的模板相关请求数据，并可能与分页功能结合使用。 
 * 总结：`TemplateReq` 是一个封装了模板请求数据的模型类，支持分页功能，并通过注解增强了其可读性和文档生成能力。 
**/
@ApiModel
@Getter
@Setter
public class TemplateReq extends PageReqBase {

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类别ID")
	private String type;

	/**
	 * 搜索
	 */
	@ApiModelProperty(value = "检索参数")
	private String search;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 难
	 */
	@ApiModelProperty(value = "难度")
	private String difficult;

}

