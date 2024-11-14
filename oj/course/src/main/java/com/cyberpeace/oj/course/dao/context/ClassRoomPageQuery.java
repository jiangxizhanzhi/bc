package com.cyberpeace.oj.course.dao.context;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.common.model.PageReqBase;
import lombok.Data;

/**
 * 类房间分页查询
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class ClassRoomPageQuery extends PageReqBase {


	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 训练计划id
	 */
	private String trainPlanId;

	/**
	 * 组织id
	 */
	private String organizationId;

	/**
	 * 子级组织
	 */
	private List<String> childrenOrg;

}
