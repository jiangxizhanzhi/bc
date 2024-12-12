package com.cyberpeace.common.model.ws;

import lombok.*;

import java.util.List;

/**
 * 聊天组
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatGroup {

	/**
	 * 用户id列表
	 */
	private List<String> userIds;
	/**
	 * 群id
	 */
	private String groupId;
	/**
	 * 群名称
	 */
	private String groupName;
	/**
	 * 岗位名称
	 */
	private String roleName;
}
