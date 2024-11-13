package com.cyberpeace.cr.crbase.service.context.effect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势动效映射新增请求体
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class SituationEffectMappingAddReq {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "映射关系名称")
	private String name;

	/**
	 * 统治
	 */
	@ApiModelProperty(value = "流量规则")
	private String rule;

	/**
	 * 源动效
	 */
	@ApiModelProperty(value = "源节点动效")
	private String sourceEffect;

	/**
	 * 目标动效
	 */
	@ApiModelProperty(value = "目标节点动效")
	private String targetEffect;

	/**
	 * 重点
	 */
	@ApiModelProperty(value = "是否聚焦")
	private Boolean focus;

	/**
	 * 颜色
	 */
	@ApiModelProperty(value = "线路颜色")
	private Integer color;

	/**
	 * 线路类型
	 */
	@ApiModelProperty(value = "线路类型")
	private Integer lineType;

	/**
	 * 线宽
	 */
	@ApiModelProperty(value = "线宽")
	private Integer lineWidth;
}
