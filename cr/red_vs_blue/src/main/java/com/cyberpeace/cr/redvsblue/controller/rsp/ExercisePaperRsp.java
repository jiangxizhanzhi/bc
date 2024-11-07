package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 锻炼纸响应
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class ExercisePaperRsp {

	/**
	 * 练习纸
	 */
	@ApiModelProperty(value = "演习文书")
	private String exercisePaper;

}
