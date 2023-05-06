package com.cyberpeace.common.model.room;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 指示板组
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class DashboardGroup implements Serializable {

	@ApiModelProperty("分组名称")
	private String groupname;

	@ApiModelProperty("角色")
	private String role;

	@ApiModelProperty("分组编号")
	private String id;

	@ApiModelProperty("头像")
	private String avatar;

	@ApiModelProperty("组员编号")
	private List<String> members;

	/**
	 * 指示板组
	 *
	 * @param role 角色
	 */
	public DashboardGroup(String role) {
		this.role = role;
		this.avatar = "";
		this.groupname = role;
		this.id = role;
	}
}
