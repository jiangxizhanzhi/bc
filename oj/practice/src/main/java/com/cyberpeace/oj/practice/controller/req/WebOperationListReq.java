package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Web实操列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Data
@ApiModel
public class WebOperationListReq extends PageReqBase {

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("方向")
	private String categoryId;

	/**
	 * 实践设置id
	 *
	 * @see String
	 */
	@ApiModelProperty("习题集")
	private String practiceSetId;

	/**
	 * 解决
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否解出")
	private Boolean solved;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setPractice_set_id(String practiceSetId) {
		this.practiceSetId = practiceSetId;
	}
}
