package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 实践类别列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class PracticeCategoryListReq extends PageReqBase {

	/**
	 * 执业类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(name = "practice_type", value = "练习分类类型 0=理论基础 2=实操练习", required = true)
	@NotNull
	private Integer practiceType;

	/**
	 * 子级
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否查询子分类 保留字段预置功能")
	private Boolean children = CommonConstant.FALSE;

	public void setPractice_type(Integer practiceType) {
		this.practiceType = practiceType;
	}
}
