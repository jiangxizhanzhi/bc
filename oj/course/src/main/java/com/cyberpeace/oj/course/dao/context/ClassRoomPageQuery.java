package com.cyberpeace.oj.course.dao.context;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.common.model.PageReqBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 类房间分页查询
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Getter
@Setter
public class ClassRoomPageQuery extends PageReqBase {


	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 训练计划id
	 *
	 * @see String
	 */
	private String trainPlanId;

	/**
	 * 组织id
	 *
	 * @see String
	 */
	private String organizationId;

	/**
	 * 子级组织
	 *
	 * @see List<String>
	 */
	private List<String> childrenOrg;

}
