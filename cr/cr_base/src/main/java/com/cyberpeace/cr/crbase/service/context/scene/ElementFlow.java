package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;

@Data
public class ElementFlow implements Serializable {

	private String id;

	private String name;

	private String desc;

	private String type;

	private Integer category;

	private Integer interactiveType;

	private Integer commonStatus = 0 ;

	private Integer status;

	private String statusText;

	private String taskId;

	private Integer count;

	private String sourceIp;

	public enum TaskStatusEnum {

		UNDEFINED(-1,"未定义"),
		INIT(0, "初始化"),
		RUNNING(1, "运行中"),
		STOPPING(2, "停止中"),
		STOPPED(3, "已停止"),
		ZERO(4, "未启动过");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		TaskStatusEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public enum CommonStatusEnum {

		FREE(0, "空闲"),
		ACTIVE(1, "活动");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		CommonStatusEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

	public void setCommonStatus(Integer taskStatus) {

		Integer commonStatus = CommonStatusEnum.ACTIVE.getCode();

		if (taskStatus.equals(TaskStatusEnum.STOPPED.getCode())
				|| taskStatus.equals(TaskStatusEnum.ZERO.getCode())) {
			commonStatus = CommonStatusEnum.FREE.getCode();
		}
		this.commonStatus = commonStatus;
	}

}
