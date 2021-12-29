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

	private String sortType = "score";

	@Override
	public int compareTo(RankData o) {
		if (sortType.equals("missionRatio")) {
			return o.missionRatio - this.missionRatio;
		}

		return o.score - this.score;
	}
}
