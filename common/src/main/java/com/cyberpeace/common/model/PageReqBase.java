package com.cyberpeace.common.model;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 分页请求体基础
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为 `PageReqBase` 的类，用于处理分页请求的基本信息。以下是对其功能和结构的简要分析： 
 * 1. **继承与注解**： 
 * - `PageReqBase` 继承自 `PatternBase` 类。 
 * - 使用了 `@ApiModel` 注解，表明这是一个 API 模型类，通常用于描述 RESTful API 中的数据模型。 
 * 2. **字段说明**： 
 * - `perPage`：表示每页显示的记录数，默认值为 10。 
 * - `page`：表示当前页码，默认值为 1。 
 * - `sort`：表示排序参数，用于指定数据排序的方式。 
 * 3. **方法功能**： 
 * - `toPageParam()` 方法将当前对象转换为 MyBatis-Plus 的 `Page` 对象，用于分页查询。 
 * - 如果 `sort` 字段为空，则直接返回默认分页对象。 
 * - 如果 `sort` 以 `-` 开头，则设置排序方式为降序；否则为升序。 
 * - 检查 `sort` 是否包含非法字符（如空格、分号、括号等），若存在则抛出 `BusinessException` 异常。 
 * - 创建 `OrderItem` 对象并将其添加到 `Page` 的排序列表中。 
 * - 提供了 `setPer_page` 和 `setPage` 方法来设置分页相关的属性。 
 * 4. **依赖库**： 
 * - 使用了 `com.baomidou.mybatisplus` 提供的分页工具类 `Page` 和排序工具类 `OrderItem`。 
 * - 使用了 `io.swagger.annotations.ApiModel` 和 `ApiModelProperty` 注解，便于生成 Swagger 文档。 
 * - 使用了 `org.apache.commons.lang3.StringUtils` 进行字符串操作。 
 * - 使用了 `lombok` 的 `@Getter` 和 `@Setter` 注解简化属性的访问器和修改器生成。 
 * 5. **异常处理**： 
 * - 当 `sort` 包含非法字符时，会抛出自定义异常 `BusinessException`，并携带错误码 `ErrorCode.METHOD_ARG_NOT_VALID`。 
 * 总结来说，这个类是一个通用的分页请求封装类，支持基本的分页和排序功能，并且通过注解和异常机制确保了数据的安全性和规范性。 
**/
@ApiModel
public class PageReqBase extends PatternBase {

	/**
	 * 每分页
	 */
	@ApiModelProperty(name = "per_page", value = "每页大小")
	@Getter
	@Setter
	private Integer perPage = 10;

	/**
	 * 分页
	 */
	@ApiModelProperty(value = "页号，从1开始")
	@Getter
	@Setter
	private Integer page = 1;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序参数")
	@Getter
	@Setter
	private String sort;

	public Page toPageParam() {
		Page page = new Page(this.page, this.perPage);

		if (StringUtils.isBlank(sort)) {
			return page;
		}
		boolean asc = true;
		if (StringUtils.startsWith(sort, "-")) {
			sort = StringUtils.substring(sort, 1);
			asc = false;
		}

		if (StringUtils.isNotEmpty(sort) && (sort.trim().contains(" ") || sort.trim().contains(";")
				|| sort.contains("(") || sort.contains(")") || sort.contains("'")
				|| sort.contains("\"") || sort.contains("%") || sort.contains("\\"))) {
			throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
		}

		// 设置排序字段
		OrderItem item = new OrderItem(sort, asc);
		List<OrderItem> orders = Lists.newArrayList();
		orders.add(item);
		page.setOrders(orders);

		return page;
	}

	public void setPer_page(Integer perPage) {
		this.perPage = perPage;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}

