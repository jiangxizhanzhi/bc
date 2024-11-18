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
 * @version 1.0.0
 * @date 2023/05/15
 * @see ReleaseProgress
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RoleTaskReleaseProgress extends ReleaseProgress implements Serializable {

	/**
	 * 角色id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "角色id")
	private String roleId;

	/**
	 * 角色名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "角色名称")
	private String roleName;

	/**
	 * 阶段
	 *
	 * @see List<StageTaskReleaseProgress>
	 */
	@ApiModelProperty(value = "阶段进度")
	private List<StageTaskReleaseProgress> stage = Lists.newArrayList();
}
