package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 开始场景响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class StartSceneRsp implements Serializable {

	/**
	 * 中台场景id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "创建的中台场景id")
	private String mpSceneId;

	public StartSceneRsp(String mpSceneId) {
		this.mpSceneId = mpSceneId;
	}
}
