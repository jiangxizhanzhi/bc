/**
 * 这段代码定义了一个名为 `MissionTemplatePageReq` 的 Java 类，用于表示任务模板分页请求的参数。以下是对代码的简要分析： 
 * 1. **类的继承**： 
 * - `MissionTemplatePageReq` 继承自 `PageReqBase`，表明它扩展了分页请求的基本功能。 
 * 2. **注解与功能**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，简化了代码。 
 * - Swagger 注解 `@ApiModelProperty` 用于为 API 文档生成描述字段的元信息，便于接口文档的生成和理解。 
 * 3. **字段说明**： 
 * - `type`：表示任务类型的整数字段，用于过滤或分类任务模板。 
 * - `difficulty`：表示任务难度的整数字段，用于根据任务难度进行筛选。 
 * 4. **用途**： 
 * - 这个类主要用于任务模板的分页查询请求，通过扩展 `PageReqBase` 提供分页功能，并增加了类型和难度两个额外的过滤条件。 
 * 总结：该类是一个基于分页请求的封装类，适用于任务模板的分页查询场景，支持通过类型和难度进行筛选。 
**/
package com.cyberpeace.cr.crbase.controller.req.mission;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务模板分页请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionTemplatePageReq extends PageReqBase {

	/**
	 * 类型
	 */
	@ApiModelProperty("类型")
	@Getter
	@Setter
	private Integer type;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度")
	@Setter
	@Getter
	private Integer difficulty;
}
