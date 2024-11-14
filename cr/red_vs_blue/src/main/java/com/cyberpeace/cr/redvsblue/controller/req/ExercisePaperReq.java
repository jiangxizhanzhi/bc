package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 练习试卷请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class ExercisePaperReq implements Serializable {

	/**
	 * 演习文书
	 */
	@ApiModelProperty(value = "演习文书")
	private String exercisePaper;

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "房间id", required = true)
	private String resourceId;

	/**
	 * 分组
	 */
	@ApiModelProperty(value = "分组", required = true)
	private Integer groupHash;
}
