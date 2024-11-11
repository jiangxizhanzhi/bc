package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.oj.course.service.bo.CombinedCourseImportContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * rm组合课程进口请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class RMCombinedCourseImportReq implements Serializable {

	/**
	 * 课程信息
	 */
	@ApiModelProperty("组合课程导入参数")
	@NotNull
	private List<CombinedCourseImportContext> courseInfos;
}
