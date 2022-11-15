package com.cyberpeace.bs.controller.req;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IdListReq implements Serializable {

	@ApiModelProperty(value = "id列表")
	private List<String> ids;

	public List<String> getIds() {
		return ids;
	}
}
