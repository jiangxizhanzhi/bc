package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PieceInfo implements Serializable {

	@ApiModelProperty("部件所属区域")
	private List<String> areas;

	@ApiModelProperty("部件名称")
	private String name;

	@ApiModelProperty("部件描述")
	private String description;

	@ApiModelProperty("部件id")
	private String pieceId;

	@ApiModelProperty("部件类型")
	private String type;

	@ApiModelProperty("部件坐标")
	private PlaneCoordinate position;

	public void setPosition2(String positionStr) {
		JSONObject position = JsonUtil.toJsonObject(positionStr, JSONObject.class);
		Float x = position.getFloat("x");
		Float y = position.getFloat("y");
		this.position = new PlaneCoordinate(x, y);
	}

	public void setId(String id) {
		this.pieceId = id;
	}

}
