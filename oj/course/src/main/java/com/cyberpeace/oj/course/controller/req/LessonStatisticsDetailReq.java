package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class LessonStatisticsDetailReq extends PageReqBase {

	@ApiModelProperty("课时ID")
	private String lessonId;

	@ApiModelProperty("用户名、昵称查询")
	private String search;

	@ApiModelProperty("组织")
	private String orgId;

}
