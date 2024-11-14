package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课时统计学详情请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@ApiModel
@Data
public class LessonStatisticsDetailReq extends PageReqBase {

	/**
	 * 课时ID
	 */
	@ApiModelProperty("课时ID")
	private String lessonId;

	/**
	 * 用户名、姓名查询
	 */
	@ApiModelProperty("用户名、姓名查询")
	private String search;

	/**
	 * 组织
	 */
	@ApiModelProperty("组织")
	private String orgId;

}
