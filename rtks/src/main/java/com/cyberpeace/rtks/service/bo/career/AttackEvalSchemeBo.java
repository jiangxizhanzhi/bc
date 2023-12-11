package com.cyberpeace.rtks.service.bo.career;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AttackEvalSchemeBo implements Serializable {

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("子编号")
	private List<FrameItemBase> children;
}
