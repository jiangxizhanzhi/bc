package com.cyberpeace.cr.redvsblue.service.context.mission;

import java.util.List;

import com.cyberpeace.common.enums.RbRoleGroup;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色成绩
 */
@NoArgsConstructor
@Data
public class RoleScore {

	@ApiModelProperty(value = "角色名称")
	private String roleName;

	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	@ApiModelProperty(value = "得分")
	private Integer score;

	@ApiModelProperty(value = "任务信息")
	private List<Integer> missionsInfo;

	public RoleScore(String role, Integer code, Integer score, Integer finishedTaskCount,
					 Integer taskCount) {
		this.roleName = role;
		this.roleGroup = code;
		this.score = score;
		List<Integer> missions = Lists.newArrayList();
		missions.add(finishedTaskCount);
		missions.add(taskCount);

		this.missionsInfo = missions;

	}

	public RoleScore(RbRoleGroup roleGroup, Integer score, Integer finishedTaskCount,
                     Integer taskCount) {
		this(roleGroup.getDesc(), roleGroup.getCode(), score, finishedTaskCount, taskCount);
	}

}
