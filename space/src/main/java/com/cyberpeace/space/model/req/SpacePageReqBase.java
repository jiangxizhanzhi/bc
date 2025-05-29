package com.cyberpeace.space.model.req;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.space.model.SpaceBaseTable;

import io.swagger.annotations.ApiModel;

/**
 * 这段代码定义了一个名为 `SpacePageReqBase` 的类，继承自 `PageReqBase`，并主要用于生成基于条件查询的 `LambdaQueryWrapper` 对象。以下是代码的主要分析和总结： 
 * ### 主要功能 
 * 1. **继承与扩展**： 
 * - 该类继承了 `PageReqBase`，通常 `PageReqBase` 是一个分页请求基类。 
 * - 它实现了 `req2QueryWrapper()` 方法，用于将请求参数转换为 MyBatis-Plus 的 `LambdaQueryWrapper` 查询条件。 
 * 2. **核心方法**： 
 * - **`req2QueryWrapper()`**：此方法构建并返回一个 `LambdaQueryWrapper` 对象，用于数据库查询。 
 * - 默认按 `SpaceBaseTable` 表中的 `updateTime` 字段降序排序。 
 * - 如果 `this.getPattern()` 不为空，则添加模糊查询条件，匹配 `name` 字段。 
 * - 设置查询实体类为 `SpaceBaseTable`。 
 * 3. **依赖库**： 
 * - 使用了 Apache Commons Lang 的 `StringUtils` 类来判断字符串是否为空。 
 * - 引入了 MyBatis-Plus 的 `LambdaQueryWrapper`，用于构造动态 SQL 查询。 
 * - 使用了 Swagger 注解 `@ApiModel`，表示这是一个 API 模型类。 
 * 4. **设计目的**： 
 * - 该类旨在提供一个通用的分页查询封装，便于快速生成符合特定条件的查询逻辑。 
 * ### 总结 
 * - 这是一个典型的业务层请求对象，结合了分页、条件查询和动态 SQL 构建的功能。 
 * - 通过继承和方法实现，简化了查询逻辑的复用性，适合在复杂的分页查询场景中使用。 
**/
@ApiModel
public class SpacePageReqBase extends PageReqBase {

	public LambdaQueryWrapper req2QueryWrapper() {
		LambdaQueryWrapper<SpaceBaseTable> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByDesc(SpaceBaseTable::getUpdateTime);
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceBaseTable::getName, this.getPattern());
		}
		wrapper.setEntityClass(SpaceBaseTable.class);
		return wrapper;
	}
}

