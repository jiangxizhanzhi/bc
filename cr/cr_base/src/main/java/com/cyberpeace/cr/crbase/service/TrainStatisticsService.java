package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.TrainStatisticsDetailReq;
import com.cyberpeace.cr.crbase.service.context.statistics.StatisticsDetail;

public interface TrainStatisticsService {
    ListRsp<StatisticsDetail> getAllStatisticsDetail(TrainStatisticsDetailReq detailReq, Page page,Integer code);
}
