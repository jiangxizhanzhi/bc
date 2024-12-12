package com.cyberpeace.cr.crbase.dao.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.constant.BaseI18n;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 靶场任务
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@TableName("cr_task")
public class CrTask extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 得分
	 */
	private Integer score;

	/**
	 * 任务id
	 */
	private String missionId;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 用户答案
	 */
	private String userAnswer;

	/**
	 * 发布时间
	 */
	private Date releaseTime;

	/**
	 * 上次提交时间
	 */
	private Date lastSubmitTime;

	/**
	 * 任务开始时间
	 */
	private Date taskStartTime;

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 岗位id
	 */
	private String roleId;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 任务模板id
	 */
	private String missionTemplateId;

	/**
	 * 备注
	 */
	private String remark;

	public Boolean isFinished() {
		boolean result = status.intValue() == TaskStatus.SUCCESS.getCode();
		result = result || status.intValue() == TaskStatus.FAIL.getCode();

		return result;
	}

	public Boolean isSubmitted() {
		boolean result = status.intValue() == TaskStatus.RELEASED.getCode();
		result = result || status.intValue() == TaskStatus.STARTED.getCode();
		return !result;
	}

	public Boolean isSuccess() {
		return TaskStatus.SUCCESS.getCode().equals(status) ;
	}
	/**
	 * task 单兵状态枚举
	 */
	public enum SingleTaskStatus implements GenericEnum, BaseI18n {

		/***/
		UNRELEASED(10, "未下发", "Unassigned"), RELEASED(11, "未提交", "Unsubmitted"), SUCCESS(1, "成功",
				"Successful"), FAIL(2, "失败", "Failed");

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
	public enum TaskStatus implements GenericEnum{

		UNRELEASED(10, "未下发"), RELEASED(11, "已下发"), STARTED(12, "已开始"), WAIT(0, "待评估"), SUCCESS(1,
				"成功"), FAIL(2, "失败"), REJECT(3, "驳回");

		public static List<Integer> FINISHED_STATUS_LIST = new ArrayList<>(
				Arrays.asList(SUCCESS.getCode(), FAIL.getCode()));

		public static List<Integer> UNSUBMITTED_STATUS_LIST = new ArrayList<>(
				Arrays.asList(RELEASED.getCode(), STARTED.getCode()));

		public static List<Integer> UNFINISHED_STATUS_LIST = new ArrayList<>(
				Arrays.asList(WAIT.getCode(), REJECT.getCode()));

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
