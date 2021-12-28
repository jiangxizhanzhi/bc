package com.cyberpeace.cr.redvsblue.service.context.mission;

import lombok.Data;

@Data
public class RankData implements Comparable<RankData> {

	private Integer completedMission;

	private String logo;

	private Integer missionRatio;

	private String name;

	private Integer ranking;

	private Integer score;

	@Override
	public int compareTo(RankData o) {
		return this.score - o.score;
	}
}
