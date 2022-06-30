package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ApiModel
public class DuplicateCourseInspectReq implements Serializable {

	@ApiModelProperty("待检查课程名称")
	@NotEmpty
	private List<String> fileNames;

}
