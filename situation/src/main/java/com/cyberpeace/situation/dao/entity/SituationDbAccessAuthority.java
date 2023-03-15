package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("situation_db_access_authority")
public class SituationDbAccessAuthority extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	private String name;

	private String dbType;

	private String sysIp;

	private String createUserId;

}
