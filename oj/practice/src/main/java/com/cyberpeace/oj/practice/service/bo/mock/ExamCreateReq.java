package com.cyberpeace.oj.practice.service.bo.mock;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExamCreateReq implements Serializable {

	@ApiModelProperty("模拟考试名称")
	private String name;

	private Integer paperType;
}
