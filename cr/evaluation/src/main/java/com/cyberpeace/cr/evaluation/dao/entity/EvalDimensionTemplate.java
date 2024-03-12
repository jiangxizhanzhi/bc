package com.cyberpeace.cr.evaluation.dao.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("cr_eval_dimension_template")
public class EvalDimensionTemplate extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 事件名称
	 */
	private String name;

	/**
	 * 事件类型
	 */
	private Integer eventType;

	/**
	 * 规则参数
	 */
	private String ruleParameter;

	/**
	 * 关联的规则id
	 */
	private String ruleId;

	/**
	 * 云模板id
	 */
	private String cloudTemplateId;


	public void setRuleParameter(Operate operate, String object, String value) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("operate", operate.key);
		jsonObject.put("name", object);
		jsonObject.put("value", value);
		this.ruleParameter = jsonObject.toJSONString();
	}

//	类型
	public enum Type implements GenericEnum {

		HOST(0, "主机");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		Type(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}


//	事件类型
	public enum EventType implements GenericEnum {

		FILE(0, "文件"),
		REGISTRY(1, "注册表"),
		PORT(2, "服务端口"),
		PROCESS(3, "进程"),
		LOG(4, "日志"),
		PROBE(5, "自定义探针-135");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		EventType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

//	操作类型
	public enum Operate implements GenericEnum {

		MATE(0, "匹配", "match"),
		ADD(1, "增加", "add"),
		DELETE(2, "删除", "delete"),
		MODIFY(3, "修改", "modify");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		@Getter
		private final String key;

		Operate(Integer code, String desc, String key) {
			this.code = code;
			this.desc = desc;
			this.key = key;
		}
	}

}