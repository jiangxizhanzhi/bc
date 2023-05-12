package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.context.role.RoleUpdateConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneUpdateReq implements Serializable {

	@ApiModelProperty(value = "场景id")
	private String mpSceneId;

	@ApiModelProperty(value = "红方角色")
	private List<RoleUpdateConfig> redRoles;

	@ApiModelProperty(value = "蓝方角色")
	private List<RoleUpdateConfig> blueRoles;

	@ApiModelProperty(value = "团队训练角色")
	private List<RoleUpdateConfig> roleList;

	@ApiModelProperty(value = "动态更新的场景数据")
	private JSONObject updateScene;
}
