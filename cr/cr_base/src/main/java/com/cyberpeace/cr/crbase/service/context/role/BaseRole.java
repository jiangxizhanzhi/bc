package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础角色信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class BaseRole {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 岗位群
	 */
	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	/**
	 * 父哈希
	 */
	@ApiModelProperty(value = "父角色id， 冗余")
	private Integer parentHash;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 岗位数据列表
	 */
	@ApiModelProperty(value = "角色用户列表")
	private List<RoleUsers> roleDataList;

	/**
	 * 图表信息
	 */
	@ApiModelProperty(value = "图表角色信息")
	private ChartInfo chartInfo;

}