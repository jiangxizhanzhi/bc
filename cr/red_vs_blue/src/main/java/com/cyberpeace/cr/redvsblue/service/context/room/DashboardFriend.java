package com.cyberpeace.cr.redvsblue.service.context.room;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.CommonContext;

import lombok.Data;
import lombok.Getter;

@Data
public class DashboardFriend {

	private String groupname;

	private Integer id;

	private List<DashboardUser> list;

	public enum GROUP_NAME implements GenericEnum {

		FRIEND_LIST(1, "好友列表", "Friends List");

		@Getter
		private Integer code;

		private String desc;

		private String enDesc;

		@Override
		public String getDesc() {
			if (StringUtils.equals(CommonContext.getLanguage(), WebConstant.LANGUAGE_EN)) {
				return enDesc;
			} else {
				return desc;
			}
		}

		GROUP_NAME(Integer code, String desc, String enDesc) {
			this.code = code;
			this.desc = desc;
			this.enDesc = enDesc;

		}
	}
}
