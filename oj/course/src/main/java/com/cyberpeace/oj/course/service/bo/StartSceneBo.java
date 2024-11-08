package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * 开始场景博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class StartSceneBo implements Serializable {

	/**
	 * 课堂id
	 */
	private String classroomId;

	/**
	 * 群id
	 */
	private String groupId;

	/**
	 * 语言
	 */
	private String language;

	/**
	 * auth 上下文
	 */
	private AuthContext authContext;

	/**
	 * 用户信息
	 */
	private UserInfo userInfo;

}
