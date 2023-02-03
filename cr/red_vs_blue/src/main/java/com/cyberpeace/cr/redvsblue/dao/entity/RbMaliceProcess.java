package com.cyberpeace.cr.redvsblue.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("cr_rb_malice_process")
public class RbMaliceProcess extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String roomId;

	private String machineId;

	private String process;

	public RbMaliceProcess(String roomId, String machineId, String process) {
		this.roomId = roomId;
		this.machineId = machineId;
		this.process = process;
	}
}
