package com.cyberpeace.iov.service.bo.frame;

import java.util.List;

import com.cyberpeace.common.util.JsonUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VehicleFrameDetailBo extends VehicleFrameListBo {

	@ApiModelProperty(value = "拓扑")
	private String topo;

	public List<FrameTopoItem> parseTopo() {
		return JsonUtil.toJsonObjectArray(this.topo, FrameTopoItem.class);
	}

}
