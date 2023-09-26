package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExecGuideHisReq implements Serializable {

	@ApiModelProperty("房间id")
	private String roomId;

	@ApiModelProperty("训练类型")
	private Integer businessType;

	@ApiModelProperty("事件名称")
	private String eventName;

	@ApiModelProperty("攻防类型code")
	private List<Integer> categoryCode;

	@ApiModelProperty("动作类型code")
	private Integer actionCode;

	@ApiModelProperty("源节点id")
	private String sourceId;

	@ApiModelProperty("目的节点id")
	private String targetId;

	@ApiModelProperty("结果")
	private Integer result;

	@ApiModelProperty("归属角色")
	private Integer role;
}
