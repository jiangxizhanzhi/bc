package com.cyberpeace.oj.practice.service.bo.mock;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MockPaperDetail {

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "理论模拟描述")
	private String theoryDesc;

	@ApiModelProperty(value = "实操模拟描述")
	private String operationDesc;

	@ApiModelProperty(value = "理论模拟倒计时，剩余秒数")
	private Integer theoryCountDown;

	@ApiModelProperty(value = "实操模拟倒计时")
	private Integer operationCountDown;

	@ApiModelProperty(value = "理论模拟开始时间")
	private Date theoryStartTime;

	@ApiModelProperty(value = "理论模拟时长")
	private Integer theoryLength;

	@ApiModelProperty(value = "实操模拟时长")
	private Integer operationLength;

	@ApiModelProperty(value = "理论总分")
	private Integer theoryFullMark = 100;

	@ApiModelProperty(value = "模拟总分")
	private Integer operationFullMark = 100;

	@ApiModelProperty(value = "理论模拟状态，1：未开始 2：进行中 3：通过 4：未通过")
	private Integer theoryStatus;

	@ApiModelProperty(value = "理论得分")
	private Integer theoryScore;

	@ApiModelProperty(value = "实操状态 1：未开始 2：进行中 3：通过 4：未通过")
	private Integer operationStatus;

	@ApiModelProperty(value = "实操得分")
	private Integer operationScore;

	@ApiModelProperty(value = "实操开始时间")
	private Date operationStartTime;

	@ApiModelProperty(value = "实操结束时间")
	private Date operationEndTime;
}
