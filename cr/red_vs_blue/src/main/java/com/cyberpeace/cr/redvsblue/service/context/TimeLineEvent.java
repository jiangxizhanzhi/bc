package com.cyberpeace.cr.redvsblue.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * 红蓝时间线事件
 */
@Data
public class TimeLineEvent {

	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	@ApiModelProperty(value = "事件")
	private Date time;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "头像地址")
	private String avatarUrl;

	@ApiModelProperty(value = "类型")
	private Integer type;

	public enum SendDataType {

		SEND_DATA_IS_MISSION(1, "下发任务"), SEND_DATA_IS_TRAFFIC(2, "下发流量");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private SendDataType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
