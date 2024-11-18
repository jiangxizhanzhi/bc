package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流量下载响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class FlowDownloadRsp implements Serializable {

	/**
	 * 文件路径
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "文件路径")
	private String filePath;

	public FlowDownloadRsp(String filePath) {
		this.filePath = filePath;
	}

}
