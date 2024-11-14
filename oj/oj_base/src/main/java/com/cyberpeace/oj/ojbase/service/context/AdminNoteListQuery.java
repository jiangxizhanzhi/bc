package com.cyberpeace.oj.ojbase.service.context;

import com.cyberpeace.common.model.PatternBase;

import lombok.Data;

/**
 * 管理笔记列表查询
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class AdminNoteListQuery extends PatternBase {

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 笔记类型
	 */
	private Integer noteType;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 业务id
	 */
	private String businessId;

}
