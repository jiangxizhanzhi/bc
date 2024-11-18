package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 锻炼试卷请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ExercisePaperReq implements Serializable {

	/**
	 * 练习试卷
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "演习文书")
	private String exercisePaper;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "房间id", required = true)
	private String resourceId;

	/**
	 * 组哈希
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "分组", required = true)
	private Integer groupHash;
}
