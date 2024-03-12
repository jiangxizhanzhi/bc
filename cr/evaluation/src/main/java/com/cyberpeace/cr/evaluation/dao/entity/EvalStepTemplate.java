package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.controller.req.LoadSetReq;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
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
		if (this.setting == null) {
			return new ArrayList<>();
		} else {
			return JsonUtil.toJsonObjectArray(this.setting, LoadSetReq.class);
		}
	}


//	界面类型
	public enum InterfaceType implements GenericEnum {

		COMMAND(0, "命令行"),
		UI(1, "UI界面"),
		WEB(2, "WEB界面"),
		API(3, "restful API");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		InterfaceType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}


}