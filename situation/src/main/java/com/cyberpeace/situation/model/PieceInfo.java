package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 件数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class PieceInfo implements Serializable {

	/**
	 * 部件所属区域
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("部件所属区域")
	private List<String> areas;

	/**
	 * 部件名称
	 *
	 * @see String
	 */
	@ApiModelProperty("部件名称")
	private String name;

	/**
	 * 部件描述
	 *
	 * @see String
	 */
	@ApiModelProperty("部件描述")
	private String description;

	/**
	 * 部件id
	 *
	 * @see String
	 */
	@ApiModelProperty("部件id")
	private String pieceId;

	/**
	 * 部件类型
	 *
	 * @see String
	 */
	@ApiModelProperty("部件类型")
	private String type;

	/**
	 * 部件坐标
	 *
	 * @see PlaneCoordinate
	 */
	@ApiModelProperty("部件坐标")
	private PlaneCoordinate position;

	public void setPosition2(String positionStr) {
		JSONObject jsonObject = JsonUtil.toJsonObject(positionStr, JSONObject.class);
		Float x = jsonObject.getFloat("x");
		Float y = jsonObject.getFloat("y");
		this.position = new PlaneCoordinate(x, y);
	}

	public void setId(String id) {
		this.pieceId = id;
	}

}
