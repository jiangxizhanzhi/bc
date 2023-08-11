package com.cyberpeace.cr.evaluation.dao.entity.rb;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@TableName("cr_eval_task_submit")
public class CrEvalTaskSubmit extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String roomId;

	private String taskId;

	private String answer;

	private String answerExtension;

	private String evaluatorId;

	private Date evaluateTime;

	private Integer evaluateStatus;

	private Integer reason;

	private String detail;

	private Integer score;

	private Integer missionScore;

	private String evalTemplateId;

	public enum EvaluateStatus {

		WAIT(0, "待评估"), SUCCESS(1, "成功"), FAIL(2, "失败"), REJECT(3, "驳回"), TASK_NOT_SUBMITTED(4,
				"未交卷");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		EvaluateStatus(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public enum EvaluateType {

		MACHINE_EVALUATION(0, "机器评估"), MANUAL_EVALUATION(1, "人工评估");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		EvaluateType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
