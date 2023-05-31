package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.Date;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.mark.EvidenceBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * 团队时间线事件
 */
@Data
public class TimeLineEvent {

	@ApiModelProperty(value = "编号")
	private String resourceId;

	@ApiModelProperty(value = "时间")
	private Date time;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "头像地址")
	private String avatarUrl;

	@ApiModelProperty(value = "类型， 1：任务，2：流量， 3：标记")
	private Integer type;

	@ApiModelProperty(value = "任务事件类型， 0：待评估，1：成功，2:失败")
	private Integer status;

	@ApiModelProperty(value = "任务是否动态新增")
	private Boolean isDynamic;

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
