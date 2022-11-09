package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("cr_user_report")
@Data
public class CrUserReport extends BaseTable {

	@ApiModelProperty(value = "用户id")
	private String userId;

	@ApiModelProperty(value = "房间id")
	private String roomId;

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "角色类型")
	private Integer roleType;

	@ApiModelProperty(value = "角色名")
	private String roleName;

	@ApiModelProperty(value = "任务已完成")
	Integer completedCount;

	@ApiModelProperty(value = "任务总数")
	Integer missionCount;

	@ApiModelProperty(value = "评语")
	private String comment;

	@ApiModelProperty(value = "分数")
	private Integer score;
}
