package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景开始从模板请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneStartFromTemplateReq implements Serializable {

	/**
	 * 模板id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "场景模板id", required = true)
	private String templateId;
}
