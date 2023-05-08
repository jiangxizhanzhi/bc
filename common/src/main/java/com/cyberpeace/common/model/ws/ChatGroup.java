package com.cyberpeace.common.model.ws;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天群
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatGroup {

	private List<String> userIds;

	private String groupId;

	private String groupName;

	private String roleName;
}
