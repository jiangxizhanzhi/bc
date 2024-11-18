package com.cyberpeace.cr.teamtrain.service.context;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队训练模板
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BasicTemplate
 * @since 1.0.0
 */
@Data
public class TeamTrainTemplateContext extends BasicTemplate {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private String resourceId;

	/**
	 * 角色列表
	 *
	 * @see String
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private String roleList;

	/**
	 * 时间线配置
	 *
	 * @see Object
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private Object timeLineConfig = "{}";

	/**
	 * 人数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(required = false)
	private Integer peopleNumber;
}
