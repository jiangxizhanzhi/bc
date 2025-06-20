package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色任务下发进度
 *
 * @author SN
 * @date 2023/05/15
 */
@Getter
@Setter
public class RoleTaskReleaseProgress extends ReleaseProgress implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 岗位id
	 */
	@ApiModelProperty(value = "角色id")
	private String roleId;

	/**
	 * 岗位名称
	 */
	@ApiModelProperty(value = "角色名称")
	private String roleName;

	/**
	 * 阶段
	 */
	@ApiModelProperty(value = "阶段进度")
	private List<StageTaskReleaseProgress> stage = Lists.newArrayList();
}
