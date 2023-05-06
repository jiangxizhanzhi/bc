package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景运维请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class SceneOpsReq implements Serializable {

	@ApiModelProperty(value = "业务实例id", required = true)
	private String roomId;
}
