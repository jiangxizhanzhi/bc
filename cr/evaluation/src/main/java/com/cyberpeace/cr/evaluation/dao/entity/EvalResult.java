package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("cr_eval_result")
public class EvalResult extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 评估运行实例id
	 */
	private String record;

	private Integer weight;

	private Integer indexScore;

	private Integer finalScore;

	private Integer type;

	public enum Type implements GenericEnum {
		MANUAL_SYSTEM(1, "人工改判"),
		SYSTEM_RUN(2, "重新评估");

		@Getter
		private final Integer code;
		@Getter
		private final String desc;

		Type(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
