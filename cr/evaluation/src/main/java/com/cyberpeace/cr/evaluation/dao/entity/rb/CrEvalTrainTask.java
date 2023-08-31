package com.cyberpeace.cr.evaluation.dao.entity.rb;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.constant.BaseI18n;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@TableName("cr_eval_train_task")
public class CrEvalTrainTask extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private Integer status;

	private Integer score;

	private String missionId;

	private String userId;

	private String userAnswer;

	private Date releaseTime;

	private Date lastSubmitTime;

	private Date taskStartTime;

	private String roomId;

	private String roleId;

	private Integer businessType;

	private String missionTemplateId;

	private String evalTemplateId;

	public Boolean isFinished() {
		boolean result = status.intValue() == TaskStatus.SUCCESS.getCode();
		result = result || status.intValue() == TaskStatus.FAIL.getCode();

		return result;
	}

	/**
	 * task 单兵状态枚举
	 */
	public enum SingleTaskStatus implements GenericEnum, BaseI18n {

		/***/
		UNRELEASED(10, "未下发", "Unassigned"),
		RELEASED(11, "未提交", "Unsubmitted"),
		SUCCESS(1, "成功", "Successful"),
		FAIL(2, "失败", "Failed");

		@Getter
		private final Integer code;

		@Getter
		private final String chinese;

		@Getter
		private final String english;

		SingleTaskStatus(Integer code, String desc, String enDesc) {
			this.code = code;
			this.chinese = desc;
			this.english = enDesc;
		}

		@Override
		public String getDesc() {
			return getMsg();
		}

	}

	/**
	 * task 状态枚举
	 */
	public enum TaskStatus {

		UNRELEASED(10, "未下发"),
		RELEASED(11, "已下发"),
		STARTED(12, "已开始"),
		WAIT(0, "待评估"),
		SUCCESS(1, "成功"),
		FAIL(2, "失败"),
		REJECT(3, "驳回"),
		PAUSE(4, "暂停"), // 未使用
		CLOSE(5, "关闭");

		public static List<Integer> FINISHED_STATUS_LIST = new ArrayList<>(
				Arrays.asList(SUCCESS.getCode(), FAIL.getCode()));

		public static List<Integer> UNSUBMITTED_STATUS_LIST = new ArrayList<>(
				Arrays.asList(RELEASED.getCode(), STARTED.getCode()));

		public static List<Integer> UNFINISHED_STATUS_LIST = new ArrayList<>(
				Arrays.asList(WAIT.getCode(), REJECT.getCode()));

		public static List<Integer> ONGOING_STATUS_LIST = new ArrayList<>(
				Arrays.asList(RELEASED.getCode(), STARTED.getCode(), WAIT.getCode(), REJECT.getCode()));

		@Getter
		private Integer code;

		@Getter
		private String desc;

		TaskStatus(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
