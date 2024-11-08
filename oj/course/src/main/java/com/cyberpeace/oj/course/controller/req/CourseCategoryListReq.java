package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 课程类别列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class CourseCategoryListReq extends PageReqBase {

	/**
	 * 子级
	 */
	@ApiModelProperty("是否查询子类数据 true查询子类 false不查询")
	@NotNull
	private Boolean children;

}
