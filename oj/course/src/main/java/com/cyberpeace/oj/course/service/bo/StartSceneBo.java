package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class StartSceneBo implements Serializable {

	private String classroomId;

	private String groupId;

	private String language;

	private AuthContext authContext;

	private UserInfo userInfo;

}
