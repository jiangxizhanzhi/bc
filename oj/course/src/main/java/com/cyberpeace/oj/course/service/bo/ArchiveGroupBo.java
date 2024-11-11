package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 归档组 bo
 *
 * @author SN
 * @date 2024/11/11
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
	 * 群用户id列表
	 */
	@ApiModelProperty("组员编号")
	private String groupUserIds;

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

}
