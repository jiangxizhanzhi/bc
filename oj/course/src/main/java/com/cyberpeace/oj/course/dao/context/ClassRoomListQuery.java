package com.cyberpeace.oj.course.dao.context;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 教室列表查询
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class ClassRoomListQuery implements Serializable {

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 组织id
	 */
	private String orgId;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 子级组织
	 */
	private List<String> childrenOrg;

	/**
	 * 训练计划id
	 */
	private String trainPlanId;

}
