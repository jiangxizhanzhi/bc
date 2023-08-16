package com.cyberpeace.oj.course.service;


import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.TrainModeListReq;
import com.cyberpeace.cr.crbase.controller.req.TrainModeReq;
import com.cyberpeace.cr.crbase.controller.rsp.TrainModeRsp;

import java.util.List;

public interface TrainingModeService {

     ListRsp<TrainModeRsp> list(TrainModeListReq listReq) ;

     Boolean delete(List<TrainModeReq> trainModeReqList) ;
}
