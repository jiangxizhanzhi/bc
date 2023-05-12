package com.cyberpeace.cs.controller.req.scene;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneUpdateReq implements Serializable {

	@ApiModelProperty(value = "场景id")
	private String mpSceneId;

	@ApiModelProperty(value = "红方角色")
	private List<RoleConfig> redRoles;

	@ApiModelProperty(value = "蓝方角色")
	private List<RoleConfig> blueRoles;

	@ApiModelProperty(value = "团队训练角色")
	private List<RoleConfig> roleList;

	@ApiModelProperty(value = "动态更新的场景数据")
	private JSONObject updateScene;
}
