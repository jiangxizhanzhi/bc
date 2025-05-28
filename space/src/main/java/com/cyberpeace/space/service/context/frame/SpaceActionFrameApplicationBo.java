
package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceActionFrameApplication;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间行动框架行为依赖Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceActionFrameApplicationBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "红蓝对抗业务名称")
	private String rvbName;

	@ApiModelProperty(value = "红蓝对抗业务id")
	private String rvbId;

	@ApiModelProperty(value = "框架id")
	private String frameId;

	@ApiModelProperty(value = "框架名称")
	private String name;

	@ApiModelProperty(value = "分类，面向流程，面向目标")
	private Integer type;

	@ApiModelProperty(value = "类型id")
	private String categoryId;

	@ApiModelProperty(value = "应用场景")
	private List<String> tag;

	@ApiModelProperty(value = "使用说明")
	private String description;

	@ApiModelProperty(value = "攻防动作解析")
	private String analyze;

	@ApiModelProperty(value = "框架编排")
	private ActionFrameArrangeBo frame;

	public SpaceActionFrameApplication bo2Entity() {
		SpaceActionFrameApplication entity = BeanUtils.copy(this,
				SpaceActionFrameApplication.class);
		entity.setTag(JsonUtil.arrayToString(this.tag));
		entity.setFrame(JsonUtil.toJsonString(this.frame));
		return entity;
	}

}
