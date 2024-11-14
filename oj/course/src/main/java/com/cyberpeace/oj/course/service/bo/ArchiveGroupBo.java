package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 档案组Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class ArchiveGroupBo implements Serializable {

	/**
	 * 组号
	 */
	@ApiModelProperty("组号")
	private Integer groupNo;

	/**
	 * 组员编号
	 */
	@ApiModelProperty("组员编号")
	private String groupUserIds;

	/**
	 * 资源编号
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

}
