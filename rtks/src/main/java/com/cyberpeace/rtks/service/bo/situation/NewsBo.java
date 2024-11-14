package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;
import java.util.Date;

import com.cyberpeace.rtks.dao.entity.SituationLog;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动态
 */
@Data
public class NewsBo implements Serializable {

	/**
	 * 用户id
	 */
	@ApiModelProperty("用户id")
	private String userId;

	/**
	 * 用户名称
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 岗位id
	 */
	@ApiModelProperty("岗位id")
	private String roleId;

	/**
	 * 岗位名称
	 */
	@ApiModelProperty("岗位名称")
	private String roleName;

	/**
	 * 1：合格，2：优秀
	 */
	@ApiModelProperty("1：合格，2：优秀")
	private Integer type;

	/**
	 * 记录时间
	 */
	@ApiModelProperty("记录时间")
	private Date createTime;

	public NewsBo(SituationLog log) {
		this.userId = log.getUserId();
		this.roleId = log.getRoleId();
		this.roleName = log.getRoleName();
		this.type = log.getType();
		this.createTime = log.getCreateTime();
	}
}
