package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.integratation.service.bo.ScreenRecordRsp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class ClassroomReportBo implements Serializable {

	@ApiModelProperty("用户编号")
	private String userId;

	@ApiModelProperty("用户名")
	private String userName;

	@ApiModelProperty("用户姓名")
	private String name;

	@ApiModelProperty("组织编号")
	private String organizationId;

	@ApiModelProperty("组织名称")
	private String organizationName;

	@ApiModelProperty("笔记编号")
	private String noteId;

	@ApiModelProperty("实验报告内容")
	private String content;

	@ApiModelProperty("实验报告得分")
	private Double score;

	@ApiModelProperty("评分人编号")
	private String evaluatorId;

	@ApiModelProperty("评分人名称")
	private String evaluatorUserName;

	@ApiModelProperty("评分人姓名")
	private String evaluatorName;

	@ApiModelProperty("评分时间")
	private Date evaluateTime;

	@ApiModelProperty("报告提交时间")
	private Date submitTime;

	@ApiModelProperty("评语")
	private String evaluateComment;

	@ApiModelProperty("是否通过")
	private Boolean passed;

	@ApiModelProperty(value = "实验视频")
	private ScreenRecordRsp screenRecord;

}
