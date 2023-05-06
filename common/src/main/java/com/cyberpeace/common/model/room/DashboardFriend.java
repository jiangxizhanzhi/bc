package com.cyberpeace.common.model.room;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * 仪表板好友
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class DashboardFriend implements Serializable {

	@ApiModelProperty("分组名称")
	private String groupname;

	@ApiModelProperty("编号")
	private Integer id;

	@ApiModelProperty("好友列表")
	private List<DashboardUser> list;

	/**
	 * 组名称
	 *
	 * @author cyberpeace
	 * @date 2023/05/06
	 */
	public enum GROUP_NAME implements GenericEnum, BaseI18n {

		/***/
		FRIEND_LIST(1, "好友列表", "Friends List");

		@Getter
		private final Integer code;

		@Getter
		private final String chinese;

		@Getter
		private final String english;

		/**
		 * 得到desc
		 *
		 * @return {@link String}
		 */
		@Override
		public String getDesc() {
			return getMsg();
		}

		/**
		 * 组名称
		 *
		 * @param code   代码
		 * @param desc   desc
		 * @param enDesc en desc
		 */
		GROUP_NAME(Integer code, String desc, String enDesc) {
			this.code = code;
			this.chinese = desc;
			this.english = enDesc;
		}
	}
}
