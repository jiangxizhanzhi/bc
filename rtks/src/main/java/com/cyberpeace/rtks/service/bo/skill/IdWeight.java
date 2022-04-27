package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IdWeight implements Serializable {

	@ApiModelProperty("id")
	private String id;

	@ApiModelProperty("权重")
	private Integer weight;
}
