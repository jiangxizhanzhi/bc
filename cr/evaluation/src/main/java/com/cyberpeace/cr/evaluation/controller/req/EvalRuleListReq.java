package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class EvalRuleListReq extends PageReqBase implements Serializable {


	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("房间号roomId")
	@NotBlank
	private String roomId;

}
