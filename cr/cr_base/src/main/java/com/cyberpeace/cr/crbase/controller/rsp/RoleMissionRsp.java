package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.RbUserData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色任务返回值
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class RoleMissionRsp implements Serializable {

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

	@ApiModelProperty("任务答案")
	private String answer;

	@ApiModelProperty("任务分数")
	private Integer score;

	@ApiModelProperty("用户列表")
	private List<RbUserData> deliveryDetails;
}
