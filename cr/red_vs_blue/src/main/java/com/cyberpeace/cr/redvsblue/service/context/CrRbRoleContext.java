package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;
import java.util.Map;

import com.cyberpeace.cr.crbase.service.context.MissionCompletion;
import com.cyberpeace.cr.crbase.service.context.role.ShowWriteup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝角色模型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class CrRbRoleContext {

	/**
	 * 组
	 *
	 * @see List<GroupData>
	 */
	@ApiModelProperty(value = "角色数据")
	private List<GroupData> groups;

	/**
	 * 任务映射
	 *
	 * @see Map<String, MissionCompletion>
	 */
	@ApiModelProperty(value = "任务完成状态")
	private Map<String, MissionCompletion> missionMapping;

	/**
	 * 显示撰写映射
	 *
	 * @see Map<String, ShowWriteup>
	 */
	@ApiModelProperty(value = "题解显示状态")
	private Map<String, ShowWriteup> showWriteupMapping;

	/**
	 * 动态
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "动态任务")
	private List<String> dynamic;
}
