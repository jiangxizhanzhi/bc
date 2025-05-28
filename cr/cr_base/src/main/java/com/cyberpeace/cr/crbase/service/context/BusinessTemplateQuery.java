/**
 * 该代码定义了一个名为 `BusinessTemplateQuery` 的 Java 类，用于处理业务模板查询相关的数据。以下是代码的简要分析： 
 * ### 主要功能 
 * - **继承与用途**：该类继承自 `PageReqBase`，通常用于分页查询请求的封装。 
 * - **属性定义**： 
 * - `type`：表示业务类型的 ID。 
 * - `search`：用于检索的参数。 
 * - `name`：模板的名称。 
 * - `ids`：一个字符串列表，可能包含多个模板 ID。 
 * - **方法**： 
 * - `setSearch(String search)`：重写了 `search` 属性的 setter 方法，在设置值时会对搜索参数进行处理（调用 `LocalStringUtil.transfer` 方法）。 
 * ### 特点 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，简化了 getter 和 setter 方法的生成。 
 * - 借助 Swagger 注解（`@ApiModel` 和 `@ApiModelProperty`），便于 API 文档生成，清晰描述了每个字段的含义。 
 * - 对 `search` 字段进行了特殊处理，通过 `LocalStringUtil.transfer` 方法进行格式化或清理。 
 * ### 总结 
 * 这是一个典型的业务查询类，主要用于后台系统中对业务模板的分页查询，支持多种筛选条件。通过注解和工具方法的使用，提高了代码的可读性和复用性。 
**/
package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.LocalStringUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 业务模板查询
 *
 * @author SN
 * @date 2024/11/08
 *//*
 * 后台使用的查询类
 */
@ApiModel
@Getter
@Setter
public class BusinessTemplateQuery extends PageReqBase {

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "业务类型ID")
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
	 * id列表
	 */
	private List<String> ids;

	public void setSearch(String search) {
		String newPattern = search;
		if (!StringUtils.isBlank(search)) {

			newPattern = LocalStringUtil.transfer(search);			

		}

		this.search = newPattern;
	}
}