package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;
import java.util.Map;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.google.common.collect.Maps;

import lombok.Data;

@Data
public class SceneMpContext implements Serializable {

	private String accessUserId = UserInfoContext.getUserContext().getUserId();

	private Map<String, String> passThrough = Maps.newHashMap();

}