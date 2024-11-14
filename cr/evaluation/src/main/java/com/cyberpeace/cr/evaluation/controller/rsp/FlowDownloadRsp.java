package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流量下载响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class FlowDownloadRsp implements Serializable {

	/**
	 * 文件路径
	 */
	@ApiModelProperty(value = "文件路径")
	private String filePath;

	public FlowDownloadRsp(String filePath) {
		this.filePath = filePath;
	}

}
