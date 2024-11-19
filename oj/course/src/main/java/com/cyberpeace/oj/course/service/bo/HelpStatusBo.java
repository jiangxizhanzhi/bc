package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 帮助状态Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class HelpStatusBo implements Serializable {

	/**
	 * 用户编号
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 课时编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课时编号")
	private String lessonId;

	/**
	 * 节点编号
	 *
	 * @see String
	 */
	@ApiModelProperty("节点编号")
	private String nodeId;

	/**
	 * 状态 0=未请求协助 1=请求协助 2=协助中 3=协助已结束
	 *
	 * @see Integer
	 */
	@ApiModelProperty("状态 0=未请求协助 1=请求协助 2=协助中 3=协助已结束")
	private Integer status;

}
