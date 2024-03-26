package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("cr_eval_action_template")
public class EvalActionTemplate extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 云模板id
	 */
	@ApiModelProperty("云模板id")
	private String cloudTemplateId;

	/**
	 * 关联的规则id
	 */
	@ApiModelProperty("关联的规则id")
	private String ruleId;

	/**
	 * 事件名称
	 */
	@ApiModelProperty("事件名称")
	private String name;

	/**
	 * 动作类型
	 */
	@ApiModelProperty("动作类型")
	private Integer actionType;

	/**
	 * 告警事件级别
	 */
	@ApiModelProperty("告警事件级别")
	private Integer level;

	/**
	 * 告警描述
	 */
	@ApiModelProperty("告警描述")
	private String description;

	/**
	 * 攻击类型级别
	 */
	@ApiModelProperty("攻击类型级别")
	private Integer attackType;

	/**
	 * 攻击源节点id
	 */
	@ApiModelProperty("攻击源节点id")
	private String sourceId;

	/**
	 * 攻击目标节点id
	 */
	@ApiModelProperty("攻击目标节点id")
	private String targetId;

	/**
	 * 攻击结果
	 */
	@ApiModelProperty("攻击结果")
	private Integer result;


//	动作类型
	public enum ActionType implements GenericEnum {

		ALARM(0, "告警事件"),
		SITUATION(1, "态势展示");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		ActionType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

//	级别
	public enum Level implements GenericEnum {

		TIP(0, "提示"),
		GENERAL(1, "一般"),
		SERIOUS(2, "严重");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		Level(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

//	结果
	public enum Result implements GenericEnum {

		FAIL(0, "失败"),
		SUCCESS(1, "成功"),
		UNKNOWN(2, "未知");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		Result(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}