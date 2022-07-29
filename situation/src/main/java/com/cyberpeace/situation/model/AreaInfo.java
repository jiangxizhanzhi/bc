package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.dao.entity.SituationConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
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

	@ApiModelProperty("区域样式")
	private LineStyle style;

	@ApiModelProperty("区域动效")
	private List<String> effect;

	@ApiModelProperty("区域层级")
	private Float hierarchy = 0.0f;

	public AreaInfo(JSONObject area) {
		this.areaId = area.getString("id");
		this.desc = area.getString("description");
	}

	public AreaInfo() {}

	public void setConfig(Map<Integer, String> nodeConfig) {

		// 设置区域名称
		String name = nodeConfig.getOrDefault(SituationConfig.Attribute.NAME.getCode(), null);
		if (name != null) {
			this.name = name;
		}

		// 设置区域颜色
		String color = nodeConfig.getOrDefault(SituationConfig.Attribute.COLOR.getCode(), null);
		if (color != null) {
			this.style.setColor(color);
		}

		// 设置区域高度
		String hierarchy = nodeConfig.getOrDefault(SituationConfig.Attribute.HEIGHT.getCode(),
				null);
		if (hierarchy != null) {
			try {
				this.hierarchy = Float.parseFloat(hierarchy);
			} catch (Exception e) {
				log.info(String.format(
						"Fail to get the area hierarchy of %s, the original value is %s.",
						this.areaId, hierarchy));
			}
		}
	}

}
