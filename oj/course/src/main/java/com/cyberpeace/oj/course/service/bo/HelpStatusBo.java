package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 帮助状态博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class HelpStatusBo implements Serializable {

	/**
	 * 用户id
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 课id
	 */
	@ApiModelProperty("课时编号")
	private String lessonId;

	/**
	 * 节点id
	 */
	@ApiModelProperty("节点编号")
	private String nodeId;

	/**
	 * 状态
	 */
	@ApiModelProperty("状态 0=未请求协助 1=请求协助 2=协助中 3=协助已结束")
	private Integer status;

}
