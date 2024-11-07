package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * 元素流
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class ElementFlow implements Serializable {

	/**
	 * id
	 */
	@ApiModelProperty("ID")
	private String id;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty("描述")
	private String desc;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型")
	private String type;

	/**
	 * 类别
	 */
	@ApiModelProperty("分类")
	private Integer category;

	/**
	 * 交互式类型
	 */
	@ApiModelProperty("交互类型")
	private Integer interactiveType;

	/**
	 * 常见状态
	 */
	@ApiModelProperty("通常状态")
	private Integer commonStatus = 0;

	/**
	 * 状态
	 */
	@ApiModelProperty("状态")
	private Integer status;

	/**
	 * 状态发短信
	 */
	@ApiModelProperty("状态信息")
	private String statusText;

	/**
	 * 任务id
	 */
	@ApiModelProperty("关联测评任务ID")
	private String taskId;

	/**
	 * 计数
	 */
	@ApiModelProperty("执行数")
	private Integer count;

	/**
	 * 源ip地址
	 */
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
