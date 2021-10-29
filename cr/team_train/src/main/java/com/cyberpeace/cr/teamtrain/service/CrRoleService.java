package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.teamtrain.controller.CrRoleOutput;
import com.cyberpeace.cr.teamtrain.dao.entity.CrRole;

/**
 *
 */
public interface CrRoleService extends IService<CrRole> {

    CrRoleOutput getOr404(String roomId);
}
