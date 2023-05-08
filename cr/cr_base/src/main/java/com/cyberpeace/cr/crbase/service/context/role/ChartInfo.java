package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import com.cyberpeace.common.baseinterface.GenericEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**图表信息
 * 角色图表
 @author cyberpeace
 @date 2023/05/06
 */
@Data
public class ChartInfo {

	@ApiModelProperty(value = "用户对抗状态")
	private Integer infoType;

	@ApiModelProperty(value = "角色用户列表")
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
