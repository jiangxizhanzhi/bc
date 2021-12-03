package com.cyberpeace.cr.singletrain.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainListReq;
import com.cyberpeace.cr.singletrain.controller.req.SubmitMissionReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.service.bo.*;

import java.util.List;

public interface SingleRoomService extends IService<SingleRoom> {

    SingleRoomBo createRoom(String businessTemplateId);

    SingleRoomBo getRoomInfo(String businessTemplateId);

    Boolean deleteRoom(String businessTemplateId);

    ListRsp<SingleRoomListBo> getSingleTrainPageList(SingleTrainListReq queryParams);

    SingleMissionBo getMission(String businessTemplateId, String missionId);

    Boolean submitMission(String businessTemplateId, SubmitMissionReq requestData);

    List<TrainRecordBo> getTrainRecord(String businessTemplateId);

    String getTrainManual(String businessTemplateId);

    TrainTimeLineBo getTimeLineData(String businessTemplateId);

}
