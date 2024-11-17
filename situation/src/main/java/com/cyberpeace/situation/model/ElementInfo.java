package com.cyberpeace.situation.model;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.dao.entity.SituationConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

	@ApiModelProperty("节点动作状态")
	private String actionStatus;

	@ApiModelProperty("节点状态")
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

	@ApiModelProperty("模型Url")
	@JsonProperty("modelUrl")
	private String modelUrl;

	@ApiModelProperty("漏洞数量")
	private Integer leakCount;

	@ApiModelProperty("漏洞信息")
	private List<LeakInfo> leakInfos;

	@ApiModelProperty("设备id")
	private String imageUuid;

	@ApiModelProperty("设备类型")
	private Integer flag;

	@ApiModelProperty("cvss")
	private Double cvss;

	@ApiModelProperty("设备等级 0 无， 1 低， 2 中， 3 高， 4 严重")
	private Integer cvssLevel;

	public ElementInfo(JSONObject element) {
		this.setElementId(element.getString("id"));
		this.setName(element.getString("name"));
	}

	public ElementInfo() {}

	public void setConfig(Map<Integer, String> nodeConfig) {

		// 设置节点名称
		String name = nodeConfig.getOrDefault(SituationConfig.Attribute.NAME.getCode(), null);
		if (name != null) {
			this.name = name;
		}

		// 设置节点高度
		String hierarchy = nodeConfig.getOrDefault(SituationConfig.Attribute.HEIGHT.getCode(),
				null);
		if (hierarchy != null) {
			try {
				this.hierarchy = Float.parseFloat(hierarchy);
			} catch (Exception e) {
				log.info(String.format("Fail to get the hierarchy of %s, the original value is %s.",
						this.elementId, hierarchy));
			}
		}

		// 设置节点颜色
		String color = nodeConfig.getOrDefault(SituationConfig.Attribute.COLOR.getCode(), null);
		if (color != null) {
			this.color = color;
		}
	}
}
