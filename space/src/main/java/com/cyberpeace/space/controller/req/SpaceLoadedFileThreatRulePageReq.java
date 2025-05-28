/**
 * 这段代码定义了一个用于分页查询的空间加载文件威胁规则请求体类 `SpaceLoadedFileThreatRulePageReq`，继承自 `SpaceToolPageReq`。以下是代码的主要功能和结构分析： 
 * ### 主要功能 
 * 1. **类的功能**： 
 * - 该类用于封装分页查询空间加载文件威胁规则所需的各种条件。 
 * - 提供了一个方法 `req2QueryWrapper()`，将请求参数转换为 `LambdaQueryWrapper` 对象，用于数据库查询。 
 * 2. **核心方法**： 
 * - `req2QueryWrapper()`：根据请求中的条件（如模式、工具、类型、阶段等）动态构造查询条件，并返回 `LambdaQueryWrapper` 对象。支持模糊查询和精确查询，并按照更新时间降序排序。 
 * 3. **字段说明**： 
 * - `type`：表示威胁规则的类型，支持模糊查询。 
 * 4. **依赖的其他类**： 
 * - `SpaceLoadedFileThreatRule`：威胁规则实体类。 
 * - `SpaceToolPageReq`：分页请求基类。 
 * - `LambdaQueryWrapper`：MyBatis-Plus 提供的查询包装器。 
 * - `StringUtils`：字符串工具类，用于判断字符串是否为空。 
 * ### 总结 
 * 该类的主要目的是提供一个灵活的分页查询请求体，能够根据不同的查询条件动态构造查询语句。通过继承 `SpaceToolPageReq` 和实现 `req2QueryWrapper()` 方法，实现了从请求参数到数据库查询条件的映射，具有良好的扩展性和可维护性。 
**/
package com.cyberpeace.space.controller.req;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.model.req.SpaceToolPageReq;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间加载文件威胁统治分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceToolPageReq
 * @since 1.0.0
 */
@ApiModel
public class SpaceLoadedFileThreatRulePageReq extends SpaceToolPageReq {

	@Getter
	@Setter
	private String type;

	/**
	 * 将请求转换为查询包装器
	 *
	 * 此方法根据请求中的条件构建一个LambdaQueryWrapper对象，用于查询空间加载文件威胁规则
	 * 它会检查请求中的模式、工具、类型和阶段字段，并根据这些条件生成相应的查询条件
	 * 最后，它还会设置查询结果的排序方式和实体类类型
	 *
	 * @return LambdaQueryWrapper<SpaceLoadedFileThreatRule> 返回构建好的查询包装器对象
	 */
	@Override
	public LambdaQueryWrapper<SpaceLoadedFileThreatRule> req2QueryWrapper() {
	    // 创建一个新的LambdaQueryWrapper对象
	    LambdaQueryWrapper<SpaceLoadedFileThreatRule> wrapper = new LambdaQueryWrapper<>();

	    // 如果模式字段不为空，则添加模糊查询条件
	    if (StringUtils.isNotBlank(this.getPattern())) {
	        wrapper.like(SpaceLoadedFileThreatRule::getName, this.getPattern());
	    }

	    // 如果工具字段不为空，则添加模糊查询条件
	    if (StringUtils.isNotBlank(this.getTool())) {
	        wrapper.like(SpaceLoadedFileThreatRule::getTool, this.getTool());
	    }

	    // 如果类型字段不为空，则添加模糊查询条件
	    if (StringUtils.isNotBlank(this.getType())) {
	        wrapper.like(SpaceLoadedFileThreatRule::getType, this.getType());
	    }

	    // 如果阶段字段不为空，则添加精确查询条件
	    if (null != this.getStage()) {
	        wrapper.eq(SpaceLoadedFileThreatRule::getStage, this.getStage());
	    }

	    // 设置查询结果按更新时间降序排序
	    wrapper.orderByDesc(SpaceLoadedFileThreatRule::getUpdateTime);

	    // 设置查询器的实体类类型
	    wrapper.setEntityClass(SpaceLoadedFileThreatRule.class);

	    // 返回构建好的查询包装器对象
	    return wrapper;
	}
}
