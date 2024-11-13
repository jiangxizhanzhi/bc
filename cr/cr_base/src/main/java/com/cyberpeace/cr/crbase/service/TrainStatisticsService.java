package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.TrainStatisticsDetailReq;
import com.cyberpeace.cr.crbase.service.context.statistics.StatisticsDetail;

/**
 * 训练统计学Service
 *
 * @author SN
 * @date 2024/11/13
 */
public interface TrainStatisticsService {
    /**
     * 获取全部统计学详情
     *
     * @param detailReq 详情请求体
     * @param page      分页
     * @param code      代号
     * @return {@code ListRsp<StatisticsDetail> }
     */
    ListRsp<StatisticsDetail> getAllStatisticsDetail(TrainStatisticsDetailReq detailReq, Page page,Integer code);
}
