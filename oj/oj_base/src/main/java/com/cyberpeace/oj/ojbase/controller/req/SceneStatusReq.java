package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 场景状态请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class SceneStatusReq implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	@NotBlank
	private String resourceId;

	/**
	 * 状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty("1=启动 3=暂停")
	@NotNull
	private Integer status;

	/**
	 * 房间id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程表编号")
	private String roomId;

}
