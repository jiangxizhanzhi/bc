package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 新增课实践请求体
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
public class AddLessonPracticeReq implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	@NotBlank
	private String resourceId;

	/**
	 * 执业名单
	 *
	 * @see List<PaperPracticeContext>
	 */
	@ApiModelProperty("习题数据")
	private List<PaperPracticeContext> practiceList;

}
