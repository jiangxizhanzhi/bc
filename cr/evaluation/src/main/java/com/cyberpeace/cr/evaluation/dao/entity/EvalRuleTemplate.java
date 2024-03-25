package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@TableName("cr_eval_rule_template")
public class EvalRuleTemplate extends BaseTable {

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
	 * 云模板id
	 */
	private String cloudTemplateId;

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
	 * 关联步骤id列表
	 */
	private String stepIds;

	/**
	 * 创建人
	 */
	private String createUserId;

	public void addStepId(String stepId) {
		List<String> stepIdList = getStepIdList();
		stepIdList.add(stepId);
		stepIds = JsonUtil.toJsonString(stepIdList);
	}

	public List<String> getStepIdList() {
		if (StringUtils.isEmpty(stepIds)) {
			return new ArrayList<>();
		} else {
			return JsonUtil.toJsonObjectArray(stepIds, String.class);
		}
	}

	public void setStepIdList(List<String> tasks) {
		stepIds = JsonUtil.toJsonString(tasks);
	}

	public void setLabelList (List<String> labelList) {
		this.labels = JsonUtil.toJsonString(labelList);
	}

	public List<String> getLabelList () {
		if (StringUtils.isEmpty(this.labels)) {
			return new ArrayList<>();
		} else {
			return JsonUtil.toJsonObjectArray(this.labels, String.class);
		}
	}


//	状态
	public enum Status implements GenericEnum {

		CLOSE(0, "关闭"),
		OPEN(1, "打开");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		Status(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

//	等级
	public enum Grade implements GenericEnum {

		LOW(0, "低"),
		MEDIUM(1, "中"),
		HIGH(2, "高");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		Grade(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

//	布尔关系
	public enum BooleanRelation implements GenericEnum {

		OR(0, "或"),
		AND(1, "与");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		BooleanRelation(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}



}