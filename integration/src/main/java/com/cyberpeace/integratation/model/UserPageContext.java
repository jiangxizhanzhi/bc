package com.cyberpeace.integratation.model;

import java.io.Serializable;

import com.cyberpeace.common.constant.CommonConstant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPageContext implements Serializable {

	private String username;

	private String ids;

	private Integer perPage;

	private Integer page;

	private String org;

	public Integer getPerPage() {
		return null == perPage ? CommonConstant.NINE_NINE_NINE : perPage;
	}

	public Integer getPage() {
		return null == page ? CommonConstant.ONE : page;
	}
}
