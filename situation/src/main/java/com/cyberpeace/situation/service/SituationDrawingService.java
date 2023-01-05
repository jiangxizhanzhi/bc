package com.cyberpeace.situation.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.SituationDrawingPageReq;
import com.cyberpeace.situation.controller.req.SituationDrawingReq;
import com.cyberpeace.situation.dao.entity.SituationDrawing;
import com.cyberpeace.situation.dao.entity.SituationDrawingCategory;

import java.util.List;

/**
 * @Author xuxz
 * @Date 4/1/2023 下午1:44
 */
public interface SituationDrawingService {

    /**
     * 新增图形资源
     *
     * @param situationDrawingReq
     * @return
     */
    String createSituationDrawing(SituationDrawingReq situationDrawingReq);

    /**
     * 更新图形资源
     *
     * @param situationDrawingReq
     * @return
     */
    String updateSituationDrawing(SituationDrawingReq situationDrawingReq);

    /**
     * 获取图形资源详情
     *
     * @param resourceId
     * @return
     */
    SituationDrawing getSituationDrawingInfo(String resourceId);

    /**
     * 批量删除图形资源
     *
     * @param idList
     * @return
     */
    Integer deleteSituationDrawing(List<String> idList);

    /**
     * 获取列表分页
     *
     * @param situationDrawingPageReq
     * @return
     */
    ListRsp<SituationDrawing> getSituationDrawingPage(SituationDrawingPageReq situationDrawingPageReq);

    /**
     * 获取类型
     * @return
     */
    List<SituationDrawingCategory> getSituationCategory();
}
