package com.cyberpeace.situation.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.SituationCommonComponentsPageReq;
import com.cyberpeace.situation.controller.res.SituationCommonComponentsPageRes;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午5:10
 */
public interface SituationCommonComponentsService {

    /**
     * 获取gis列表
     *
     * @param situationCommonComponentsPageReq
     * @return
     */
    ListRsp<SituationCommonComponentsPageRes> getGisPage(SituationCommonComponentsPageReq situationCommonComponentsPageReq);
}
