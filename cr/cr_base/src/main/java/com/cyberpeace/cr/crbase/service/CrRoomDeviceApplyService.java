package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.rsp.RoomDeviceApplyListRsp;
import com.cyberpeace.cr.crbase.controller.rsp.RoomDeviceOccupyRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRoomDeviceApply;

public interface CrRoomDeviceApplyService extends IService<CrRoomDeviceApply> {
    Boolean apply(String roomId, String roomName, String deviceId);

    RoomDeviceOccupyRsp occupy(String deviceId);

    ListRsp<RoomDeviceApplyListRsp> list(Page page, String deviceId);

    Boolean allow(String applyId);

    Boolean refuse(String applyId);

    Boolean recycle(String roomId, String deviceId);

}
