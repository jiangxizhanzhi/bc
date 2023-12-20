package com.cyberpeace.cr.evaluation.dao.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.util.JsonUtil;

import lombok.Data;

/**
 * 测评模板
 * 	仅使用 operationAuthType， operationAuthIds
 * 	不使用 readAuthType， readAuthIds
 *
 * @author SN
 * @date 2023/08/24
 */
@Data
@TableName("cr_eval_template")
public class EvalTemplate extends AuthTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String name;

	private String description;

	private String logo;

	private String topoId;

	private Integer categoryCode;

	private String stepConfig;

	private String missionsScheme;

	private Boolean enableRecording;

	private String currentTaskId;

	private String createUserId;

	public void setLogoId(String logoId) {
		this.logo = logoId;
	}

	public List<String> getAuthIds() {
		return JsonUtil.toJsonObjectArray(this.getOperationAuthIds(), String.class);
	}

}
