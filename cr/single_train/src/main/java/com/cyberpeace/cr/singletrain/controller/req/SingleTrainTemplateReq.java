package com.cyberpeace.cr.singletrain.controller.req;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;

import lombok.Data;

/**
 * 单训练模板请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class SingleTrainTemplateReq extends BasicTemplate {

	/**
	 * 任务计划
	 */
	private CrRoleConfigMissionsScheme missionsScheme;

	/**
	 * 资源id
	 */
	private String resourceId;

}
