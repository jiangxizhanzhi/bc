package com.cyberpeace.oj.practice.service.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluateResult implements Serializable {

	/** 是否通过*/
	private Boolean solved;

	/** 得分*/
	private Double score;

	/** 总分*/
	private Double totalScore;

	/** 资源编号*/
	private String resourceId;
}
