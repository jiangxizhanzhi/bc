package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 自由实践列表请求体
 *
 * @author SN
 * @see PageReqBase
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel
public class LibPracticeListReq extends PageReqBase {

	/**
	* 类别id
	*
	* @see String
	*/
	@ApiModelProperty(name = "category_id", value = "分类编号")
	private String categoryId;

	/**
     * 问题类型
     *
     * @see Integer
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
