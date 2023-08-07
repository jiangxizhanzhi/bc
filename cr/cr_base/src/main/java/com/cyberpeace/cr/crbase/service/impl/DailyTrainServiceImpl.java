package com.cyberpeace.cr.crbase.service.impl;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.DailyTrainListReq;
import com.cyberpeace.cr.crbase.controller.rsp.DailyTrainRsp;
import com.cyberpeace.cr.crbase.dao.mapper.DailyTrainMapper;
import com.cyberpeace.cr.crbase.service.DailyTrainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author SN
 */
@Service
@Slf4j
public class DailyTrainServiceImpl implements DailyTrainService {
    @Autowired
    private DailyTrainMapper dailyTrainMapper;

    @Override
    public ListRsp<DailyTrainRsp> list(DailyTrainListReq listReq) {
//        dailyTrainMapper.singleTrainSearch()
        return null;
    }
}