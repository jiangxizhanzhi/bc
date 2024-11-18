package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 锻炼试卷响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class ExercisePaperRsp {

	/**
	 * 练习试卷
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "演习文书")
	private String exercisePaper;

}
