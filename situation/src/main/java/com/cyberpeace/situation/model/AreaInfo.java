package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.dao.entity.SituationConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 区域信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class AreaInfo implements Serializable {

	/**
	 * 区域id
	 *
	 * @see String
	 */
	@ApiModelProperty("区域id")
	private String areaId;

	/**
	 * 区域名称
	 *
	 * @see String
	 */
	@ApiModelProperty("区域名称")
	private String name;

	/**
	 * 区域描述
	 *
	 * @see String
	 */
	@ApiModelProperty("区域描述")
	private String desc;

	/**
	 * 区域形状
	 *
	 * @see String
	 */
	@ApiModelProperty("区域形状")
	private String shape;

	/**
	 * 区域范围
	 *
	 * @see List<PlaneCoordinate>
	 */
	@ApiModelProperty("区域范围")
	private List<PlaneCoordinate> scope;

	/**
	 * 区域状态
	 *
	 * @see String
	 */
	@ApiModelProperty("区域状态")
	private String status;

	/**
	 * 区域颜色
	 *
	 * @see String
	 */
	@ApiModelProperty("区域颜色")
	private String color;

	/**
	 * 区域边框样式
	 *
	 * @see LineStyle
	 */
	@ApiModelProperty("区域边框样式")
	private LineStyle style;

	/**
	 * 区域字体样式
	 *
	 * @see FontStyle
	 */
	@ApiModelProperty("区域字体样式")
	private FontStyle fontStyle;

	/**
	 * 区域动效
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("区域动效")
	private List<String> effect;

	public AreaInfo(JSONObject area) {
		this.areaId = area.getString("id");
		this.desc = area.getString("description");
	}

	public AreaInfo() {}

	public void setConfig(String name,String color) {

		if (name != null) {
			this.name = name;
		}

		if (color != null) {
			this.style.setColor(color);
		}
	}

}
