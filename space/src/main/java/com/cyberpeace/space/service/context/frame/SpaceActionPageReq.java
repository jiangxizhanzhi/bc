package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceAction;
import com.cyberpeace.space.model.req.FrameBasePageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间行动分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see FrameBasePageReq
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceActionPageReq` 的类，继承自 `FrameBasePageReq`。它主要用于构建与分页查询相关的请求体，并能够将请求参数转换为 MyBatis-Plus 的 `LambdaQueryWrapper` 查询条件。 
 * ### 主要功能分析： 
 * 1. **继承与注解**： 
 * - 继承了 `FrameBasePageReq` 类，可能包含一些通用的分页参数（如 `getPattern()`）。 
 * - 使用了 `@ApiModel` 和 `@ApiModelProperty` 注解，用于生成 Swagger 文档，便于接口文档生成。 
 * 2. **字段定义**： 
 * - `type`：表示空间行动的类型（1为攻击，2为防御），可选值。 
 * - `methodId`：一个字符串列表，存储方法 ID，可能用于关联查询。 
 * 3. **方法实现**： 
 * - `req2QueryWrapper()`：根据当前对象中的属性动态生成 `LambdaQueryWrapper` 对象，支持按名称模糊查询 (`name like`) 和类型精确匹配 (`type eq`)。 
 * - `getUpperIds()`：返回 `methodId` 列表。 
 * 4. **封装性**： 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 提供字段的访问器和修改器，简化代码。 
 * ### 总结： 
 * 该类是一个典型的业务逻辑封装类，用于处理分页请求并生成对应的数据库查询条件。适合在服务层或控制器中使用，提供清晰的接口和良好的扩展性。 
**/
@ApiModel
public class SpaceActionPageReq extends FrameBasePageReq {

	@Getter
	@Setter
	@ApiModelProperty("类型，1攻击2防御")
	private Integer type;

	/**
	 * 方法id
	 */
	@Getter
	@Setter
	@ApiModelProperty(value = "方法id")
	private List<String> methodId;

	public LambdaQueryWrapper<SpaceAction> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceAction> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceAction::getName, this.getPattern());
		}
		if(null != this.type){
			wrapper.eq(SpaceAction::getType, this.type);
			wrapper.setEntityClass(SpaceAction.class);
		}
		return wrapper;
	}

	public List<String> getUpperIds() {
		return this.methodId;
	}
}
