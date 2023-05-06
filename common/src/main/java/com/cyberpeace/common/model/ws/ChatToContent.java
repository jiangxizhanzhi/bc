package com.cyberpeace.common.model.ws;

import java.util.List;

import lombok.Data;

/**
 * 聊天内容
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class ChatToContent {

	// 公共字段
	private String role;

	private String id;

	private String avatar;

	private String name;

	private String type;

	// 后面增加的服务器时间
	private String time;

	// 分组独有
	private String groupname;

	private List<String> members;

	// 好友独有
	private String username;

	private String sign;

	private String status;
}
