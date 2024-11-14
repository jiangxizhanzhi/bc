package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 练习设置分页请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class PracticeSetPageReq extends PageReqBase {

	/**
	 * 习题集类型 0=理论基础 2=实践操作
	 */
	@ApiModelProperty(name = "practice_type", value = "习题集类型 0=理论基础 2=实践操作")
	private Integer practiceType;

	public void setPractice_type(Integer practiceType) {
		this.practiceType = practiceType;
	}
}
