package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class TrainEvalAddReq implements Serializable {

	@ApiModelProperty("房间号roomId")
	@NotBlank
	private String roomId;
}
