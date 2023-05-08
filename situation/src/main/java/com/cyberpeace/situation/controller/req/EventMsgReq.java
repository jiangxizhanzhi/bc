package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.cyberpeace.integratation.model.situation.req.EventReq;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 事件消息请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class EventMsgReq extends EventReq implements Serializable {

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
	 * 提取大屏实例信息
	 *
	 * @param instance 大屏实例
	 */
	public void writeInfoFromInstance(SituationPublishInstance instance) {

		// 提取任务ID
		this.setTaskId(instance.getTaskId());

		// 提取科目ID
		this.setSubjectId(instance.getSubjectId());
	}

}
