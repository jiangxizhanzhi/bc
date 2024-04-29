package com.cyberpeace.cr.evaluation.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

@Data
@TableName("cr_eval_task")
public class EvalTask extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String templateId;

	private String templateName;

	private String taskName;

	private String localSceneId;

	private String mpSceneId;

	private String phase;

	private String copyedTopoId;

	private Date startTime;

	private Date endTime;

	private Boolean enableRecording;

	private String createUserId;

	public enum Status implements GenericEnum {

		RUNNING(1, "运行中"), FINISHED(2, "已结束");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		Status(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
