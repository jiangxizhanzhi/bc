package com.cyberpeace.situation.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.SituationDecorationPageReq;
import com.cyberpeace.situation.controller.req.SituationDecorationReq;
import com.cyberpeace.situation.controller.res.SituationDecorationPageRes;
import com.cyberpeace.situation.dao.entity.SituationDecoration;

import java.util.List;

/**
 * @Author xuxz
 * @Date 4/1/2023 下午1:44
 */
public interface SituationDecorationService {

    /**
     * 新增图形资源
     *
     * @param situationDecorationReq
     * @return
     */
    String createSituationDecoration(SituationDecorationReq situationDecorationReq);

    /**
     * 更新图形资源
     *
     * @param situationDecorationReq
     * @return
     */
    String updateSituationDecoration(SituationDecorationReq situationDecorationReq);

    /**
     * 获取图形资源详情
     *
     * @param resourceId
     * @return
     */
    SituationDecoration getSituationDecorationInfo(String resourceId);

    /**
     * 批量删除图形资源
     *
     * @param idList
     * @return
     */
    Integer deleteSituationDecoration(List<String> idList);

    /**
     * 获取列表分页
     *
     * @param situationDecorationPageReq
     * @return
     */
    ListRsp<SituationDecorationPageRes> getSituationDecorationPage(SituationDecorationPageReq situationDecorationPageReq);
}
