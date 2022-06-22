package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.oj.course.service.bo.CombinedCourseImportContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
public class RMCombinedCourseImportReq implements Serializable {

	@ApiModelProperty("组合课程导入参数")
	@NotBlank
	private List<CombinedCourseImportContext> courseInfos;
}
