package com.cyberpeace.common.context.role;

import java.io.Serializable;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * 节点简要信息
 */
@Data
public class CrRoleNode implements Serializable {

	/**
	 * 节点id
	 */
	@ApiModelProperty(value = "节点id")
	private String id;

	/**
	 * 节点类型
	 */
	@ApiModelProperty(value = "节点类型")
	private Integer category;

	public enum CategoryGroup implements GenericEnum, BaseI18n {

		/*应用系统：1 操作系统:2 网路设备:3 安防设备:4**/
		OPERATING(1, "操作系统", "OPERATING"),
		APPLICATION(2, "应用系统", "APPLICATION"),
		NETWORK(3, "网络设备", "NETWORK"),
		SECURITY(4, "安防设备", "SECURITY"),
		LAYER_NODE(5, "层节点","LAYER_NODE");

		@Getter
		private final Integer code;

		@Getter
		private final String chinese;

		@Getter
		private final String english;

		CategoryGroup(Integer code, String chinese, String english) {
			this.code = code;
			this.chinese = chinese;
			this.english = english;
		}

		@Override
		public String getChinese() {
			return this.chinese;
		}

		@Override
		public String getEnglish() {
			return this.english;
		}

		@Override
		public String getDesc() {
			return this.getMsg();
		}
	}
}
