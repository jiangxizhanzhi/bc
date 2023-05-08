package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.cyberpeace.integratation.model.situation.TaskEventContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 事件任务请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class EventTaskReq implements Serializable {

	/**
	 * 大屏ID
	 */
	@ApiModelProperty("大屏ID")
	@NotEmpty
	private String dashboardId;

	/**
	 * 特征ID
	 */
	@ApiModelProperty("特征ID")
	@NotEmpty
	private String featureId;

	/**
	 * 任务事件
	 */
	@ApiModelProperty("任务事件")
	@NotEmpty
	private TaskEventContext taskEvent;

}
