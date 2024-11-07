package com.cyberpeace.cr.crbase.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

/**
 * 靶场任务提交
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@TableName("cr_task_submit")
public class CrTaskSubmit extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 任务id
	 */
	private String taskId;

	/**
	 * 答案
	 */
	private String answer;

	/**
	 * 答案外延
	 */
	private String answerExtension;

	/**
	 * 计算器id
	 */
	private String evaluatorId;

	/**
	 * 评估时间
	 */
	private Date evaluateTime;

	/**
	 * 评估状态
	 */
	private Integer evaluateStatus;

	/**
	 * 原因
	 */
	private Integer reason;

	/**
	 * 详情
	 */
	private String detail;

	/**
	 * 得分
	 */
	private Integer score;

	/**
	 * 任务分数
	 */
	private Integer missionScore;

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
