package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.situation.constant.dashboard.DashboardLevel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大屏实例查询请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationPublishInstanceFilterReq extends PageReqBase implements Serializable {

	/**
	 * 大屏名称
	 */
	@ApiModelProperty(value = "大屏名称")
	private String name;

	/**
	 * 大屏类型 1-综合态势 2-任务态势
	 */
	@ApiModelProperty(value = "大屏类型 1-综合态势 2-任务态势")
	private Integer type;

	/**
	 * 大屏层级
	 */
	@ApiModelProperty(value = "大屏层级")
	private List<Integer> levels;

	/**
	 * 任务类型
	 */
	@ApiModelProperty(value = "任务类型")
	private String category;

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private String status;

	/**
	 * 过滤条件-开始时间
	 */
	@ApiModelProperty(name = "start_time", value = "过滤条件-开始时间")
	private Date startTime;

	/**
	 * 过滤条件-结束时间
	 */
	@ApiModelProperty(name = "end_time", value = "过滤条件-结束时间")
	private Date endTime;

	/**
	 * 模式 1-实时 2-回放
	 */
	@ApiModelProperty(value = "模式 1-实时 2-回放")
	private Integer replayMode;

	/**
	 * 是否重演大屏
	 */
	@ApiModelProperty(value = "是否重演大屏")
	private Boolean replay = false;

	/**
	 * 设置开始时间
	 *
	 * @param startTime 开始时间
	 */
	public void setStart_time(String startTime) {
		this.startTime = TimeUtil.toDate1(startTime);
	}

	/**
	 * 设置结束时间
	 *
	 * @param endTime 结束时间
	 */
	public void setEnd_time(String endTime) {
		this.endTime = TimeUtil.toDate1(endTime);
	}

	/**
	 * 设置大屏类型及层级过滤条件
	 *
	 * @param type 类型
	 */
	public void setType(Integer type) {
		this.type = type;
		this.levels = DashboardLevel.selectLevelByType(type);
	}
}
