package com.cyberpeace.cr.crbase.service.context.effect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势动效新增请求体
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class SituationEffectAddReq {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 图标
	 */
	@ApiModelProperty(value = "缩略图")
	private String logo;

	/**
	 * 类别id
	 */
	@ApiModelProperty(value = "分类id")
	private String categoryId;

	/**
	 * 文件
	 */
	@ApiModelProperty(value = "动效文件")
	private String file;
}
