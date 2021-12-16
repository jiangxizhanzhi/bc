package com.cyberpeace.cr.redvsblue.service.context.mission;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RoleScore {

	private String roleName;

	private Integer roleGroup;

	private Integer score;

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

}
