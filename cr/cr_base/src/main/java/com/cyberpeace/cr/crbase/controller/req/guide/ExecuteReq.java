package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExecuteReq implements Serializable {

	@ApiModelProperty("动作类型")
	private Integer eventType;

	@ApiModelProperty("攻防类型")
	private List<Integer> categoryList;

	@ApiModelProperty("源节点")
	private String source;

	@ApiModelProperty("目标节点")
	private String target;


}
