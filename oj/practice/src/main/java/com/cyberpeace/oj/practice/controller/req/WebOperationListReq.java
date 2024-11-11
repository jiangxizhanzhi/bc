package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Web实操列表请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class WebOperationListReq extends PageReqBase {

	/**
	 * 类别id
	 */
	@ApiModelProperty("方向")
	private String categoryId;

	/**
	 * 练习设置id
	 */
	@ApiModelProperty("习题集")
	private String practiceSetId;

	/**
	 * 解决
	 */
	@ApiModelProperty("是否解出")
	private Boolean solved;

	/**
	 * 困难
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
