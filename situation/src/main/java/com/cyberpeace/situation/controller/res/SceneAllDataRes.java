package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.model.AverageSceneData;
import com.cyberpeace.situation.model.TotalSceneData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SceneAllDataRes implements Serializable {

	@ApiModelProperty("所有链路数据")
	private List<LinkStateRes> linkStateList;

	@ApiModelProperty("所有节点数据")
	private List<ElementStateRes> elementStateList;

	@ApiModelProperty("总和数据")
	private TotalSceneData totalSceneData;

	@ApiModelProperty("平均数据")
	private AverageSceneData averageSceneData;
}
