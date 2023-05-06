package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * 场景展示模型
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class SceneViewContext implements Serializable {

	@ApiModelProperty("房间id")
	private String roomId;

	@ApiModelProperty("场景管理器id")
	private String localSceneId;

	@ApiModelProperty("场景id")
	private String mpSceneId;

	@ApiModelProperty("拓扑id")
	private String topoId;

	@ApiModelProperty("拓扑模板数据")
	private JSONObject topoData;

	@ApiModelProperty("场景数据")
	private JSONObject sceneData;
}
