package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;

import lombok.Data;

/**
 * 任务模型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@Data
public class TaskContext extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	private Integer score;

	/**
	 * 任务id
	 *
	 * @see String
	 */
	private String missionId;

	/**
	 * 状态
	 *
	 * @see Integer
	 */
	private Integer status;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 用户答案
	 *
	 * @see String
	 */
	private String userAnswer;

	/**
	 * 发布时间
	 *
	 * @see Date
	 */
	private Date releaseTime;

	/**
	 * 上次提交时间
	 *
	 * @see Date
	 */
	private Date lastSubmitTime;

	/**
	 * 任务开始时间
	 *
	 * @see Date
	 */
	private Date taskStartTime;

	/**
	 * 房间id
	 *
	 * @see String
	 */
	private String roomId;

	/**
	 * 角色id
	 *
	 * @see String
	 */
	private String roleId;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	private Integer businessType;

	/**
	 * 任务
	 *
	 * @see Mission
	 */
	private Mission mission;

	/**
	 * 备注
	 *
	 * @see String
	 */
	private String remark;

	public boolean isFinished() {
		return CrTask.TaskStatus.FINISHED_STATUS_LIST.contains(status);
	}

}
