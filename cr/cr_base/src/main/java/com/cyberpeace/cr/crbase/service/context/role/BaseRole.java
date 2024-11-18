package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础角色信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class BaseRole {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 角色组
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	/**
	 * 父哈希
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "父角色id， 冗余")
	private Integer parentHash;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 角色数据列表
	 *
	 * @see List<RoleUsers>
	 */
	@ApiModelProperty(value = "角色用户列表")
	private List<RoleUsers> roleDataList;

	/**
	 * 图表信息
	 *
	 * @see ChartInfo
	 */
	@ApiModelProperty(value = "图表角色信息")
	private ChartInfo chartInfo;

}