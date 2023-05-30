package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * 节点简要信息
 */
@Data
public class CrRoleNode implements Serializable {

	@ApiModelProperty(value = "节点id")
	private String id;

	@ApiModelProperty(value = "节点类型")
	private Integer category;

	public enum CategoryGroup implements GenericEnum, BaseI18n {

		/*应用系统：1 操作系统:2 网路设备:3 安防设备:4**/
		APPLICATION(1, "应用系统", "APPLICATION"),
		OPERATING(2, "操作系统", "OPERATING"),
		NETWORK(3, "网路设备", "NETWORK"),
		SECURITY(4, "安防设备", "SECURITY");

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

		public boolean equals(Integer roleGroupCode) {
			return getCode().equals(roleGroupCode);
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
