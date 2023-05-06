package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;
import java.util.Map;

import com.cyberpeace.cr.crbase.service.context.MissionCompletion;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * cr角色上下文
 * 角色模型
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class CrRoleContext<T> {

	@ApiModelProperty(value = "角色")
	private List<RoleData<T>> roles;

	@ApiModelProperty(value = "任务完成状态")
	private Map<String, MissionCompletion> missionMapping;

	@ApiModelProperty(value = "题解展示状态")
	private Map<String, ShowWriteup> showWriteupMapping;

	@ApiModelProperty(value = "动态任务， 冗余")
	private List<String> dynamic;

}
