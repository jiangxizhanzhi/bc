package com.cyberpeace.bs.dao.entity;

import java.time.LocalTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("bs_user_traffic")
public class UserTraffic extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("id")
	private String resourceId;

	@ApiModelProperty("用户名称")
	private String userName;

	@ApiModelProperty("用户类型")
	private Integer userType;

	@ApiModelProperty("开始时间")
	private LocalTime startTime;

	@ApiModelProperty("结束时间")
	private LocalTime endTime;

	@ApiModelProperty("访问带宽")
	private Integer bandwidth;

	@ApiModelProperty("访问习惯")
	private Integer accessOs;

	@ApiModelProperty("访问配置")
	private Integer accessConfig;

	@ApiModelProperty("访问行为")
	private String behaviorList;

	@ApiModelProperty("行为数量")
	private Integer behaviorCount;

	@ApiModelProperty("最后已执行编号（倒排）")
	private Integer executedIndex;

	@ApiModelProperty("执行状态")
	private Integer status;

	@ApiModelProperty("执行设备id")
	private String targetId;

	@ApiModelProperty("执行设备名称")
	private String targetName;

	/**
	 * 靶标ip
	 */
	private String targetIp;

	/**
	 * 用户行为模拟id
	 */
	private String userBehaviorId;

	public enum Status implements GenericEnum {

		NOT_STARTED(0, "未开始"), INITIATING(1, "初始化"), RUNNING(2, "执行中"), STOPPED(3, "已停止");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		Status(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
