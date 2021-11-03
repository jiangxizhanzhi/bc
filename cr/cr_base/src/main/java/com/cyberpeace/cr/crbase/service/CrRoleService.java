package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.model.role.CrRoleRsp;

/**
 *
 */
public interface CrRoleService extends IService<CrRole> {

	CrRoleRsp getRoles(String roomId);

	List<String> getRoomIdsByUserid(String userid);
}
