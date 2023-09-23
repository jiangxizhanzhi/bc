package com.cyberpeace.cr.crbase.controller.req.resource;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BatchDumpReq implements Serializable {

	@ApiModelProperty("模板id列表")
	private List<String> ids;
}
