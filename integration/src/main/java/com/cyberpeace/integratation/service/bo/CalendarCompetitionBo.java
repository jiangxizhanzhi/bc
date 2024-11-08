package com.cyberpeace.integratation.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日历竞赛 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class CalendarCompetitionBo implements Serializable {

	/**
	 * 赛事编号
	 */
	private String resourceId;

	/**
	 * 赛事创建人
	 */
	private String createUserId;

	/**
	 * 赛事名称
	 */
	private String name;

	/**
	 * 赛事状态
	 */
	private Integer status;

	/**
	 * 赛事类型
	 */
	private Integer type;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 比赛分类
	 */
	private Integer classify;

}
