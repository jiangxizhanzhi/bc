package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务布局
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionLayout implements Serializable {

	/**
	 * 岗位id
	 *
	 * @see String
	 */
	@ApiModelProperty("岗位id")
	private String roleId;

	/**
	 * 任务id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("任务id列表")
	private List<String> missionIds;
}
