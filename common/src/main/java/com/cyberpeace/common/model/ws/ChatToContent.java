package com.cyberpeace.common.model.ws;

import lombok.Data;

import java.util.List;

/**
 * Chat to Content （聊天到内容）
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class ChatToContent {

	/**
	 * 角色
	 *
	 * @see String
	 */ // 公共字段
	private String role;

	/**
	 * id
	 *
	 * @see String
	 */
	private String id;

	/**
	 * 化身
	 *
	 * @see String
	 */
	private String avatar;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 类型
	 *
	 * @see String
	 */
	private String type;

	/**
	 * 时间
	 *
	 * @see String
	 */ // 后面增加的服务器时间
	private String time;

	/**
	 * 组名
	 *
	 * @see String
	 */ // 分组独有
	private String groupname;

	/**
	 * 成员
	 *
	 * @see List<String>
	 */
	private List<String> members;

	/**
	 * 用户名
	 *
	 * @see String
	 */ // 好友独有
	private String username;

	/**
	 * 标志
	 *
	 * @see String
	 */
	private String sign;

	/**
	 * 状态
	 *
	 * @see String
	 */
	private String status;

	/**
	 * uuid
	 *
	 * @see String
	 */
	private String uuid;
}
