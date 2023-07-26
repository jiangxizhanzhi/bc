package com.cyberpeace.common.context.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class ElementFlow implements Serializable {

	@ApiModelProperty("ID")
	private String id;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("描述")
	private String desc;

	@ApiModelProperty("类型")
	private String type;

	@ApiModelProperty("分类")
	private Integer category;

	@ApiModelProperty("交互类型")
	private Integer interactiveType;

	@ApiModelProperty("通常状态")
	private Integer commonStatus = 0;

	@ApiModelProperty("状态")
	private Integer status;

	@ApiModelProperty("状态信息")
	private String statusText;

	@ApiModelProperty("关联测评任务ID")
	private String taskId;

	@ApiModelProperty("执行数")
	private Integer count;

	@ApiModelProperty("来源IP")
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
