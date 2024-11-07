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
 * @date 2024/11/07
 */
@Data
public class TeamSceneViewContext extends SceneViewContext implements Serializable {

	/**
	 * 角色列表
	 */
	@ApiModelProperty("场景角色资产配置，用于动态编辑")
	private List<CrRoleConfig> roleList = Lists.newArrayList();
}
