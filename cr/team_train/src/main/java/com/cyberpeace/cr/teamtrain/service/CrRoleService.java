package com.cyberpeace.cr.teamtrain.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.teamtrain.dao.entity.CrRole;
import com.cyberpeace.cr.teamtrain.model.role.CrRoleRsp;

/**
 *
 */
public interface CrRoleService extends IService<CrRole> {

	CrRoleRsp getRoles(String roomId);

	List<String> getRoomIdsByUserid(String userid);
}
