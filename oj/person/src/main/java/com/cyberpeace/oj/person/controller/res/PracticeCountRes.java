package com.cyberpeace.oj.person.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PracticeCountRes implements Serializable {

	@ApiModelProperty("累计练习人数")
	private Integer peopleCount;

	@ApiModelProperty("累计练习次数")
	private Integer practiceCount;

	@ApiModelProperty("平均练习次数")
	private Integer averageCount;
}
