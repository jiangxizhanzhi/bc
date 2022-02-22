package com.cyberpeace.cr.hld.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.service.context.CrRoleConfigHld;
import com.cyberpeace.cr.hld.service.context.HldRoleConfigBase;
import com.cyberpeace.cr.hld.service.context.HldRoomContext;
import com.cyberpeace.cr.hld.service.context.HldTaskContext;

import java.util.List;

public interface HldRoomService extends IService<HldRoom> {

	HldRoom create(HldRoomContext hldRoomContext);

	HldRoom update(HldRoomContext hldRoomContext, HldRoom hldRoom);

	void checkUserPermission(String roomId, String userId);

	List<HldRoleConfigBase> getHldRoleConfig(HldTaskContext hldTaskContext);

	BaseInfo getHldBaseInfo(HldTaskContext hldTaskContext);

}
