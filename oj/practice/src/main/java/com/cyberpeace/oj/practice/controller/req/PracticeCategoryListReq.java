package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 练习类别列表请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class PracticeCategoryListReq extends PageReqBase {

	/**
	 * 练习类型
	 */
	@ApiModelProperty(name = "practice_type", value = "练习分类类型 0=理论基础 2=实操练习", required = true)
	@NotNull
	private Integer practiceType;

	/**
	 * 子级
	 */
	@ApiModelProperty("是否查询子分类 保留字段预置功能")
	private Boolean children = CommonConstant.FALSE;

	public void setPractice_type(Integer practiceType) {
		this.practiceType = practiceType;
	}
}
