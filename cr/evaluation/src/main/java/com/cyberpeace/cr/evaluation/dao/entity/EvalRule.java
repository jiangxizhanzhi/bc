package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@TableName("cr_eval_rule")
public class EvalRule extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 规则名称
	 */
	private String name;

	/**
	 * 规则描述
	 */
	private String description;

	/**
	 * 试验id
	 */
	private String testId;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 标签
	 */
	private String labels;

	/**
	 * 等级
	 */
	private Integer grade;

	/**
	 * 布尔关系
	 */
	private Integer booleanRelation;

	/**
	 * 关联步骤id
	 */
	private String stepIds;

	/**
	 * 创建者id
	 */
	private String createUserId;


	public List<String> getStepIdList() {
		if (StringUtils.isBlank(stepIds)) {
			return new ArrayList<>();
		}
		return JsonUtil.toJsonObjectArray(stepIds, String.class);
	}

	public void setStepIdList(List<String> stepIdList) {
		stepIds = JsonUtil.toJsonString(stepIdList);
	}
}