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
 * @date 2024/11/14
 */
@Data
public class TeamTrainTemplateReq extends BasicTemplate {

	/**
	 * 角色列表
	 */
	@ApiModelProperty(value = "角色列表", required = true)
	@NotNull
	private List<CrRoleConfig> roleList;

	/**
	 * 人数
	 */
	private Integer peopleNumber;

	/**
	 * 时间轴配置
	 */
	@ApiModelProperty(value = "时间轴配置", required = true)
	@NotNull
	private Object timeLineConfig = "{}";

}
