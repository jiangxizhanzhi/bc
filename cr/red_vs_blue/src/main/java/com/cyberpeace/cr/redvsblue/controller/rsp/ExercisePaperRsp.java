package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 练习试卷响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class ExercisePaperRsp {

	/**
	 * 演习文书
	 */
	@ApiModelProperty(value = "演习文书")
	private String exercisePaper;

}
