package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.controller.req.LoadSetReq;
import lombok.Data;

import java.util.List;

@Data
@TableName("cr_eval_step_template")
public class EvalStepTemplate extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 阶段名称
	 */
	private String name;

	/**
	 * 验证过程
	 */
	private String process;

	/**
	 * 载荷设置
	 */
	private String setting;

	/**
	 * 云模板id
	 */
	private String cloudTemplateId;


	public void setSettingList(List<LoadSetReq> settingList) {
		this.setSetting(JsonUtil.toJsonString(settingList));
	}

	public List<LoadSetReq> getSettingList() {
		return JsonUtil.toJsonObjectArray(this.setting, LoadSetReq.class);
	}

}