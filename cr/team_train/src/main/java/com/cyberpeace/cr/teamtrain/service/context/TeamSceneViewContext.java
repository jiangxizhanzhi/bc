package com.cyberpeace.cr.teamtrain.service.context;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队场景视图上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see SceneViewContext
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TeamSceneViewContext extends SceneViewContext implements Serializable {

	/**
	 * 角色列表
	 *
	 * @see List<CrRoleConfig>
	 */
	@ApiModelProperty("场景角色资产配置，用于动态编辑")
	private List<CrRoleConfig> roleList = Lists.newArrayList();
}
