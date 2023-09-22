package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.dao.entity.SituationConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class ElementInfo implements Serializable {

	@ApiModelProperty("节点id")
	private String elementId;

	@ApiModelProperty("节点名称")
	private String name;

	@ApiModelProperty("节点角色")
	private String role;

	@ApiModelProperty("节点描述")
	private String desc;

	@ApiModelProperty("节点设备类型")
	private String deviceType;

	@ApiModelProperty("节点类型")
	private String type;

	@ApiModelProperty("节点业务状态")
	private String actionStatus;

	@ApiModelProperty("节点运行状态")
	private String status;

	@ApiModelProperty("节点所属区域")
	private List<String> areas;

	@ApiModelProperty("节点坐标")
	private PlaneCoordinate position;

	@ApiModelProperty("节点端口")
	private Map<String, PortInfo> ports;

	@ApiModelProperty("节点层级")
	private Float hierarchy;

	@ApiModelProperty("节点颜色")
	private String color;

	@ApiModelProperty("节点字体样式")
	private FontStyle fontStyle;

	@ApiModelProperty("一级类型code")
	private Integer oneCategoryCode;

	public ElementInfo(JSONObject element) {
		this.setElementId(element.getString("id"));
		this.setName(element.getString("name"));
	}

	public ElementInfo() {}

	public void setConfig(String name,String hierarchy,String color) {


		if (name != null) {
			this.name = name;
		}

		if (hierarchy != null) {
			try {
				this.hierarchy = Float.parseFloat(hierarchy);
			} catch (Exception e) {
				log.info(String.format("Fail to get the hierarchy of %s, the original value is %s.",
						this.elementId, hierarchy));
			}
		}
		if (color != null) {
			this.color = color;
		}
	}
}
