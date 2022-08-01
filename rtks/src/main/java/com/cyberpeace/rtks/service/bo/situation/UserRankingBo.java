package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserRankingBo implements Serializable {

	@ApiModelProperty("用户id")
	private String userId;

	@ApiModelProperty("用户名称")
	private String name;

	@ApiModelProperty("岗位名称")
	private String roleName;

	@ApiModelProperty("匹配度")
	private Integer match;

	@ApiModelProperty("是否岗位之星")
	private Boolean star = false;

	public UserRankingBo(RoleMatchRecordContext recordContext) {
		this.userId = recordContext.getUserId();
		this.roleName = recordContext.getRoleName();
		this.match = recordContext.getMatch();
	}
}
