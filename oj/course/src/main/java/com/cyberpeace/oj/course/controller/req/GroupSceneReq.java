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
 * @date 2024/11/11
 */
@Data
@ApiModel
public class GroupSceneReq implements Serializable {

	/**
	 * 群id
	 */
	@ApiModelProperty("课堂组编号")
	@NotBlank
	private String groupId;

}
