package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;

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

	@ApiModelProperty("区域样式")
	private LineStyle style;

	@ApiModelProperty("区域动效")
	private List<String> effect;

}
