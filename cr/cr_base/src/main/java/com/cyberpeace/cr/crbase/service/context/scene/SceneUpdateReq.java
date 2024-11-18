package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.context.role.RoleUpdateConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneUpdateReq implements Serializable {

	/**
	 * 中台场景id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "场景id")
	private String mpSceneId;

	/**
	 * RED 角色
	 *
	 * @see List<RoleUpdateConfig>
	 */
	@ApiModelProperty(value = "红方角色")
	private List<RoleUpdateConfig> redRoles;

	/**
	 * 蓝色角色
	 *
	 * @see List<RoleUpdateConfig>
	 */
	@ApiModelProperty(value = "蓝方角色")
	private List<RoleUpdateConfig> blueRoles;

	/**
	 * 角色列表
	 *
	 * @see List<RoleUpdateConfig>
	 */
	@ApiModelProperty(value = "团队训练角色")
	private List<RoleUpdateConfig> roleList;

	/**
	 * 更新场景
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty(value = "动态更新的场景数据")
	private JSONObject updateScene;
}
