package com.cyberpeace.cr.evaluation.service.bo;

import lombok.Data;

/**
 * 排名信息
 */
@Data
public class EvalRankExportBo implements Comparable<EvalRankExportBo> {

	private Integer sort;

	private String name;

	private Integer score;

	private Integer rankScore;

	private Integer totalScore;

	private String group;


	@Override
	public int compareTo(EvalRankExportBo o) {
		return o.totalScore - this.totalScore;
	}
}
