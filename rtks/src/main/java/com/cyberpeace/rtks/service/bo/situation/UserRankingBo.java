package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户排名 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class UserRankingBo implements Serializable {

	/**
	 * 用户id
	 */
	@ApiModelProperty("用户id")
	private String userId;

	/**
	 * 名称
	 */
	@ApiModelProperty("用户名称")
	private String name;

	/**
	 * 角色名称
	 */
	@ApiModelProperty("岗位名称")
	private String roleName;

	/**
	 * 火柴
	 */
	@ApiModelProperty("匹配度")
	private Integer match;

	/**
	 * 星
	 */
	@ApiModelProperty("是否岗位之星")
	private Boolean star = false;

	public UserRankingBo(RoleMatchRecordContext recordContext) {
		this.userId = recordContext.getUserId();
		this.roleName = recordContext.getRoleName();
		this.match = recordContext.getMatch();
	}
}
