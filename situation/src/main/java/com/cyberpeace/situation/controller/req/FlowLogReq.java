package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流量日志请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class FlowLogReq implements Serializable {

	/**
	 * 场景id
	 */
	@ApiModelProperty(value = "场景id", required = true)
	@NotEmpty
	private String sceneId;

	/**
	 * 起始时间
	 */
	@ApiModelProperty("起始时间")
	private String startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty("结束时间")
	private String endTime;

	/**
	 * 每页数据量
	 */
	@ApiModelProperty("每页数据量")
	private Integer perPage = 10;

	/**
	 * 构造函数
	 *
	 * @param sceneId 场景id
	 */
	public FlowLogReq(String sceneId) {
		this.sceneId = sceneId;
	}

	public FlowLogReq() {}
}
