package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LibPracticeListReq extends PageReqBase {

	@ApiModelProperty(name = "practice_set_id", value = "习题集编号")
	private String practiceSetId;

	@ApiModelProperty(name = "category_id", value = "分类编号")
	private String categoryId;

	@ApiModelProperty(name = "question_type", value = "题型")
	private Integer questionType;

	public void setPractice_set_id(String practiceSetId) {
		this.practiceSetId = practiceSetId;
	}

	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setQuestion_type(Integer questionType) {
		this.questionType = questionType;
	}
}
