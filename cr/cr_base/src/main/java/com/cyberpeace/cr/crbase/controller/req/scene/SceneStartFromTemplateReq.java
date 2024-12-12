package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景开始从模板请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class SceneStartFromTemplateReq implements Serializable {

	/**
	 * 模板id
	 */
	@ApiModelProperty(value = "场景模板id", required = true)
	private String templateId;
}
