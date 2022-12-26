package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class ElementContext implements Serializable {

	@ApiModelProperty("元素ID")
	private String elementId;

	@ApiModelProperty("元素类型")
	private String elementType;

	@ApiModelProperty("元素名称")
	private String name;

	@ApiModelProperty("元素角色")
	private String role;

	@ApiModelProperty("元素描述")
	private String desc;

	@ApiModelProperty("三方靶标链接")
	private String thirdUrl;

	@ApiModelProperty("虚实类型")
	private String vrType;

	@ApiModelProperty("设备类型")
	private String deviceType;

	@ApiModelProperty("设备模型")
	private String model;

	@ApiModelProperty("元素层级")
	private Float hierarchy;

	@ApiModelProperty("父节点ID")
	private String parent;

	@ApiModelProperty("子节点ID列表")
	private List<String> child;

	@ApiModelProperty("节点坐标")
	private PositionContext position;

}
