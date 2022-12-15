package com.cyberpeace.situation.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.SituationIconPageReq;
import com.cyberpeace.situation.controller.req.SituationIconReq;
import com.cyberpeace.situation.controller.res.SituationIconRes;
import com.cyberpeace.situation.dao.entity.SituationIcon;

import java.util.List;

/**
 * @Author xuxz
 * @Date 15/12/2022 上午11:00
 */
public interface SituationIconService {

    /**
     * 新增图标
     *
     * @param situationIconReq
     * @return
     */
    String createSituationIcon(SituationIconReq situationIconReq);

    /**
     * 更新图标
     *
     * @param situationIconReq
     * @return
     */
    String updateSituationIcon(SituationIconReq situationIconReq);

    /**
     * 获取主题样式数据详情
     *
     * @param resourceId
     * @return
     */
    SituationIconRes getSituationIconInfo(String resourceId);

    /**
     * 批量删除主题样式数据
     *
     * @param idList
     * @return
     */
    Integer deleteSituationIcon(List<String> idList);

    /**
     * 获取列表分页
     *
     * @param situationIconPageReq
     * @return
     */
    ListRsp<SituationIconRes> getSituationIconPage(SituationIconPageReq situationIconPageReq);


}
