package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测试场景请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class TestSceneReq implements Serializable {

	@ApiModelProperty(value = "业务模板id", required = true)
	private String businessTemplateId;
}
