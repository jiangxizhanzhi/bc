package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景老年 退休金 计划请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class SceneOpsReq implements Serializable {

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "业务实例id", required = true)
	private String roomId;
}
