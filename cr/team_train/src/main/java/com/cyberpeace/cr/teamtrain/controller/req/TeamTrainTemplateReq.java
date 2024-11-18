package com.cyberpeace.cr.teamtrain.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队训练模板请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BasicTemplate
 * @since 1.0.0
 */
@Data
public class TeamTrainTemplateReq extends BasicTemplate {

	/**
	 * 角色列表
	 *
	 * @see List<CrRoleConfig>
	 */
	@ApiModelProperty(value = "角色列表", required = true)
	@NotNull
	private List<CrRoleConfig> roleList;

	/**
	 * 人数
	 *
	 * @see Integer
	 */
	private Integer peopleNumber;

	/**
	 * 时间线配置
	 *
	 * @see Object
	 */
	@ApiModelProperty(value = "时间轴配置", required = true)
	@NotNull
	private Object timeLineConfig = "{}";

}
