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

	@ApiModelProperty(name = "category_children", value = "是否查询分类子类 true=查询的分类信息中包含子类信息")
	private Boolean categoryChildren;

	@ApiModelProperty(name = "question_type", value = "题型")
	private String questionType;

	public void setPractice_set_id(String practiceSetId) {
		this.practiceSetId = practiceSetId;
	}

	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setQuestion_type(String questionType) {
		this.questionType = questionType;
	}

	public Boolean getCategoryChildren() {
		return null == categoryChildren ? false : categoryChildren;
	}
}
