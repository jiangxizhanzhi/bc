package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Data;
import lombok.Getter;

@Data
public class ChartInfo {

	private Integer infoType;

	private List<RoleUsers> roleDataList;

	public enum ChartInfoType implements GenericEnum {

		RED_OR_BLUE_ONGOING(1, "红蓝实例启动状态"), RED_OR_BLUE_COMPLETE(2,
				"红蓝用户--对抗进行中"), NOT_RED_OR_BLUE_ONGOING(3,
						"红蓝用户--对抗完成"), NOT_RED_OR_BLUE_COMPLETE(4, "非红蓝用户--对抗完成");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private ChartInfoType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
