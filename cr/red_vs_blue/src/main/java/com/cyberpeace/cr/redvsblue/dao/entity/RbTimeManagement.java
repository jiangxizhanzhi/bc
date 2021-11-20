package com.cyberpeace.cr.redvsblue.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("cr_rb_time_management")
public class RbTimeManagement extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String roomId;

	private Date astronomicalTime;

	private Date combatTime;

	private Integer rate;

	private Integer status;

	private String createUserId;

	private String updateUserId;
}
