package com.cyberpeace.cr.redvsblue.service.context.mission;

import lombok.Data;

/**
 * 排名信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Comparable
 * @since 1.0.0
 */
@Data
public class RankData implements Comparable<RankData> {

    /**
     * 完成任务
     *
     * @see Integer
     */
    private Integer completedMission;

    /**
     * 图标
     *
     * @see String
     */
    private String logo;

    /**
     * 任务比率
     *
     * @see Integer
     */
    private Integer missionRatio;

    /**
     * 名称
     *
     * @see String
     */
    private String name;

    /**
     * 排名
     *
     * @see Integer
     */
    private Integer ranking;

    /**
     * 得分
     *
     * @see Integer
     */
    private Integer score;

    /**
     * 排序类型
     *
     * @see String
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
