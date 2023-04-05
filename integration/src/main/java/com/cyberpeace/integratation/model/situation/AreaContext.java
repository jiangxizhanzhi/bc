package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AreaContext implements Serializable {

	@ApiModelProperty("区域id")
	private String areaId;

	@ApiModelProperty("区域名称")
	private String name;

	@ApiModelProperty("角色")
	private String role;

	@ApiModelProperty("区域范围")
	private List<PositionContext> scope;

	@ApiModelProperty("区域层级")
	private Float hierarchy;

}
