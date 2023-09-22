package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.dao.entity.SituationConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AreaInfo implements Serializable {

	@ApiModelProperty("区域id")
	private String areaId;

	@ApiModelProperty("区域名称")
	private String name;

	@ApiModelProperty("区域描述")
	private String desc;

	@ApiModelProperty("区域形状")
	private String shape;

	@ApiModelProperty("区域范围")
	private List<PlaneCoordinate> scope;

	@ApiModelProperty("区域状态")
	private String status;

	@ApiModelProperty("区域颜色")
	private String color;

	@ApiModelProperty("区域边框样式")
	private LineStyle style;

	@ApiModelProperty("区域字体样式")
	private FontStyle fontStyle;

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
