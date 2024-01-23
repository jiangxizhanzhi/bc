package com.cyberpeace.cr.redvsblue.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cyberpeace.common.model.BaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("cr_rb_combat_log")
public class RbCombatLog extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String roomId;

	private Integer eventType;

	private Integer eventCategory;

	private Integer attackPhase;

	private Integer defensePhase;

	private Integer attackAction;

	private Integer defenseAction;

	private Integer actionType;

	private Boolean isLostHost;

	private Integer sourceGroup;

	private String sourceIp;

	private String sourceName;

	private Integer destGroup;

	private String destIp;

	private String destName;

	private String agentName;

	private String agentIp;

	private String eventContent;

	private Date eventTime;

	private String originalLog;

	private String mainLogId;

}
