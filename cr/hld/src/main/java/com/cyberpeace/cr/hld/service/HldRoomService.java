package com.cyberpeace.cr.hld.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.service.context.HldRoomContext;

public interface HldRoomService extends IService<HldRoom> {

	HldRoom create(HldRoomContext hldRoomContext);

	HldRoom update(HldRoomContext hldRoomContext, HldRoom hldRoom);

	void checkUserPermission(String roomId, String userId);

}
