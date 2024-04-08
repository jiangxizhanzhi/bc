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
@TableName("cr_eval_step")
public class EvalStep extends BaseTable {

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
	 * 试验id
	 */
	private String testId;

	/**
	 * 步骤序号
	 */
	private Integer stage;

	public List<LoadSetReq> getLoadList() {
		return JsonUtil.toJsonObjectArray(setting, LoadSetReq.class);
	}

}