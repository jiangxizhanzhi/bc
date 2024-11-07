package com.cyberpeace.common.model.ws;

import lombok.Data;

import java.util.List;

/**
 * Chat to Content （聊天到内容）
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class ChatToContent {

	/**
	 * 角色
	 */// 公共字段
	private String role;

	/**
	 * id
	 */
	private String id;

	/**
	 * 化身
	 */
	private String avatar;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 时间
	 */// 后面增加的服务器时间
	private String time;

	/**
	 * 组名
	 */// 分组独有
	private String groupname;

	/**
	 * 成员
	 */
	private List<String> members;

	/**
	 * 用户名
	 */// 好友独有
	private String username;

	/**
	 * 标志
	 */
	private String sign;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * uuid
	 */
	private String uuid;
}
