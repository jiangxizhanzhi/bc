/**
 * 这段代码定义了一个名为 `TopoNodeListReq` 的 Java 类，用于表示拓扑节点列表请求体。以下是对该类的简要分析： 
 * ### 主要功能 
 * - 该类实现了 `Serializable` 接口，因此它可以被序列化。 
 * - 它包含了拓扑节点的一些属性，如 `id`、`category`、`name` 和 `isAll`。 
 * - 提供了根据这些属性生成查询字符串的方法 `getQuery` 和 `toUrl`。 
 * ### 属性 
 * - `id`: 拓扑节点的唯一标识符。 
 * - `category`: 节点的类别。 
 * - `name`: 节点的名称。 
 * - `isAll`: 表示是否展示简易型。 
 * ### 方法 
 * 1. **`protected List<String> getQuery()`** 
 * - 功能：根据对象的属性生成查询参数列表。 
 * - 逻辑： 
 * - 如果 `name` 不为空，则添加 `"name=" + this.name` 到查询列表。 
 * - 如果 `category` 不为 `null`，则添加 `"category=" + this.category` 到查询列表。 
 * - 根据 `isAll` 的值决定是否添加 `"isAll=true"` 或 `"isAll=false"`。 
 * 2. **`public String toUrl()`** 
 * - 功能：生成包含 `id` 和查询参数的 URL 字符串。 
 * - 逻辑： 
 * - 首先检查 `id` 是否为空，如果为空则抛出 `BusinessException`。 
 * - 调用 `getQuery` 方法获取查询参数列表。 
 * - 如果查询参数列表为空，则返回 `id`。 
 * - 否则，返回格式化的 URL 字符串 `id + "?" + String.join("&", queries)`。 
 * ### 日志和异常处理 
 * - 使用 `log.error` 记录错误信息。 
 * - 抛出 `BusinessException` 异常，如果 `id` 为空。 
 * ### 其他 
 * - 使用了 Lombok 注解简化代码，如 `@Getter` 和 `@Setter` 自动提供 getter 和 setter 方法。 
 * - 使用了 Apache Commons Lang 和 Apache Commons Compress 库来处理字符串和列表。 
 * ### 总结 
 * 这个类主要用于封装拓扑节点的信息，并能够根据这些信息生成一个 URL 字符串。它通过 Lombok 简化了代码，并使用了日志记录和异常处理来确保系统的健壮性。 
**/
package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 拓扑节点列表请求体
 *
 * @author SN
 * @date 2023/05/30
 */
@Getter
@Setter
@Slf4j
public class TopoNodeListReq implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * 类别
	 */
	private Integer category;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 是否展示简易型
	 */
	private Boolean isAll;

	protected List<String> getQuery() {
		List<String> queries = Lists.newArrayList();
		if (StringUtils.isNotBlank(name)) {
			queries.add("name=" + this.name);
		}
		if (null != category) {
			queries.add("category=" + this.category);
		}
		// 不传isAll默认不展示简易型
		if (Boolean.TRUE.equals(this.isAll)) {
			queries.add("isAll=true");
		} else {
			queries.add("isAll=false");
		}
		return queries;
	}

	public String toUrl() {
		if (StringUtils.isBlank(this.id)) {
			log.error("blank id for scene or topo");
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		List<String> queries = getQuery();
		if (queries.isEmpty()) {
			return id;
		} else {
			return id + "?" + String.join("&", queries);
		}
	}
}
