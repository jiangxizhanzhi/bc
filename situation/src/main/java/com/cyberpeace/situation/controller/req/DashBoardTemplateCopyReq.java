package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 复制大屏模板请求
 *
 * @author chenhy
 * @date 2023/05/10
 */
@Data
public class DashBoardTemplateCopyReq implements Serializable {

	/**
	 * 模板ID
	 */
	@ApiModelProperty("模板ID")
	@NotBlank
	private String templateId;

	/**
	 * 模板名称
	 */
	@ApiModelProperty("模板名称")
	@NotBlank
	private String name;

}
