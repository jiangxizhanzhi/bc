package com.cyberpeace.cr.crbase.service.context.template;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练模板简要信息
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class BusinessTemplateSimple implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("场景名称")
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty("场景类型")
	private Integer type;

	/**
	 * 困难
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;
}
