package com.cyberpeace.assess.controller.req;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AssessTagUpdateReq extends AssessTagAddReq {

	@ApiModelProperty("资源编号")
	@NotBlank(groups = { Update.class })
	private String resourceId;

}
