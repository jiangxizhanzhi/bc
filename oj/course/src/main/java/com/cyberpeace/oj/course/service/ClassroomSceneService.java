package com.cyberpeace.oj.course.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.AuthContext;

public interface ClassroomSceneService {

	/**
	 * 课堂组场景启动
	 * @param groupId
	 * @param authContext
	 * @return
	 */
	JSONObject startScene(String groupId, AuthContext authContext);

	/**
	 * 课堂组场景删除
	 * @param groupId
	 * @param authContext
	 * @return
	 */
	Boolean deleteScene(String groupId, AuthContext authContext);
}
