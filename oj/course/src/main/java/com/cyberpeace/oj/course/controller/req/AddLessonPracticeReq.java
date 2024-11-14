package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 新增课时练习请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class AddLessonPracticeReq implements Serializable {

	/**
	 * 课程编号
	 */
	@ApiModelProperty("课程编号")
	@NotBlank
	private String resourceId;

	/**
	 * 习题数据
	 */
	@ApiModelProperty("习题数据")
	private List<PaperPracticeContext> practiceList;

}
