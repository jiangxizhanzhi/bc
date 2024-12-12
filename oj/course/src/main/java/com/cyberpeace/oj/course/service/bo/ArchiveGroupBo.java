package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 归档组 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
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
	 * 群用户id列表
	 *
	 * @see String
	 */
	@ApiModelProperty("组员编号")
	private String groupUserIds;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

}
