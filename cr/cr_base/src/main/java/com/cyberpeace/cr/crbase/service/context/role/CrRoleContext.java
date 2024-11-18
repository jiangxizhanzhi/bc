package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;
import java.util.Map;

import com.cyberpeace.cr.crbase.service.context.MissionCompletion;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色模型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class CrRoleContext<T> {

	/**
	 * 角色
	 *
	 * @see List<RoleData<T>>
	 */
	@ApiModelProperty(value = "角色")
	private List<RoleData<T>> roles;

	/**
	 * 任务映射
	 *
	 * @see Map<String, MissionCompletion>
	 */
	@ApiModelProperty(value = "任务完成状态")
	private Map<String, MissionCompletion> missionMapping;

	/**
	 * 显示 Writeup 映射
	 *
	 * @see Map<String, ShowWriteup>
	 */
	@ApiModelProperty(value = "题解展示状态")
	private Map<String, ShowWriteup> showWriteupMapping;

	/**
	 * 动态
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "动态任务， 冗余")
	private List<String> dynamic;

}
