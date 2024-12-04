package com.cyberpeace.oj.ojbase.service.context;

import com.cyberpeace.common.model.PatternBase;

import lombok.Data;

/**
 * 管理员备注列表查询
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PatternBase
 * @since 1.0.0
 */
@Data
public class AdminNoteListQuery extends PatternBase {

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	private String userName;

	/**
	 * 注释类型
	 *
	 * @see Integer
	 */
	private Integer noteType;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	private Integer businessType;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	private String businessId;

}
