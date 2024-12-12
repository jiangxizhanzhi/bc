package com.cyberpeace.cr.crbase.service.context.scene;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.context.role.RoleUpdateConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 场景更新请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class SceneUpdateReq implements Serializable {

	/**
	 * 中台场景id
	 */
	@ApiModelProperty(value = "场景id")
	private String mpSceneId;

	/**
	 * RED 角色
	 */
	@ApiModelProperty(value = "红方角色")
	private List<RoleUpdateConfig> redRoles;

	/**
	 * 蓝色角色
	 */
	@ApiModelProperty(value = "蓝方角色")
	private List<RoleUpdateConfig> blueRoles;

	/**
	 * 岗位列表
	 */
	@ApiModelProperty(value = "团队训练角色")
	private List<RoleUpdateConfig> roleList;

	/**
	 * 更新场景
	 */
	@ApiModelProperty(value = "动态更新的场景数据")
	private JSONObject updateScene;
}
