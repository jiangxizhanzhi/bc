package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("rtks_daily_match_log")
public class DailyMatchLog extends BaseTable {

	private String userId;

	private String roleId;

	private Integer match;
}
