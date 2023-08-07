package com.cyberpeace.cr.evaluation.service.bo;

import lombok.Data;

/**
 * 排名信息
 */
@Data
public class RankDataBo implements Comparable<RankDataBo> {

	private Integer completedMission;

	private String logo;

	private Integer missionRatio;

	private String name;

	private Integer ranking;

	private Integer score;

	private String sortType = "score";

	@Override
	public int compareTo(RankDataBo o) {
		if (sortType.equals("missionRatio")) {
			return o.missionRatio - this.missionRatio;
		}

		return o.score - this.score;
	}
}
