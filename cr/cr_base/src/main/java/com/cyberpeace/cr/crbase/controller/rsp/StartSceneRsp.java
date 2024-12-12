package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 开始场景响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class StartSceneRsp implements Serializable {

	/**
	 * 中台场景id
	 */
	@ApiModelProperty(value = "创建的中台场景id")
	private String mpSceneId;

	public StartSceneRsp(String mpSceneId) {
		this.mpSceneId = mpSceneId;
	}
}
