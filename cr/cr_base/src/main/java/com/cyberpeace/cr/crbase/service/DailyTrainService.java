package com.cyberpeace.cr.crbase.service;


import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.DailyTrainListReq;
import com.cyberpeace.cr.crbase.controller.rsp.DailyTrainRsp;
import org.springframework.validation.annotation.Validated;


public interface DailyTrainService {

     ListRsp<DailyTrainRsp> list(@Validated DailyTrainListReq listReq) ;
}
