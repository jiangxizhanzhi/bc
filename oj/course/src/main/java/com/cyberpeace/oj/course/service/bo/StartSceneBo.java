package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * 开始场景博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class StartSceneBo implements Serializable {

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	private String classroomId;

	/**
	 * 群id
	 *
	 * @see String
	 */
	private String groupId;

	/**
	 * 语言
	 *
	 * @see String
	 */
	private String language;

	/**
	 * auth 上下文
	 *
	 * @see AuthContext
	 */
	private AuthContext authContext;

	/**
	 * 用户信息
	 *
	 * @see UserInfo
	 */
	private UserInfo userInfo;

}
