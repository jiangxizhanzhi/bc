/**
 * 这段代码定义了一个名为 `SpaceActionTargetPageReq` 的类，它继承自 `FrameBasePageReq`，并扩展了分页请求的功能。以下是代码的主要分析： 
 * 1. **类的定义与继承**： 
 * - `SpaceActionTargetPageReq` 是一个用于处理空间行动目标分页请求的类。 
 * - 它继承自 `FrameBasePageReq`，表明它可能包含一些通用的分页参数。 
 * 2. **注解与元数据**： 
 * - 使用了 `@ApiModel` 注解，表明这是一个模型类，通常用于 Swagger 文档生成，描述 API 中的数据模型。 
 * 3. **字段与 getter/setter**： 
 * - 类中有一个私有字段 `frameId`，通过 `@Getter` 和 `@Setter` 注解提供了自动的访问器和修改器。 
 * 4. **方法实现**： 
 * - `req2QueryWrapper()` 方法将请求对象转换为 MyBatis-Plus 的 `LambdaQueryWrapper` 对象，用于数据库查询。如果 `pattern` 不为空白，则会添加一个基于名称的模糊查询条件。 
 * - `getUpperId()` 方法返回 `frameId`，可能是为了支持某种父级 ID 的获取逻辑。 
 * 5. **依赖的其他类**： 
 * - 使用了 `LambdaQueryWrapper` 和 `StringUtils` 来构建查询条件和处理字符串。 
 * - 引用了 `SpaceActionTarget` 实体类和 `FrameBasePageReq` 基础请求类。 
 * 总结：该类主要用于封装空间行动目标的分页请求参数，并提供将请求参数转换为查询条件的功能，适合在基于 MyBatis-Plus 的项目中使用。 
**/
package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceActionTarget;

import com.cyberpeace.space.model.req.FrameBasePageReq;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间行动目标分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see FrameBasePageReq
 * @since 1.0.0
 */
@ApiModel
public class SpaceActionTargetPageReq extends FrameBasePageReq {

	@Getter
	@Setter
	private String frameId;

	public LambdaQueryWrapper<SpaceActionTarget> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceActionTarget> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceActionTarget::getName, this.getPattern());
		}
		return wrapper;
	}

	@Override
	public String getUpperId() {
		return this.frameId;
	}

}