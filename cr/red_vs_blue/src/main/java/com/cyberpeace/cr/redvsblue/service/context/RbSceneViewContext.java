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
 * @version 1.0.0
 * @date 2024/11/07
 * @see SceneViewContext
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RbSceneViewContext extends SceneViewContext implements Serializable {

    /**
     * RED 角色
     *
     * @see List<CrRoleConfig>
     */
    @ApiModelProperty(value = "红方角色")
	private List<CrRoleConfig> redRoles;

    /**
     * 蓝色角色
     *
     * @see List<CrRoleConfig>
     */
    @ApiModelProperty(value = "蓝方角色")
	private List<CrRoleConfig> blueRoles;
}
