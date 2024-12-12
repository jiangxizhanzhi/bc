package com.cyberpeace.common.model.room;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 仪表板好友
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class DashboardFriend implements Serializable {

	/**
	 * 组名
	 */
	@ApiModelProperty("分组名称")
	private String groupname;

	/**
	 * id
	 */
	@ApiModelProperty("编号")
	private Integer id;

	/**
	 * 列表
	 */
	@ApiModelProperty("好友列表")
	private List<DashboardUser> list;

	/**
	 * 群名称
	 *
	 * @author SN
	 * @date 2024/11/08
	 */
	public enum GROUP_NAME implements GenericEnum, BaseI18n {

		/**
		 * 好友列表
		 */
		FRIEND_LIST(1, "好友列表", "Friends List");

		/**
		 * 代号
		 */
		@Getter
		private final Integer code;

		/**
		 * 中文
		 */
		@Getter
		private final String chinese;

		/**
		 * 英语
		 */
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
