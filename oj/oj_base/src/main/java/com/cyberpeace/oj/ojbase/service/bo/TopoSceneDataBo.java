package com.cyberpeace.oj.ojbase.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 拓扑场景数据博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class TopoSceneDataBo implements Serializable {

	/**
	 * 场景配置数据
	 */
	@ApiModelProperty("topo数据")
	private JSONObject sceneConfigData;

	/**
	 * 场景数据
	 */
	@ApiModelProperty("场景数据")
	private JSONObject sceneData;

}
