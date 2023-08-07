package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
    * 下发flag任务信息
    */
@TableName(value = "cr_flag_task")
@Data
public class CrFlagTask extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String missionId;

	private String taskId;

	private String attachmentId;

	private String flagValue;

}