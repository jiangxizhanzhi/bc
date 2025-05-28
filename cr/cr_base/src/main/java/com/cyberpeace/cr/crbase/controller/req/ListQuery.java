/**
 * 该代码定义了一个名为 `ListQuery` 的 Java 类，用于处理列表查询请求。以下是对其功能和结构的简要分析： 
 * ### 主要功能： 
 * 1. **继承与扩展**： 
 * - `ListQuery` 继承自 `PageReqBase`，表明它是一个分页查询的基础类，可能包含分页相关的字段（如当前页码、每页大小等）。 
 * 2. **属性定义**： 
 * - 定义了多个查询条件字段，包括： 
 * - `category`：训练类别 ID。 
 * - `search`：检索参数，支持模糊搜索。 
 * - `name`：名称。 
 * - `difficult`：难度（整型值）。 
 * - `ids`：一组字符串类型的 ID 列表。 
 * 3. **方法实现**： 
 * - 重写了 `setSearch` 方法，对传入的 `search` 参数进行处理： 
 * - 如果 `search` 不为空，则通过 `LocalStringUtil.transfer` 方法对其进行转换（可能是去除空格或特殊字符等操作）。 
 * - 最终将处理后的值赋给 `search` 属性。 
 * 4. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，简化了属性的 getter 和 setter 方法生成。 
 * - 使用了 Swagger 的 `@ApiModel` 和 `@ApiModelProperty` 注解，为接口文档提供元信息。 
 * 5. **设计目的**： 
 * - 该类主要用于前台查询请求，封装了多种查询条件，便于后端接收和解析。 
 * ### 总结： 
 * - 该类是一个典型的业务查询请求类，结合了分页功能和多条件查询。 
 * - 具有良好的可读性和扩展性，适合用于前后端交互场景中。 
**/
package com.cyberpeace.cr.crbase.controller.req;

import java.util.List;

import com.cyberpeace.common.model.PageReqBase;

import com.cyberpeace.common.util.LocalStringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 列表查询
 *
 * @author SN
 * @date 2024/11/08
 *//*
 * 前台使用的查询类
 */
@ApiModel
@Getter
@Setter
public class ListQuery extends PageReqBase {

	/**
	 * 类别
	 */
	@ApiModelProperty(value = "训练类别ID")
	private String category;

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
	private Integer difficult;

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