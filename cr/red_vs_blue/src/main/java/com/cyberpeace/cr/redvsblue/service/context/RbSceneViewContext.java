package com.cyberpeace.cr.redvsblue.service.context;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RB场景视图上下文
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RbSceneViewContext extends SceneViewContext implements Serializable {

	/**
	 * RED 角色
	 */
	@ApiModelProperty(value = "红方角色")
	private List<CrRoleConfig> redRoles;

	/**
	 * 蓝色角色
	 */
	@ApiModelProperty(value = "蓝方角色")
	private List<CrRoleConfig> blueRoles;
}
