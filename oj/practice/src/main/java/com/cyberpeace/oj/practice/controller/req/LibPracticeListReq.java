package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 自由实践列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class LibPracticeListReq extends PageReqBase {
	/**
	 * 类别id
	 */
	@ApiModelProperty(name = "category_id", value = "分类编号")
	private String categoryId;

	/**
	 * 问题类型
	 */
	@ApiModelProperty(name = "question_type", value = "题型")
	private Integer questionType;


	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setQuestion_type(Integer questionType) {
		this.questionType = questionType;
	}
}
