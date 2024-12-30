package com.cyberpeace.cr.evaluation.dao.entity;
import com.cyberpeace.common.model.BaseRsp;
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
public class CombatLog  {


	private String sceneId;
	/**
	 * 攻击类型
	 */
	private String eventType;

	/**
	 * 告警名称
	 */
	private String eventName;
	/**
	 * 设备名称
	 */
	private String hostName;
	/**
	 * 攻击时间
	 */
	private Date eventTime;

	/**
	 * 告警等级
	 */
	private Integer grade;

	private String originalLog;

	/**
	 * 告警匹配规则名称
	 */
	private String alarmMatchName;

}
