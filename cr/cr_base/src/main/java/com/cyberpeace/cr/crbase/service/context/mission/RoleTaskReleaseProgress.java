package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色任务下发进度
 *
 * @author SN
 * @date 2023/05/15
 */
@Data
public class RoleTaskReleaseProgress extends ReleaseProgress implements Serializable {

	/**
	 * 角色id
	 */
	@ApiModelProperty(value = "角色id")
	private String roleId;

	/**
	 * 角色名称
	 */
	@ApiModelProperty(value = "角色名称")
	private String roleName;

	/**
	 * 阶段
	 */
	@ApiModelProperty(value = "阶段进度")
	private List<StageTaskReleaseProgress> stage = Lists.newArrayList();
}
