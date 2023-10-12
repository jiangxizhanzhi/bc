package com.cyberpeace.cr.redvsblue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

import java.util.Date;

@Data
@TableName("cr_rb_time")
public class RbTime extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String roomId;

	private Date astronomicalTime;

	private String combatTime;

	private Integer rate;

}
