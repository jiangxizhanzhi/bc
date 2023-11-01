package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class LoadCourseReq {

	@ApiModelProperty("课程数据文件")
	@NotNull
	private MultipartFile file;

}
