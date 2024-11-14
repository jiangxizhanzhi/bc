package com.cyberpeace.cr.teamtrain.service.context;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队训练模板
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TeamTrainTemplateContext extends BasicTemplate {

	/**
	 * 资源id
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private String resourceId;

	/**
	 * 岗位列表
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private String roleList;

	/**
	 * 时间线配置
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private Object timeLineConfig = "{}";

	/**
	 * 人数
	 */
	@ApiModelProperty(required = false)
	private Integer peopleNumber;
}
