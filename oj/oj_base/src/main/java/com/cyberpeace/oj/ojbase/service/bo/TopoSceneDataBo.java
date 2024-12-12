package com.cyberpeace.oj.ojbase.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 拓扑场景数据博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class TopoSceneDataBo implements Serializable {

	/**
	 * 场景配置数据
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("topo数据")
	private JSONObject sceneConfigData;

	/**
	 * 场景数据
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("场景数据")
	private JSONObject sceneData;

}
