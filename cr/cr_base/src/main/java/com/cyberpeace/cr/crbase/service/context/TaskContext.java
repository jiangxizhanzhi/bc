package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;

import lombok.Data;

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

	private Date reportTime;

	private Date taskStartTime;

	private String roomId;

	private String roleId;

	private Integer businessType;

	private Mission mission;

	public boolean isFinished() {
		return ArrayUtils.contains(CrTask.TaskStatus.getFINISHED_STATUS_LIST(), status);
	}

}
