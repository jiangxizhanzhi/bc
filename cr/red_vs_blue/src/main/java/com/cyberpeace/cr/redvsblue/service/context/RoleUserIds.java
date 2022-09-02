package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import lombok.Data;

/**
 * 角色、用户id集合
 */
@Data
public class RoleUserIds {

	List<String> roleIds;

	List<String> userIds;

}
