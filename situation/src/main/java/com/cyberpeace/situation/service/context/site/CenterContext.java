package com.cyberpeace.situation.service.context.site;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CenterContext extends SiteContext implements Serializable {

	@ApiModelProperty("LG中心ID")
	private String centerId;

	@ApiModelProperty("LG中心名称")
	private String centerName;

	@Override
	public String getId() {
		return this.centerId;
	}

	public void setGroupId(String groupId) {
		this.centerId = groupId;
	}

	public void setName(String name) {
		this.centerName = name;
	}
}
