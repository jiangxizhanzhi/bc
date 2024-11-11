package com.cyberpeace.integratation.model;

import java.io.Serializable;

import com.cyberpeace.common.constant.CommonConstant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户分页上下文
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPageContext implements Serializable {

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * id列表
	 */
	private String ids;

	/**
	 * 每分页
	 */
	private Integer perPage;

	/**
	 * 分页
	 */
	private Integer page;

	public Integer getPerPage() {
		return null == perPage ? CommonConstant.NINE_NINE_NINE : perPage;
	}

	public Integer getPage() {
		return null == page ? CommonConstant.ONE : page;
	}
}
