package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户统计
 *
 * @author SN
 * @date 2023/05/25
 */
@Data
public class UserStatisticsBo implements Serializable {

	@ApiModelProperty(value = "岗位名称")
	private String roleName;

	@ApiModelProperty(value = "岗位id")
	private String roleId;

	@ApiModelProperty(value = "岗位层级")
	private String rolePath;

	@ApiModelProperty(value = "岗位课程数")
	private Integer courseCount;

	@ApiModelProperty(value = "课程完成度")
	private Integer courseProgress;

	@ApiModelProperty(value = "岗位任务数")
	private Integer missionCount;

	@ApiModelProperty(value = "任务完成度")
	private Integer missionProgress;

	@ApiModelProperty(value = "岗位满足度")
	private Integer match;

	@ApiModelProperty(value = "岗位排名")
	private Integer ranking;
}
