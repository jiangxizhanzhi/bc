package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class ElementSlimContext implements Serializable {

	@ApiModelProperty("节点坐标")
	private PositionContext position;

	@ApiModelProperty("父节点ID列表")
	private List<String> parentIds;

	@ApiModelProperty("平台ID")
	private String platformId;

	public ElementSlimContext() {}

	public ElementSlimContext(ElementContext elementContext) {
		this.position = elementContext.getPosition();
		this.platformId = elementContext.getPlatformId();
		this.parentIds = elementContext.getParentIds();
	}

}
