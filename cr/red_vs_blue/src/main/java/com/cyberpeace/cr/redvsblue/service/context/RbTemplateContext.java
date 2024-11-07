package com.cyberpeace.cr.redvsblue.service.context;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝模板
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RbTemplateContext extends BasicTemplate {

	/**
	 * 资源id
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private String resourceId;

	/**
	 * 角色列表
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private String roleList;

}
