package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.Date;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.mark.EvidenceBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * 团队时间线事件
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class TimeLineEvent {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "时间")
	private Date time;

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "标题")
	private String title;

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "用户名")
	private String userName;

	/**
	 * 头像 URL
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "头像地址")
	private String avatarUrl;

	/**
	 * 角色组
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "角色组，2：团队学员，4：红方，5：蓝方")
	private Integer roleGroup;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "类型, || 1:未开始，2：进行中，3：已完成")
	private Integer type;

	/**
	 * 事件类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "事件类型; 1：任务，2：流量，3：标记")
	private Integer eventType;

	/**
	 * 状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "任务事件状态， 0：待评估，1：成功，2:失败")
	private Integer status;

	/**
	 * 是动态
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "任务是否动态新增")
	private Boolean isDynamic;

	/**
	 * 附件
	 *
	 * @see List<EvidenceBo>
	 */
	@ApiModelProperty(value = "标记附件")
	private List<EvidenceBo> attachments;

	public enum EventType {

		MISSION(1, "下发任务"), TRAFFIC(2, "下发流量"), MARK(3, "标记");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private EventType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
