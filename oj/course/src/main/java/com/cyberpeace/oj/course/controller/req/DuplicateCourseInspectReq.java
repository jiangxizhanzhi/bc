package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 重复课程检查请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class DuplicateCourseInspectReq implements Serializable {

	/**
	 * 文件名
	 */
	@ApiModelProperty("待检查课程名称")
	@NotEmpty
	private List<String> fileNames;

}
