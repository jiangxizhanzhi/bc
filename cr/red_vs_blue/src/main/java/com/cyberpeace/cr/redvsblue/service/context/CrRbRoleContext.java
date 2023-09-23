package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;
import java.util.Map;

import com.cyberpeace.cr.crbase.service.context.MissionCompletion;
import com.cyberpeace.cr.crbase.service.context.role.ShowWriteupBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝角色模型
 */
@Data
public class CrRbRoleContext {

	@ApiModelProperty(value = "角色数据")
	private List<GroupData> groups;

	@ApiModelProperty(value = "任务完成状态")
	private Map<String, MissionCompletion> missionMapping;

	@ApiModelProperty(value = "题解显示状态")
	private Map<String, ShowWriteupBo> showWriteupMapping;

	@ApiModelProperty(value = "动态任务")
	private List<String> dynamic;
}
