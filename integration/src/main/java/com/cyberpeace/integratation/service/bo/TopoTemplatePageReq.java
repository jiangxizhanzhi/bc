/**
 * 这段代码定义了一个名为 `TopoTemplatePageReq` 的 Java 类，属于包 `com.cyberpeace.integratation.service.bo`。该类主要用于构建一个分页查询请求，包含了一些基本属性和一个生成查询字符串的方法。 
 * ### 主要组成部分： 
 * 1. **引入的库**: 
 * - `org.apache.commons.lang3.StringUtils`: 用于处理字符串操作，特别是检查字符串是否为空或空白。 
 * - `lombok.Getter` 和 `lombok.Setter`: Lombok 注解用来自动生成属性的 getter 和 setter 方法。 
 * 2. **属性**: 
 * - `private String type;`: 用于存储类型信息。 
 * - `private String per_page;`: 用于存储每页显示的条目数量。 
 * - `private String page;`: 用于存储当前页码。 
 * - `private String search;`: 用于存储搜索关键字。 
 * 3. **方法**: 
 * - `@Getter @Setter`: 自动为所有属性生成 getter 和 setter 方法。 
 * - `public String toQuery()`: 这个方法构建了一个查询字符串，包含了 `per_page`, `page`, `type` 和 `search` 参数。如果 `search` 不为空，则将其添加到查询字符串中。 
 * ### 总结： 
 * - 这个类的主要目的是封装一些分页查询相关的参数，并提供一个方便的方法来生成 URL 查询字符串。 
 * - 使用了 Lombok 库来简化代码，减少了样板代码（如 getter 和 setter 方法）。 
 * - 通过 `StringUtils` 确保只有非空的 `search` 参数才会被添加到查询字符串中，这有助于避免不必要的查询参数。 
**/
package com.cyberpeace.integratation.service.bo;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopoTemplatePageReq {

    private String type;

    private String per_page;

    private String page;
    
    private String search;

    public String toQuery() {
        StringBuilder builder = new StringBuilder();
        builder.append("?per_page=").append(this.per_page);
        builder.append("&page=").append(this.page);
        builder.append("&type=").append(this.type);
		if (StringUtils.isNotBlank(this.search)) {
			builder.append("&search=").append(this.search);
		}
        return builder.toString();
    }
}
