package com.cyberpeace.integratation.service.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class RemoteUserInfos implements Serializable {

	/**
	 * 用户id
	 */
	private List<RemoteUserInfo> list_third_user_dtos;

	public RemoteUserInfos(List<RemoteUserInfo> userInfos) {
		this.list_third_user_dtos = userInfos;
	}

}
