package com.cyberpeace.oj.practice.service.bo;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RandomPracticeQuery extends PatternBase {

	@ApiModelProperty("习题集编号")
	private String practiceSetId;

	@ApiModelProperty("分类编号")
	private List<String> categoryIds;

	@ApiModelProperty("题型 0=单选 1-多选.....")
	private Integer questionType;

	@ApiModelProperty("习题数量")
	private Integer count;

	@ApiModelProperty("已选择的习题集编号")
	private List<String> selectIds;
}
