/**
 * 该代码定义了一个名为 `SpaceActionFrameApplicationListReq` 的类，用于处理空间行动框架行为依赖列表的请求。以下是对其的主要分析： 
 * ### 类的功能 
 * - **继承与扩展**：该类继承自 `SpacePageReqBase`，表明它可能是一个分页请求相关的类。 
 * - **注解说明**：使用了 `@ApiModel` 和 `@ApiModelProperty` 注解，用于生成 API 文档，描述了类和字段的作用。 
 * ### 字段分析 
 * - **rvb_id**: 
 * - 标记为 `@NotNull`，表示这是一个必填字段。 
 * - 用于标识红蓝对抗的 ID。 
 * - **type**: 
 * - 可能的值为 1 或 2，分别代表面向流程或面向目标。 
 * ### 方法分析 
 * - **req2QueryWrapper()**: 
 * - 将请求对象转换为 MyBatis-Plus 的查询包装器 (`LambdaQueryWrapper`)。 
 * - 按照 `id` 升序排序，并根据 `rvb_id` 和 `type` 进行条件过滤。 
 * - 返回一个配置好的查询包装器实例。 
 * ### 总结 
 * 该类主要用于构建基于 `SpaceActionFrameApplication` 实体的查询条件，支持分页和多条件筛选，适用于处理与红蓝对抗相关的数据查询需求。 
**/
package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.space.dao.entity.SpaceActionFrameApplication;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 空间行动框架行为依赖列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpacePageReqBase
 * @since 1.0.0
 */
@ApiModel
public class SpaceActionFrameApplicationListReq extends SpacePageReqBase {

	@ApiModelProperty(value = "红蓝对抗id")
	@Getter
	@Setter
    @NotNull
	private String rvb_id;

	@ApiModelProperty(value = "面向流程1，面向目标2")
	@Getter
	@Setter
	private Integer type;

	@Override
	public LambdaQueryWrapper req2QueryWrapper() {
		LambdaQueryWrapper<SpaceActionFrameApplication> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByAsc(SpaceActionFrameApplication::getId);
		wrapper.eq(SpaceActionFrameApplication::getRvbId, this.getRvb_id());
		if (null != this.getType()) {
			wrapper.eq(SpaceActionFrameApplication::getType, this.getType());
		}
		wrapper.setEntityClass(SpaceActionFrameApplication.class);
		return wrapper;
	}
}
