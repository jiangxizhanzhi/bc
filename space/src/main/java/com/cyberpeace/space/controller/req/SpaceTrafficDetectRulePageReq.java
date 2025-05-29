package com.cyberpeace.space.controller.req;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.model.req.SpaceToolPageReq;

import io.swagger.annotations.ApiModel;

/**
 * 空间流量检测统治分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceToolPageReq
 * @since 1.0.0
 */
/**
 * 该代码定义了一个名为 `SpaceTrafficDetectRulePageReq` 的类，继承自 `SpaceToolPageReq`，主要用于处理空间流量检测规则的分页请求。以下是代码的主要功能和结构分析： 
 * 1. **类与注解**： 
 * - 类使用了 `@ApiModel` 注解，表明这是一个 API 请求模型类。 
 * - 类名明确表示其用途是处理空间流量检测规则的分页请求。 
 * 2. **方法功能**： 
 * - 方法 `req2QueryWrapper()` 将请求参数转换为 `LambdaQueryWrapper` 对象，用于数据库查询。 
 * - 方法逻辑包括： 
 * - 创建 `LambdaQueryWrapper` 对象。 
 * - 根据更新时间字段按降序排序。 
 * - 如果工具字段不为空，添加模糊匹配条件。 
 * - 如果阶段字段不为空，添加精确匹配条件。 
 * - 设置实体类类型为 `SpaceTrafficDetectRule`。 
 * - 返回构建完成的 `LambdaQueryWrapper` 对象。 
 * 3. **依赖与引用**： 
 * - 使用了 `org.apache.commons.lang3.StringUtils` 进行字符串判空操作。 
 * - 引用了 `com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper` 和相关实体类。 
 * 4. **设计意图**： 
 * - 该类的设计目的是将前端请求参数封装成适合数据库查询的对象，便于后续的数据检索和分页操作。 
 * 总结：此代码通过封装请求参数，生成用于数据库查询的条件对象，实现了从请求到查询的转换，符合 RESTful API 设计规范。 
**/
@ApiModel
public class SpaceTrafficDetectRulePageReq extends SpaceToolPageReq {

	/**
	 * 将请求转换为查询包装器
	 *
	 * 此方法根据请求中的条件生成一个LambdaQueryWrapper对象，用于查询空间流量检测规则
	 * 它首先创建一个LambdaQueryWrapper对象，并根据请求中的条件进行排序、模糊匹配和精确匹配操作
	 * 最后设置实体类并返回查询包装器对象
	 *
	 * @return LambdaQueryWrapper<SpaceTrafficDetectRule> 查询包装器对象，用于执行数据库查询
	 */
	@Override
	public LambdaQueryWrapper<SpaceTrafficDetectRule> req2QueryWrapper() {
	    // 创建LambdaQueryWrapper对象，用于构建查询条件
	    LambdaQueryWrapper<SpaceTrafficDetectRule> wrapper = new LambdaQueryWrapper<>();
	    // 按更新时间降序排序
	    wrapper.orderByDesc(SpaceTrafficDetectRule::getUpdateTime);

	    // 如果工具字段不为空，则添加模糊匹配条件
	    if (StringUtils.isNotBlank(this.getTool())) {
	        wrapper.like(SpaceTrafficDetectRule::getTool, this.getTool());
	    }
	    // 如果阶段字段不为空，则添加精确匹配条件
	    if (null != this.getStage()) {
	        wrapper.eq(SpaceTrafficDetectRule::getStage, this.getStage());
	    }
	    // 设置实体类，指定查询的实体类型
	    wrapper.setEntityClass(SpaceTrafficDetectRule.class);
	    // 返回构建好的查询包装器对象
	    return wrapper;
	}
}

