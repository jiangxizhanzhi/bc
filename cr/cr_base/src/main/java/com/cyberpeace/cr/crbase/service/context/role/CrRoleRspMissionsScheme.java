package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 用于后台模板查询接口返回基本任务信息
 */
@Data
public class CrRoleRspMissionsScheme implements Serializable {

	private Integer color;

	private Integer template;

	private List<CrRoleRspSchemeStage> scheme_data;
}