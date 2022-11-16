package com.cyberpeace.situation.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.GisPageReq;
import com.cyberpeace.situation.controller.res.GisPageRes;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午5:10
 */
public interface SituationGisService {

    /**
     * 获取gis列表
     * @param gisPageReq
     * @return
     */
    ListRsp<GisPageRes> getGisPage(GisPageReq gisPageReq);
}
