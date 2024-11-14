package com.cyberpeace.oj.ojbase.dao.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 笔记独特上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteUniqueContext implements Serializable {

	/**
	 * 笔记类型
	 */
	private Integer noteType;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 业务id
	 */
	private String businessId;

}
