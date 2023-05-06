package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**cr角色节点
 * 节点简要信息
 @author cyberpeace
 @date 2023/05/06
 */
@Data
public class CrRoleNode implements Serializable {

	@ApiModelProperty(value = "节点id")
	private String id;

	@ApiModelProperty(value = "节点类型")
	private Integer category;
}
