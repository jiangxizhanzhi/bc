package com.cyberpeace.cr.singletrain.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainListReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.service.bo.SingleRoomBo;
import com.cyberpeace.cr.singletrain.service.bo.SingleRoomListBo;

public interface SingleRoomService extends IService<SingleRoom> {

    SingleRoomBo createRoom(String businessTemplateId);

    SingleRoomBo getRoomInfo(String businessTemplateId);

    Boolean deleteRoom(String businessTemplateId);

    ListRsp<SingleRoomListBo> getSingleTrainPageList(SingleTrainListReq queryParams);

}
