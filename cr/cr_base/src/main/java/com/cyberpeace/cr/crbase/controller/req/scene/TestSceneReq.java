package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测试场景请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class TestSceneReq implements Serializable {

	/**
	 * 业务模板id
	 */
	@ApiModelProperty(value = "业务模板id", required = true)
	private String businessTemplateId;
}
