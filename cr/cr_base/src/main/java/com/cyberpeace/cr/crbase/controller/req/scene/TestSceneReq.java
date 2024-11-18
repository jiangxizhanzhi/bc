package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测试场景请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TestSceneReq implements Serializable {

	/**
	 * 业务模板id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "业务模板id", required = true)
	private String businessTemplateId;
}
