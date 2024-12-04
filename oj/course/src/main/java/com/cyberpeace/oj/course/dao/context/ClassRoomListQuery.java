package com.cyberpeace.oj.course.dao.context;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 教室列表查询
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ClassRoomListQuery implements Serializable {

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
	 * 组织id
	 *
	 * @see String
	 */
	private String orgId;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 子级组织
	 *
	 * @see List<String>
	 */
	private List<String> childrenOrg;

	/**
	 * 训练计划id
	 *
	 * @see String
	 */
	private String trainPlanId;

}
