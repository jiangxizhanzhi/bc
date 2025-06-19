package com.cyberpeace.ai.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户画像报告数据")
public class UserProfileReportVO {

	@ApiModelProperty("报告ID")
	private String reportId;

	@ApiModelProperty("报告名称")
	private String reportName;

	@ApiModelProperty("用户姓名")
	private String userName;

	@ApiModelProperty(value = "维度", allowableValues = "1,2,3,4", notes = "1-课程学习, 2-题目练习,3-综合训练,4-岗位评估")
	private String dimension;

	@ApiModelProperty("课程学习数据")
	private UserProfileCourseDataVO courseData;

	@ApiModelProperty("题目练习数据")
	private UserProfilePracticeDataVO practiceData;

	@ApiModelProperty("综合训练数据")
	private UserProfileTrainDataVO trainingData;

	@ApiModelProperty("岗位评估数据")
	private UserProfileEvaluationDataVO evaluationData;

	@ApiModelProperty("总结数据")
	private String summaryData;

	@ApiModelProperty("建议数据")
	private String suggestionData;

}