package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景开始从模板请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class SceneStartFromTemplateReq implements Serializable {

	/**
	 * 模板id
	 */
	@ApiModelProperty(value = "场景模板id", required = true)
	private String templateId;
}
