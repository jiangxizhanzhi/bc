/**
 * 这段代码定义了一个名为 `SpaceActionMethodPageReq` 的类，继承自 `FrameBasePageReq`，主要用于生成针对 `SpaceActionMethod` 实体对象的分页查询条件。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类用于封装分页请求参数，并提供将请求参数转换为 MyBatis-Plus 查询包装器 (`LambdaQueryWrapper`) 的功能。 
 * - 它扩展了 `FrameBasePageReq` 类，可能包含通用的分页相关属性。 
 * 2. **主要成员**： 
 * - `targetId`：一个可选的字符串列表字段，表示目标 ID 列表。 
 * - 提供了 `getTargetId` 和 `setTargetId` 方法来操作该字段。 
 * - `req2QueryWrapper()` 方法：根据当前对象的属性（如 `pattern`）构建并返回一个 `LambdaQueryWrapper` 对象，用于数据库查询。 
 * - 覆写了 `getUpperIds()` 方法，返回 `targetId` 属性值。 
 * 3. **依赖与工具**： 
 * - 使用了 MyBatis-Plus 的 `LambdaQueryWrapper` 进行动态 SQL 构建。 
 * - 引入了 `StringUtils` 工具类用于非空检查。 
 * - 借助 Lombok 的 `@Getter` 和 `@Setter` 注解简化了属性的访问器和修改器生成。 
 * 4. **注释与文档**： 
 * - 通过 `@ApiModel` 注解表明这是一个 API 请求模型类。 
 * - 提供了详细的类描述和注释，便于理解其用途及上下文。 
 * 总结来说，这段代码实现了一个基于分页查询的请求体类，能够灵活地将用户输入转化为数据库查询条件，同时保持良好的代码结构和可读性。 
**/
package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;
import com.cyberpeace.space.model.req.FrameBasePageReq;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间行动方法分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see FrameBasePageReq
 * @since 1.0.0
 */
@ApiModel
public class SpaceActionMethodPageReq extends FrameBasePageReq {

	@Getter
	@Setter
	private List<String> targetId;

	public LambdaQueryWrapper<SpaceActionMethod> req2QueryWrapper() {
		LambdaQueryWrapper<SpaceActionMethod> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(this.getPattern())) {
			wrapper.like(SpaceActionMethod::getName, this.getPattern());
		}

		return wrapper;
	}

	@Override
	public List<String> getUpperIds() {
		return this.targetId;
	}

}