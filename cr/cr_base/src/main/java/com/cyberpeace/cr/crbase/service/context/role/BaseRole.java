package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础角色信息
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class BaseRole {

	@ApiModelProperty(value = "编号")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	@ApiModelProperty(value = "父角色id， 冗余")
	private Integer parentHash;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "角色用户列表")
	private List<RoleUsers> roleDataList;

	@ApiModelProperty(value = "图表角色信息")
	private ChartInfo chartInfo;

}