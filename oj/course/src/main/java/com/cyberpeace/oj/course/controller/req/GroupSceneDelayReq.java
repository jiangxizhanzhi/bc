package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 组场景延迟请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class GroupSceneDelayReq implements Serializable {

	/**
	 * 课堂编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String classroomId;

	/**
	 * 是否开启场景延时 true=开启 false=不开启
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否开启场景延时 true=开启 false=不开启")
	@NotNull
	private Boolean end;

}
