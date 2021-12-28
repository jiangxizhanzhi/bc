package com.cyberpeace.oj.ojbase.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class TopoSceneDataBo implements Serializable {

	@ApiModelProperty("topo数据")
	private JSONObject sceneConfigData;

	@ApiModelProperty("场景数据")
	private JSONObject sceneData;

}
