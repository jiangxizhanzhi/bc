package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景操作 计划请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneOpsReq implements Serializable {

	/**
	 * 房间id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "业务实例id", required = true)
	private String roomId;
}
