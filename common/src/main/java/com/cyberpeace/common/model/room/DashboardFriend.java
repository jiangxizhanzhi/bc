package com.cyberpeace.common.model.room;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class DashboardFriend implements Serializable {

	/**
	 * 分组名称
	 */
	@ApiModelProperty("分组名称")
	private String groupname;

	/**
	 * 编号
	 */
	@ApiModelProperty("编号")
	private Integer id;

	/**
	 * 好友列表
	 */
	@ApiModelProperty("好友列表")
	private List<DashboardUser> list;

	public enum GROUP_NAME implements GenericEnum, BaseI18n {

		/***/
		FRIEND_LIST(1, "好友列表", "Friends List");

		@Getter
		private final Integer code;

		@Getter
		private final String chinese;

		@Getter
		private final String english;

		@Override
		public String getDesc() {
			return getMsg();
		}

		GROUP_NAME(Integer code, String desc, String enDesc) {
			this.code = code;
			this.chinese = desc;
			this.english = enDesc;
		}
	}
}
