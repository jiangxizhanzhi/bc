package com.cyberpeace.cr.redvsblue.controller.rsp;

import com.cyberpeace.cr.crbase.controller.req.MissionFlagAttachmentQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MissionInfoRsp implements Serializable {

	@ApiModelProperty("任务id")
	private String resourceId;

	@ApiModelProperty("任务名称")
	private String title;

	@ApiModelProperty("选项")
	private String option;

	@ApiModelProperty("任务类别")
	private String category;

	@ApiModelProperty("任务描述")
	private String description;

	@ApiModelProperty("任务状态")
	private Integer missionStatus;

	@ApiModelProperty("任务类型")
	private Integer type;

	@ApiModelProperty("任务技能分数")
	private String skillPoints;

	@ApiModelProperty("任务题解")
	private String writeUp;

	@ApiModelProperty("任务分数")
	private Integer score;

	@ApiModelProperty("答案")
	private String answer;

	@ApiModelProperty("评估状态")
	private Integer evaluateStatus;

	@ApiModelProperty("拒绝列表")
	private List rejectLog;

	@ApiModelProperty("用户答案")
	private String userAnswer;

	@ApiModelProperty("任务状态")
	private Integer userMissionStatus;

	@ApiModelProperty("展示题解")
	private Boolean writeUpShow;

	@ApiModelProperty(value = "附件")
	private MissionFlagAttachmentQuery  flagFile;

}
