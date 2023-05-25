package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class KnowledgeProgressBo implements Serializable {

	@ApiModelProperty(value = "知识点总数")
	private Integer total;

	@ApiModelProperty(value = "掌握度>=60")
	private Integer ge60;

	@ApiModelProperty(value = "掌握度>=80")
	private Integer ge80;

	@ApiModelProperty(value = "掌握度>=90")
	private Integer ge90;

	@ApiModelProperty(value = "小于60")
	private Integer lt60;
}
