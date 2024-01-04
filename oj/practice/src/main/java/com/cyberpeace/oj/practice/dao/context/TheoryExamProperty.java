package com.cyberpeace.oj.practice.dao.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TheoryExamProperty implements Serializable {

	@ApiModelProperty("考试时长 分钟")
	private Integer time;

	@ApiModelProperty("单选题数量")
	private Integer singleCount;

	@ApiModelProperty("单选题分数")
	private Integer singleScore;

	@ApiModelProperty("多选题数量")
	private Integer multipleCount;

	@ApiModelProperty("多选题分数")
	private Integer multipleScore;

	@ApiModelProperty("判断题数量")
	private Integer judgmentCount;

	@ApiModelProperty("判断题分数")
	private Integer judgmentScore;

	@ApiModelProperty("简答题数量")
	private Integer shortAnswerCount;

	@ApiModelProperty("简答题分数")
	private Integer shortAnswerScore;

	@ApiModelProperty("ctf题数量")
	private Integer ctfCount;

	@ApiModelProperty("ctf题分数")
	private Integer ctfScore;
}
