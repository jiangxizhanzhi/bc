package com.cyberpeace.oj.ojbase.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 拓扑场景数据Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class TopoSceneDataBo implements Serializable {

	/**
	 * topo数据
	 */
	@ApiModelProperty("topo数据")
	private JSONObject sceneConfigData;

	/**
	 * 场景数据
	 */
	@ApiModelProperty("场景数据")
	private JSONObject sceneData;

}
