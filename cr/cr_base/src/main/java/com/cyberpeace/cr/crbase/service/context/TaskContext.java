package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.cr.crbase.dao.entity.Mission;

import lombok.Data;

@Data
@TableName("cr_task")
public class TaskContext extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String missionId;

	private String userId;

	private String userAnswer;

	private Date releaseTime;

	private Date reportTime;

	private Date taskStartTime;

	private String roomId;

	private String roleId;

	private Integer businessType;

	private Mission mission;
}
