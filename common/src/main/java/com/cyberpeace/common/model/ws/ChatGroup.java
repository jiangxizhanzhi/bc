package com.cyberpeace.common.model.ws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 聊天组
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatGroup {

	/**
	 * 用户id列表
	 *
	 * @see List<String>
	 */
	private List<String> userIds;
	/**
	 * 群id
	 *
	 * @see String
	 */
	private String groupId;
	/**
	 * 群名称
	 *
	 * @see String
	 */
	private String groupName;
	/**
	 * 角色名称
	 *
	 * @see String
	 */
	private String roleName;
}
