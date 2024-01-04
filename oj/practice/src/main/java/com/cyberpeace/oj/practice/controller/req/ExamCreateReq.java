package com.cyberpeace.oj.practice.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Size;

import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExamCreateReq implements Serializable {

	@ApiModelProperty("模拟考试名称")
	private String name;

	private Integer paperType;
}
