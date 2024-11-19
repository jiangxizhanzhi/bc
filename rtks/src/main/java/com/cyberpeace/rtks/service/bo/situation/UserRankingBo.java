package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户排名Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class UserRankingBo implements Serializable {

	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户id")
	private String userId;

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名称")
	private String name;

	/**
	 * 岗位名称
	 *
	 * @see String
	 */
	@ApiModelProperty("岗位名称")
	private String roleName;

	/**
	 * 匹配度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("匹配度")
	private Integer match;

	/**
	 * 是否岗位之星
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否岗位之星")
	private Boolean star = false;

	public UserRankingBo(RoleMatchRecordContext recordContext) {
		this.userId = recordContext.getUserId();
		this.roleName = recordContext.getRoleName();
		this.match = recordContext.getMatch();
	}
}
