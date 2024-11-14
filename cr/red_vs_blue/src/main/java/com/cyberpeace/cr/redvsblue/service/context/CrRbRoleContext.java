package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;
import java.util.Map;

import com.cyberpeace.cr.crbase.service.context.MissionCompletion;
import com.cyberpeace.cr.crbase.service.context.role.ShowWriteupBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝角色模型
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class CrRbRoleContext {

	/**
	 * 角色数据
	 */
	@ApiModelProperty(value = "角色数据")
	private List<GroupData> groups;

	/**
	 * 任务完成状态
	 */
	@ApiModelProperty(value = "任务完成状态")
	private Map<String, MissionCompletion> missionMapping;

	/**
	 * 题解显示状态
	 */
	@ApiModelProperty(value = "题解显示状态")
	private Map<String, ShowWriteupBo> showWriteupMapping;

	/**
	 * 动态任务
	 */
	@ApiModelProperty(value = "动态任务")
	private List<String> dynamic;
}
