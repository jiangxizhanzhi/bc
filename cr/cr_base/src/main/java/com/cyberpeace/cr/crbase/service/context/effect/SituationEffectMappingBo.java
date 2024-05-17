package com.cyberpeace.cr.crbase.service.context.effect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationEffectMappingBo {

	private String resourceId;

	@ApiModelProperty(value = "映射关系名称")
	private String name;

	@ApiModelProperty(value = "流量规则")
	private String rule;

	@ApiModelProperty(value = "源节点动效")
	private String sourceEffect;

	@ApiModelProperty(value = "目标节点动效")
	private String targetEffect;

	@ApiModelProperty(value = "是否聚焦")
	private Boolean focus;

	@ApiModelProperty(value = "线路颜色")
	private Integer color;

	@ApiModelProperty(value = "线路类型")
	private Integer lineType;

	@ApiModelProperty(value = "线宽")
	private Integer lineWidth;
}
