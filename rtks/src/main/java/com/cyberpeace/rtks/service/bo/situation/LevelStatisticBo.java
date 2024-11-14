package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LevelStatisticBo implements Serializable {

	/**
	 * 层级id
	 */
	@ApiModelProperty("层级id")
	private String levelId;

	/**
	 * 层级名称
	 */
	@ApiModelProperty("层级名称")
	private String levelName;

	/**
	 * 岗位列表
	 */
	@ApiModelProperty("岗位列表")
	private List<RoleContext> roles;

	public LevelStatisticBo(String levelId, String levelName) {
		this.levelId = levelId;
		this.levelName = levelName;
		this.roles = Lists.newArrayList();
	}
}