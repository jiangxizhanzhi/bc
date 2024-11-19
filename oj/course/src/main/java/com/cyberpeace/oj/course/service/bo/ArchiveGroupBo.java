package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 档案组Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class ArchiveGroupBo implements Serializable {

	/**
	 * 组号
	 *
	 * @see Integer
	 */
	@ApiModelProperty("组号")
	private Integer groupNo;

	/**
	 * 组员编号
	 *
	 * @see String
	 */
	@ApiModelProperty("组员编号")
	private String groupUserIds;

	/**
	 * 资源编号
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

}
