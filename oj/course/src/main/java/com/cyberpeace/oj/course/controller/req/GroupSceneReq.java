package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 群场景请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class GroupSceneReq implements Serializable {

	/**
	 * 群id
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂组编号")
	@NotBlank
	private String groupId;

}
