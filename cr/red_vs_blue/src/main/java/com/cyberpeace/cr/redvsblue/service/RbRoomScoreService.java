package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.context.mission.RoleScore;

public interface RbRoomScoreService extends IService<RbRoom> {

	List<RoleScore> getRoleScoreInfo(String resourceId);
}
