package com.cyberpeace.oj.practice.controller.req;

import java.util.List;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Web实操列表请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class WebOperationListReq extends PageReqBase {

	/**
	 * 方向
	 */
	@ApiModelProperty("方向")
	private String categoryId;

	/**
	 * 习题集
	 */
	@ApiModelProperty("习题集")
	private String practiceSetId;

	/**
	 * 是否解出
	 */
	@ApiModelProperty("是否解出")
	private Boolean solved;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 知识点，用逗号隔开多个知识点
	 */
	@ApiModelProperty("知识点，用逗号隔开多个知识点")
	private List<String> knowledge;

	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setPractice_set_id(String practiceSetId) {
		this.practiceSetId = practiceSetId;
	}
}
