package com.cyberpeace.cr.redvsblue.service.context.mission;

import lombok.Data;

/**
 * 排名信息
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RankData implements Comparable<RankData> {

	/**
	 * 完成任务
	 */
	private Integer completedMission;

	/**
	 * 图标
	 */
	private String logo;

	/**
	 * 任务比率
	 */
	private Integer missionRatio;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 排名
	 */
	private Integer ranking;

	/**
	 * 得分
	 */
	private Integer score;

	/**
	 * 排序类型
	 */
	private String sortType = "score";

	@Override
	public int compareTo(RankData o) {
		if (sortType.equals("missionRatio")) {
			return o.missionRatio - this.missionRatio;
		}

		return o.score - this.score;
	}
}
