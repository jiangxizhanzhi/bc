package com.cyberpeace.cr.crbase.service.context.score;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NameScore implements Serializable {

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("分数")
	private Integer score = 0;
}
