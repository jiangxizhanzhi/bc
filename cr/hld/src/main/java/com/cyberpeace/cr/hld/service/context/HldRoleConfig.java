package com.cyberpeace.cr.hld.service.context;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;

import lombok.Data;

/**
 * 用于后台模板查询的返回
 */
@Data
public class HldRoleConfig extends CrRoleConfig implements Serializable {

	private List<String> users;

}
