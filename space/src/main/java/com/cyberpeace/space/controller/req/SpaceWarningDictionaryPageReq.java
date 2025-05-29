package com.cyberpeace.space.controller.req;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.space.dao.entity.SpaceWarningDictionary;
import com.cyberpeace.space.model.req.SpaceToolPageReq;

import io.swagger.annotations.ApiModel;

/**
 * 空间警告字典分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceToolPageReq
 * @since 1.0.0
 */
/**
 * 该代码定义了一个名为 `SpaceWarningDictionaryPageReq` 的类，它是 `SpaceToolPageReq` 的子类。此类的主要功能是提供一种将请求参数转换为数据库查询条件的方法。 
 * ### 主要功能： 
 * 1. **继承与注解**： 
 * - 继承自 `SpaceToolPageReq`，表明它扩展了基本的分页请求功能。 
 * - 使用了 `@ApiModel` 注解，表示这是一个模型类，通常用于描述 API 请求或响应的数据结构。 
 * 2. **核心方法**： 
 * - 方法 `req2QueryWrapper()` 是该类的核心逻辑所在。它接收用户输入的请求参数，并将其转化为适合查询数据库的条件。 
 * 3. **具体实现**： 
 * - 创建了一个 `LambdaQueryWrapper` 对象来构建查询条件。 
 * - 默认按照 `update_time` 字段降序排列结果。 
 * - 如果请求中包含有效的 `pattern` 参数，则在 `name` 字段上添加模糊匹配条件。 
 * - 如果请求中包含有效的 `tool` 参数，则在 `tool_type` 字段上添加模糊匹配条件。 
 * - 最后设置了实体类为 `SpaceWarningDictionary`，明确了查询的目标表。 
 * 4. **设计目的**： 
 * - 这个类的设计目的是为了简化从HTTP请求到数据库查询之间的转换过程，使得开发者能够更方便地处理复杂的查询需求，同时保持代码的可读性和维护性。 
 * ### 总结： 
 * 这段代码展示了如何通过Java类来封装业务逻辑，特别是关于如何将前端传来的参数转化为适合后端数据库操作的对象。这种方法不仅提高了开发效率，还增强了系统的灵活性和可扩展性。 
**/
@ApiModel
public class SpaceWarningDictionaryPageReq extends SpaceToolPageReq {

	/**
	 * 将请求转换为SpaceWarningDictionary表的查询包装器
	 *
	 * 此方法用于根据当前请求参数构建一个LambdaQueryWrapper对象，以便进行SpaceWarningDictionary表的查询
	 * 它会根据请求中的参数，添加相应的查询条件和排序规则
	 *
	 * @return LambdaQueryWrapper<SpaceWarningDictionary>: 返回一个LambdaQueryWrapper对象，包含查询条件和排序规则
	 */
	@Override
	public LambdaQueryWrapper<SpaceWarningDictionary> req2QueryWrapper() {
	    // 创建一个LambdaQueryWrapper对象，用于构建查询条件
	    LambdaQueryWrapper<SpaceWarningDictionary> wrapper = new LambdaQueryWrapper<>();
	    // 添加排序规则，根据更新时间降序排序
	    wrapper.orderByDesc(SpaceWarningDictionary::getUpdateTime);

	    // 如果模式（名称）参数不为空，则添加模糊查询条件
	    if (StringUtils.isNotBlank(this.getPattern())) {
	        wrapper.like(SpaceWarningDictionary::getName, this.getPattern());
	    }

	    // 如果工具类型参数不为空，则添加模糊查询条件
	    if (StringUtils.isNotBlank(this.getTool())) {
	        wrapper.like(SpaceWarningDictionary::getToolType, this.getTool());
	    }

	    // 设置实体类，指定查询的表
	    wrapper.setEntityClass(SpaceWarningDictionary.class);

	    // 返回构建好的查询包装器对象
	    return wrapper;
	}

}

