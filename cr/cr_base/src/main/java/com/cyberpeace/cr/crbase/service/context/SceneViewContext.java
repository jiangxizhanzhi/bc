package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景展示模型
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class SceneViewContext implements Serializable {

	/**
	 * 房间id
	 */
	@ApiModelProperty("房间id")
	private String roomId;

	/**
	 * 当地场景id
	 */
	@ApiModelProperty("场景管理器id")
	private String localSceneId;

	/**
	 * 中台场景id
	 */
	@ApiModelProperty("场景id")
	private String mpSceneId;

	/**
	 * 拓扑id
	 */
	@ApiModelProperty("拓扑id")
	private String topoId;

	/**
	 * 拓扑数据
	 */
	@ApiModelProperty("拓扑模板数据")
	private JSONObject topoData;

	/**
	 * 场景数据
	 */
	@ApiModelProperty("场景数据")
	private JSONObject sceneData;
}
