package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课统计学详情请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@ApiModel
@Data
public class LessonStatisticsDetailReq extends PageReqBase {

	/**
	 * 课id
	 */
	@ApiModelProperty("课时ID")
	private String lessonId;

	/**
	 * 搜索
	 */
	@ApiModelProperty("用户名、姓名查询")
	private String search;

	/**
	 * 组织id
	 */
	@ApiModelProperty("组织")
	private String orgId;

}
