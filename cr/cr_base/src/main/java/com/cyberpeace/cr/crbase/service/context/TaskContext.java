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
 */
@Data
public class TaskContext extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private Integer score;

	private String missionId;

	private Integer status;

	private String userId;

	private String userAnswer;

	private Date releaseTime;

	private Date lastSubmitTime;

	private Date taskStartTime;

	private String roomId;

	private String roleId;

	private Integer businessType;

	private Mission mission;

	private String remark;

	public boolean isFinished() {
		return CrTask.TaskStatus.FINISHED_STATUS_LIST.contains(status);
	}

}
