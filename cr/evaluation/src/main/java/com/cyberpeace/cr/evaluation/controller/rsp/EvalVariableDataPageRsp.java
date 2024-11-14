package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估变量数据分页响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalVariableDataPageRsp implements Serializable {
	/**
	 * 场景节点IP
	 */
	@ApiModelProperty("场景节点IP")
    private String nodeIp;

	/**
	 * 探针数据采集时间
	 */
	@ApiModelProperty("探针数据采集时间")
    private String collectionTime;

	/**
	 * 探针采集数据
	 */
	@ApiModelProperty("探针采集数据")
    private String content;
}
