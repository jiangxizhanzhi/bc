package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 开始场景返回值
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class StartSceneRsp implements Serializable {

	@ApiModelProperty(value = "创建的中台场景id")
	private String mpSceneId;

	public StartSceneRsp(String mpSceneId) {
		this.mpSceneId = mpSceneId;
	}
}
