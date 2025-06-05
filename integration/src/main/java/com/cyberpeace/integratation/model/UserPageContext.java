package com.cyberpeace.integratation.model;

import java.io.Serializable;
import java.sql.DatabaseMetaData;

import com.cyberpeace.common.constant.CommonConstant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户分页上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPageContext implements Serializable {

	/**
	 * 用户名
	 *
	 * @see String
	 */
	private String username;

	/**
	 * id列表
	 *
	 * @see String
	 */
	private String ids;

	/**
	 * 每分页
	 *
	 * @see Integer
	 */
	private Integer perPage;

	/**
	 * 分页
	 *
	 * @see Integer
	 */
	private Integer page;

	public Integer getPerPage() {
		return null == perPage ? CommonConstant.NINE_NINE_NINE : perPage;
	}

	public Integer getPage() {
		return null == page ? CommonConstant.ONE : page;
	}
}
