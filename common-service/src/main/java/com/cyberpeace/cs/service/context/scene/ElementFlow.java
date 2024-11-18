package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * 元素流量
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ElementFlow implements Serializable {

	/**
	 * id
	 *
	 * @see String
	 */
	@ApiModelProperty("ID")
	private String id;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty("描述")
	private String desc;

	/**
	 * 类型
	 *
	 * @see String
	 */
	@ApiModelProperty("类型")
	private String type;

	/**
	 * 类别
	 *
	 * @see Integer
	 */
	@ApiModelProperty("分类")
	private Integer category;

	/**
	 * 交互式类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("交互类型")
	private Integer interactiveType;

	/**
	 * 常见状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty("通常状态")
	private Integer commonStatus = 0;

	/**
	 * 状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty("状态")
	private Integer status;

	/**
	 * 状态文本
	 *
	 * @see String
	 */
	@ApiModelProperty("状态信息")
	private String statusText;

	/**
	 * 任务id
	 *
	 * @see String
	 */
	@ApiModelProperty("关联测评任务ID")
	private String taskId;

	/**
	 * 计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("执行数")
	private Integer count;

	/**
	 * 源ip地址
	 *
	 * @see String
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
