package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * EVAL 模板列表查询
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTemplateListQuery` 的 Java 类，该类用于表示评估模板列表查询的相关信息。以下是对这段代码的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.cr.evaluation.service.context` 
 * - **继承关系**：该类继承自 `PageReqBase`，表明它可能用于分页查询。 
 * - **实现接口**：该类实现了 `Serializable` 接口，表明它可以被序列化，便于在网络上传输或持久化存储。 
 * ### 注解 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成了属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 注解 `@ApiModel` 和 `@ApiModelProperty` 来描述该类和它的属性，以便于生成 API 文档。 
 * ### 属性 
 * - **categoryCode**：一个整型变量，用于表示类别代号。使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来生成 getter 和 setter 方法。此外，还手动定义了一个 `setCategory_code` 方法，这与 Lombok 自动生成的 `setCategoryCode` 方法重复了，可以删除。 
 * ### 总结 
 * - 该类主要用于查询评估模板列表，并且支持分页功能。 
 * - 使用了 Lombok 库简化了代码编写，减少了样板代码。 
 * - 通过 Swagger 注解，使得该类可以方便地集成到 Swagger 文档中，便于 API 文档的生成。 
 * ### 建议 
 * - 删除手动定义的 `setCategory_code` 方法，因为 Lombok 已经自动生成了对应的 `setCategoryCode` 方法。 
 * - 确保 `PageReqBase` 类的功能满足分页查询的需求。 
**/
@ApiModel
public class EvalTemplateListQuery extends PageReqBase implements Serializable {

	/**
	 * 类别代号
	 */
	@ApiModelProperty(name = "category_code", value = "测评类型")
	@Getter
	@Setter
	private Integer categoryCode;

	public void setCategory_code(Integer categoryCode) {
		this.categoryCode = categoryCode;
	}

}

