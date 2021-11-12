package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class EvaluateResult implements Serializable {

	/** 是否通过*/
	private Boolean solved;

	/** 得分*/
	private Double score;

	/** 总分*/
	private Double totalScore;

	public EvaluateResult(Boolean solved, Double score, Double totalScore) {
		this.solved = solved;
		this.score = score;
		this.totalScore = totalScore;
	}
}
