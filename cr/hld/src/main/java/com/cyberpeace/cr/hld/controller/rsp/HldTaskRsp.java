package com.cyberpeace.cr.hld.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.hld.service.context.TaskRoleConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HldTaskRsp implements Serializable {

	@ApiModelProperty("角色配置")
	private List<TaskRoleConfig> taskRoleConfigs;

	@ApiModelProperty("业务名称")
	private String taskName;

	public HldTaskRsp(List<TaskRoleConfig> taskRoleConfigs, String taskName) {
		this.taskName = taskName;
		this.taskRoleConfigs = taskRoleConfigs;
	}

}
