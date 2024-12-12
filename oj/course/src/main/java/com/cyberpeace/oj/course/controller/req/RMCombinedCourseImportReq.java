package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.oj.course.service.bo.CombinedCourseImportContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * rm组合课程导入请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class RMCombinedCourseImportReq implements Serializable {

	/**
	 * 课程信息
	 *
	 * @see List<CombinedCourseImportContext>
	 */
	@ApiModelProperty("组合课程导入参数")
	@NotNull
	private List<CombinedCourseImportContext> courseInfos;
}
