package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LibPracticeListReq extends PageReqBase {

	@ApiModelProperty("习题集编号")
	private String practiceSetId;

	@ApiModelProperty("分类编号")
	private String categoryId;

	@ApiModelProperty("题型")
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
}
