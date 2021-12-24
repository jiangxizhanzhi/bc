package com.cyberpeace.cr.redvsblue.service.context.room;

import java.util.List;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Data;
import lombok.Getter;

@Data
public class DashboardFriend {

	private String groupname;

	private Integer id;

	private List<DashboardUser> list;

	public enum GROUP_NAME implements GenericEnum {

		FRIEND_LIST(1, "好友列表");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		GROUP_NAME(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
