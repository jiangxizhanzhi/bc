package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 水平统计Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class LevelStatisticBo implements Serializable {

	/**
	 * 层级id
	 *
	 * @see String
	 */
	@ApiModelProperty("层级id")
	private String levelId;

	/**
	 * 层级名称
	 *
	 * @see String
	 */
	@ApiModelProperty("层级名称")
	private String levelName;

	/**
	 * 岗位列表
	 *
	 * @see List<RoleContext>
	 */
	@ApiModelProperty("岗位列表")
	private List<RoleContext> roles;

	public LevelStatisticBo(String levelId, String levelName) {
		this.levelId = levelId;
		this.levelName = levelName;
		this.roles = Lists.newArrayList();
	}
}