package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PersonalPaperCreateReq implements Serializable {

	@ApiModelProperty("题目状态")
	private List<Integer> status;

	@ApiModelProperty("出题数量")
	private Integer count;

	@ApiModelProperty("题目难度")
	private List<Integer> difficulty;

	@ApiModelProperty("题目类型")
	private List<Integer> type;

	@ApiModelProperty("方向选择")
	private List<String> direction;
}
