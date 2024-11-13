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
 * @date 2024/11/13
 */
@Data
public class TaskContext extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 得分
	 */
	private Integer score;

	/**
	 * 任务id
	 */
	private String missionId;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 用户答案
	 */
	private String userAnswer;

	/**
	 * 下发时间
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
	 * 任务
	 */
	private Mission mission;

	/**
	 * 备注
	 */
	private String remark;

	public boolean isFinished() {
		return CrTask.TaskStatus.FINISHED_STATUS_LIST.contains(status);
	}

}
