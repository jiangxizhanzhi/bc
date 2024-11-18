package com.cyberpeace.cr.crbase.service.context.template;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练模板简要信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class BusinessTemplateSimple implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("场景名称")
	private String name;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("场景类型")
	private Integer type;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;
}
