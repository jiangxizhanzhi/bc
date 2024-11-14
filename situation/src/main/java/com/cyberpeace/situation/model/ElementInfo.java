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

	/**
	 * 节点id
	 */
	@ApiModelProperty("节点id")
	private String elementId;

	/**
	 * 节点名称
	 */
	@ApiModelProperty("节点名称")
	private String name;

	/**
	 * 节点角色
	 */
	@ApiModelProperty("节点角色")
	private String role;

	/**
	 * 节点描述
	 */
	@ApiModelProperty("节点描述")
	private String desc;

	/**
	 * 节点设备类型
	 */
	@ApiModelProperty("节点设备类型")
	private String deviceType;

	/**
	 * 节点类型
	 */
	@ApiModelProperty("节点类型")
	private String type;

	/**
	 * 节点业务状态
	 */
	@ApiModelProperty("节点业务状态")
	private String actionStatus;

	/**
	 * 节点运行状态
	 */
	@ApiModelProperty("节点运行状态")
	private String status;

	/**
	 * 节点所属区域
	 */
	@ApiModelProperty("节点所属区域")
	private List<String> areas;

	/**
	 * 节点坐标
	 */
	@ApiModelProperty("节点坐标")
	private PlaneCoordinate position;

	/**
	 * 节点端口
	 */
	@ApiModelProperty("节点端口")
	private Map<String, PortInfo> ports;

	/**
	 * 节点层级
	 */
	@ApiModelProperty("节点层级")
	private Float hierarchy;

	/**
	 * 节点颜色
	 */
	@ApiModelProperty("节点颜色")
	private String color;

	/**
	 * 节点字体样式
	 */
	@ApiModelProperty("节点字体样式")
	private FontStyle fontStyle;

	/**
	 * 一级类型code
	 */
	@ApiModelProperty("一级类型code")
	private Integer oneCategoryCode;

	/**
	 * 二级类型code
	 */
	@ApiModelProperty("二级类型code")
	private Integer secondCategoryCode;

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
