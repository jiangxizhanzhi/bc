package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class CourseCategoryListReq extends PageReqBase {

	@ApiModelProperty("是否查询子类数据 true查询子类 false不查询")
	@NotNull
	private Boolean children;

}
