
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间加载文件威胁统治Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceLoadedFileThreatRuleBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "检测类型")
	private String type;

	@ApiModelProperty(value = "内容简介")
	private String description;

	@ApiModelProperty(value = "内容详情")
	private String content;

	@ApiModelProperty(value = "应用工具")
	private String tool;

	@ApiModelProperty(value = "执行方式, 自动2，手动1")
	private Integer executeType;

	@ApiModelProperty(value = "防御阶段， type=57")
	private Integer stage;

	public SpaceLoadedFileThreatRule bo2Entity() {
		SpaceLoadedFileThreatRule entity = BeanUtils.copy(this, SpaceLoadedFileThreatRule.class);
		return entity;
	}

}
    