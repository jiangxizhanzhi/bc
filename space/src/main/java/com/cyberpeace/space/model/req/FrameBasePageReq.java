/**
 * 这段代码定义了一个名为 `FrameBasePageReq` 的 Java 类，位于包 `com.cyberpeace.space.model.req` 中。以下是对其功能和结构的简要分析： 
 * 1. **类继承与注解**： 
 * - 该类继承自 `PageReqBase`，表明它可能用于分页请求。 
 * - 使用了 `@ApiModel` 注解，通常用于描述 API 模型对象，便于生成文档。 
 * 2. **依赖的外部库**： 
 * - 引用了 `com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper`，用于构建 MyBatis-Plus 的查询条件。 
 * - 使用了 `org.apache.commons.compress.utils.Lists` 来创建列表。 
 * 3. **方法功能**： 
 * - `req2QueryWrapper()`：返回一个 `LambdaQueryWrapper` 对象，但当前实现返回 `null`，需要进一步实现以提供具体逻辑。 
 * - `getUpperId()` 和 `getUpperIds()`：分别返回单个字符串和字符串列表，当前实现均返回默认值（`null` 和空列表）。 
 * 4. **用途**： 
 * - 这是一个请求模型类，主要用于封装分页请求数据，并可能结合 MyBatis-Plus 构建动态查询条件。 
 * 总结：这是一个基础的分页请求模型类，需补充具体业务逻辑以满足实际需求。 
**/
package com.cyberpeace.space.model.req;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@ApiModel
public class FrameBasePageReq extends PageReqBase {

	public LambdaQueryWrapper req2QueryWrapper() {
		return null;
	}

	public String getUpperId() {
		return null;
	}

	public List<String> getUpperIds() {
		return Lists.newArrayList();
	}
}
