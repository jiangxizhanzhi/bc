package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 课堂档案请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class ClassroomArchiveReq implements Serializable {

	/**
	 * 课堂id
	 */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String classroomId;

	/**
	 * 群档案id
	 */
	@ApiModelProperty("分组模板编号")
	@NotBlank
	private String groupArchiveId;

}
