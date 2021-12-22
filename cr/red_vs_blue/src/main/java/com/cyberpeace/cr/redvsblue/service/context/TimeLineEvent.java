package com.cyberpeace.cr.redvsblue.service.context;

import java.util.Date;

import lombok.Data;
import lombok.Getter;

@Data
public class TimeLineEvent {

	private Integer roleGroup;

	private Date time;

	private String title;

	private String userName;

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
