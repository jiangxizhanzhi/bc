package com.cyberpeace.cr.hld.controller.rsp;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class HldRoomRsp {

	private String resourceId;

	@ApiModelProperty(required = true)
	@NotNull
	private List<JSONObject> redRoles;

	@ApiModelProperty(required = true)
	@NotNull
	private List<JSONObject> blueRoles;
}
