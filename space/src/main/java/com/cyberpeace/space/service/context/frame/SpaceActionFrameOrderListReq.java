/**
 * 这段代码定义了一个名为 `SpaceActionFrameOrderListReq` 的 Java 类，用于处理与空间行动框架次序相关的请求。以下是对代码的简要分析： 
 * ### 类的主要功能： 
 * - **继承自 `SpacePageReqBase`**：该类扩展了 `SpacePageReqBase`，可能包含分页相关的基本属性和方法。 
 * - **API 模型注解**：使用了 `@ApiModel` 和 `@ApiModelProperty` 注解，表明这是一个 API 请求模型类，便于生成 Swagger 文档。 
 * ### 属性说明： 
 * - `rvb_id`：红蓝对抗 ID，必填字段。 
 * - `user_id`：当前用户的 ID，必填字段。 
 * - `status`：任务完成状态（1 表示已完成，2 表示未完成）。 
 * ### 方法： 
 * - `req2QueryWrapper()`：将请求对象转换为 MyBatis-Plus 的查询包装器 (`LambdaQueryWrapper`)。此方法根据请求参数动态构建查询条件： 
 * - 默认按 `id` 升序排序。 
 * - 如果 `status` 不为空，则添加状态过滤条件。 
 * - 如果 `rvb_id` 不为空，则添加红蓝对抗 ID 过滤条件。 
 * - 必须提供 `user_id` 条件。 
 * ### 总结： 
 * 该类主要用于封装空间行动框架次序列表的请求数据，并提供将请求数据转换为查询条件的功能。它结合了 MyBatis-Plus 的查询能力，支持灵活的条件过滤和排序。 
**/
package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceActionFrameOrder;
import com.cyberpeace.space.model.req.SpacePageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间行动框架次序列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpacePageReqBase
 * @since 1.0.0
 */
@ApiModel
public class SpaceActionFrameOrderListReq extends SpacePageReqBase {

	@ApiModelProperty(value = "红蓝对抗id，必传")
	@Getter
	@Setter
	private String rvb_id;

	@ApiModelProperty(value = "当前用户id,必传")
	@Getter
	@Setter
	private String user_id;

	@ApiModelProperty(value = "完成状态，完成1，未完成2")
	@Getter
	@Setter
	private Integer status;

	@Override
	public LambdaQueryWrapper req2QueryWrapper() {
		LambdaQueryWrapper<SpaceActionFrameOrder> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByAsc(SpaceActionFrameOrder::getId);
		if (null != this.status) {
			wrapper.eq(SpaceActionFrameOrder::getStatus, this.status);
		}
		if (StringUtils.isNotBlank(this.rvb_id)) {
			wrapper.eq(SpaceActionFrameOrder::getRvbId, this.rvb_id);
		}
		wrapper.eq(SpaceActionFrameOrder::getUserId, this.user_id);
		wrapper.setEntityClass(SpaceActionFrameOrder.class);
		return wrapper;
	}
}
