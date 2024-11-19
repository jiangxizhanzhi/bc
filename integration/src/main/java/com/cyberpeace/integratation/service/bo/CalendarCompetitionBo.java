package com.cyberpeace.integratation.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日历比赛Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class CalendarCompetitionBo implements Serializable {

	/**
	 * 赛事编号
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 赛事创建人
	 *
	 * @see String
	 */
	private String createUserId;

	/**
	 * 赛事名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 赛事状态
	 *
	 * @see Integer
	 */
	private Integer status;

	/**
	 * 赛事类型
	 *
	 * @see Integer
	 */
	private Integer type;

	/**
	 * 开始时间
	 *
	 * @see Date
	 */
	private Date startTime;

	/**
	 * 结束时间
	 *
	 * @see Date
	 */
	private Date endTime;

	/**
	 * 比赛分类
	 *
	 * @see Integer
	 */
	private Integer classify;

}
