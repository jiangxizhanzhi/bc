package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class AddLessonPracticeReq implements Serializable {

	@ApiModelProperty("课程编号")
	@NotBlank
	private String resourceId;

	@ApiModelProperty("习题数据")
	private List<PaperPracticeContext> practiceList;

}
