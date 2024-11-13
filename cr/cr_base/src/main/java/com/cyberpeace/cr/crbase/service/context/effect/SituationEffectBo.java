package com.cyberpeace.cr.crbase.service.context.effect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势动效Bo
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class SituationEffectBo {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "id")
	private String resourceId;

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
	@ApiModelProperty(value = "文件")
	private String file;
}
