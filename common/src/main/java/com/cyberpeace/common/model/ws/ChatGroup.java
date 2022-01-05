package com.cyberpeace.common.model.ws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
