package com.cyberpeace.cr.evaluation.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@TableName("cr_eval_task_step")
public class EvalTaskStep extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String taskId;

	private Integer stage;

	private String title;

	private String process;

	private Integer type;

	private Date startTime;

	private Date endTime;

	public enum Type implements GenericEnum {

		MANUAL(1, "手动"), AUTO(2, "自动");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		Type(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
