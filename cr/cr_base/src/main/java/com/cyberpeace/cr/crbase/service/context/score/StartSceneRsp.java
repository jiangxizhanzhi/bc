package com.cyberpeace.cr.crbase.service.context.score;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StartSceneRsp implements Serializable {

	@ApiModelProperty(value = "创建的中台场景id")
	private String mpSceneId;

	public StartSceneRsp(String mpSceneId) {
		this.mpSceneId = mpSceneId;
	}
}
