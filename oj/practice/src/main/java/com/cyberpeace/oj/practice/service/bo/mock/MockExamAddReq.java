package com.cyberpeace.oj.practice.service.bo.mock;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MockExamAddReq {

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "试卷模式")
	private String presetId;
}
