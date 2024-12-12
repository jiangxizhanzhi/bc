package com.cyberpeace.integratation.model;

import com.cyberpeace.common.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户分页上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
