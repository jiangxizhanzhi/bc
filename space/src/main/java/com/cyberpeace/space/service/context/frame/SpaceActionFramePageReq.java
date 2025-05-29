package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.model.req.FrameBasePageReq;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间行动框架分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see FrameBasePageReq
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个用于分页查询的空间行动框架请求类 `SpaceActionFramePageReq`，继承自 `FrameBasePageReq`。以下是代码的主要分析： 
 * 1. **类与注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModel` 注解，表明这是一个 API 模型类。 
 * 2. **属性**： 
 * - `type`、`role` 和 `category_id` 是该类的三个主要属性，分别表示类型、角色和类别ID。 
 * - 这些属性通过 Lombok 注解自动提供了访问器方法。 
 * 3. **方法**： 
 * - `req2QueryWrapper()` 方法将请求参数转换为 MyBatis-Plus 的 `LambdaQueryWrapper` 对象，用于数据库查询。 
 * - 根据不同的属性值，动态地添加查询条件（如等于、模糊匹配等）。 
 * - 最后设置实体类为 `SpaceActionFrame` 并返回查询包装器。 
 * 4. **功能**： 
 * - 该类主要用于构建基于空间行动框架的数据查询条件，支持多种过滤条件的组合查询。 
 * 总体来说，这段代码是一个典型的用于封装查询条件的 Java Bean，结合了 Lombok 和 MyBatis-Plus 的特性，便于在服务层进行灵活的数据查询操作。 
**/
@ApiModel
public class SpaceActionFramePageReq extends FrameBasePageReq {

	@Getter
	@Setter
	private Integer type;

	@Getter
	@Setter
	private Integer role;

	@Getter
	@Setter
	private String category_id;
	public LambdaQueryWrapper<SpaceActionFrame> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceActionFrame> wrapper = new LambdaQueryWrapper<>();
		if (null != this.type) {
			wrapper.eq(SpaceActionFrame::getType, this.type);
		}

		if (null != this.role) {
			wrapper.eq(SpaceActionFrame::getRole, this.role);
		}
		if (null != this.category_id) {
			wrapper.eq(SpaceActionFrame::getCategoryId, this.category_id);
		}
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceActionFrame::getName, this.getPattern());
		}
		wrapper.setEntityClass(SpaceActionFrame.class);
		return wrapper;
	}

}
