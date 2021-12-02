package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;
import java.util.Map;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.google.common.collect.Maps;

public class SceneMpContext implements Serializable {

	private String access_user_id = UserInfoContext.getUserContext().getUserId();

	private Map<String, String> pass_through = Maps.newHashMap();

	public void setAccessUserId(String access_user_id) {
		this.access_user_id = access_user_id;
	}

	public String getAccess_user_id() {
		return access_user_id;
	}

	public void setPassThrough(Map<String, String> pass_through) {
		this.pass_through = pass_through;
	}

	public Map<String, String> getPass_through() {
		return pass_through;
	}
}