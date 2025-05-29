package com.cyberpeace.space.service.context.tactic;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.space.dao.entity.SpaceTacticAttack;
import com.cyberpeace.space.model.req.SpacePageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间战法分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpacePageReqBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个用于分页查询空间战法的请求类 `SpaceTacticPageReq`，继承自 `SpacePageReqBase`。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于构建分页查询的空间战法请求体，并提供一个方法将请求参数转换为 MyBatis-Plus 的 `LambdaQueryWrapper` 对象。 
 * 2. **主要字段**： 
 * - `category_id`：表示战法分类的 ID。 
 * - `system_id`：表示体系的 ID。 
 * 3. **方法**： 
 * - `req2QueryWrapper()`：重写父类方法，生成并返回一个 `LambdaQueryWrapper` 对象，用于查询条件的封装。 
 * - 默认按更新时间降序排序 (`orderByDesc`)。 
 * - 如果 `pattern` 不为空，则添加模糊匹配名称的条件 (`like`)。 
 * - 如果 `category_id` 或 `system_id` 不为空，则分别添加相等条件 (`eq`)。 
 * 4. **依赖与注解**： 
 * - 使用了 Apache Commons Lang 提供的 `StringUtils` 进行字符串判空操作。 
 * - 使用了 Lombok 注解 (`@Getter`, `@Setter`) 来简化字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 注解 (`@ApiModel`, `@ApiModelProperty`) 来描述模型结构。 
 * 5. **继承关系**： 
 * - 继承自 `SpacePageReqBase`，可能包含一些通用的分页参数或方法。 
 * 总结：这是一个典型的业务层请求类，结合了分页功能和动态查询条件的封装，适合用于复杂的查询场景。 
**/
@ApiModel
public class SpaceTacticPageReq extends SpacePageReqBase {

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "战法分类")
	@Getter
	@Setter
	private String category_id;

	/**
	 * 系统id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "体系id")
	@Getter
	@Setter
	private String system_id;

	/**
	 * req2 查询包装器
	 *
	 * @return Lambda 查询包装器
	 * @throws
	 * @see LambdaQueryWrapper
	 */
	@Override
	public LambdaQueryWrapper req2QueryWrapper() {
		LambdaQueryWrapper<SpaceTacticAttack> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByDesc(SpaceTacticAttack::getUpdateTime);
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceTacticAttack::getName, this.getPattern());
		}
		if (StringUtils.isNotBlank(this.getCategory_id())) {
			wrapper.eq(SpaceTacticAttack::getCategoryId, this.getCategory_id());
		}
		if (StringUtils.isNotBlank(this.getSystem_id())) {
			wrapper.eq(SpaceTacticAttack::getSystemId, this.getSystem_id());
		}
		wrapper.setEntityClass(SpaceTacticAttack.class);
		return wrapper;
	}
}

