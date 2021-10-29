package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.teamtrain.dao.entity.CrRole;
import com.cyberpeace.cr.teamtrain.model.role.CrRoleRes;

/**
 *
 */
public interface CrRoleService extends IService<CrRole> {

	CrRoleRes getRoles(String roomId);
}
