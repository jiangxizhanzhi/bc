package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.dao.entity.SituationConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
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

	@ApiModelProperty("部件层级")
	private Float hierarchy;

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

	public void setConfig(Map<Integer, String> nodeConfig) {

		// 设置部件高度
		String hierarchy = nodeConfig.getOrDefault(SituationConfig.Attribute.HEIGHT.getCode(),
				null);
		if (hierarchy != null) {
			try {
				this.hierarchy = Float.parseFloat(hierarchy);
			} catch (Exception e) {
				log.info(String.format(
						"Fail to get the piece hierarchy of %s, the original value is %s.",
						this.pieceId, hierarchy));
			}
		}
	}

}
