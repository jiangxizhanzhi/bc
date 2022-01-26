package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;

import lombok.Data;

/**
 * 用于后台模板查询接口返回基本任务信息
 */
@Data
public class CrRoleConfigMission implements Serializable {

	private String title;

	private String resourceId;

	private String description;
}
