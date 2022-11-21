package com.cyberpeace.situation.service.context;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class JtTaskEventContext implements Serializable {

	@ApiModelProperty("队伍ID")
	private String teamId;

	@ApiModelProperty("队伍名称")
	private String teamName;

	@ApiModelProperty("人员ID")
	private String personId;

	@ApiModelProperty("人员名称")
	private String personName;

	@ApiModelProperty("题目ID")
	private String subjectId;

	@ApiModelProperty("题目名称")
	private String subjectName;

	@ApiModelProperty("事件类型（解题成功）")
	private String eventType;

	@ApiModelProperty("是否一血")
	private Boolean firstBlood;

	@ApiModelProperty("事件积分")
	private Integer eventPoint;

	@ApiModelProperty("事件描述")
	private String eventDesc;

	@ApiModelProperty("事件时间")
	private Date eventTime;

	@ApiModelProperty("事件ID")
	private String eventId;

	public JtTaskEventContext() {
		this.eventDesc = "人员1（参赛队伍1）解题成功 +10分";
		this.eventTime = new Date();
		this.teamId = "team_1";
		this.teamName = "参赛队伍1";
		this.personId = "person_1";
		this.personName = "人员1";
		this.subjectId = "subject_1";
		this.subjectName = "题目1";
		this.eventType = "解题成功";
		this.firstBlood = false;
		this.eventPoint = 10;

	}

}
