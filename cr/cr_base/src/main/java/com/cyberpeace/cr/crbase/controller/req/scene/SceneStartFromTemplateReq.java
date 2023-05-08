package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景开始模板请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class SceneStartFromTemplateReq implements Serializable {

	@ApiModelProperty(value = "场景模板id", required = true)
	private String templateId;
}
