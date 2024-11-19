package com.cyberpeace.situation.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流量日志请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class FlowLogReq implements Serializable {

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "场景id", required = true)
	@NotEmpty
	private String sceneId;

	/**
	 * 起始时间
	 *
	 * @see String
	 */
	@ApiModelProperty("起始时间")
	private String startTime;

	/**
	 * 结束时间
	 *
	 * @see String
	 */
	@ApiModelProperty("结束时间")
	private String endTime;

	/**
	 * 每页数据量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("每页数据量")
	private Integer perPage = 10;

	public FlowLogReq(String sceneId) {
		this.sceneId = sceneId;
	}

	public FlowLogReq() {}
}
