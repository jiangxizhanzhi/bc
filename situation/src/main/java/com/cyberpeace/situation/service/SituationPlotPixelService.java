package com.cyberpeace.situation.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.SituationPlotPixelPageReq;
import com.cyberpeace.situation.controller.req.SituationPlotPixelReq;
import com.cyberpeace.situation.dao.entity.SituationPlotPixel;

import java.util.List;

/**
 * @Author xuxz
 * @Date 17/12/2022 下午5:10
 */
public interface SituationPlotPixelService {
    /**
     * 新增图标
     *
     * @param situationPlotPixelReq
     * @return
     */
    String createSituationPlotPixel(SituationPlotPixelReq situationPlotPixelReq);

    /**
     * 更新图标
     *
     * @param situationPlotPixelReq
     * @return
     */
    String updateSituationPlotPixel(SituationPlotPixelReq situationPlotPixelReq);

    /**
     * 获取主题样式数据详情
     *
     * @param resourceId
     * @return
     */
    SituationPlotPixel getSituationPlotPixelInfo(String resourceId);

    /**
     * 批量删除主题样式数据
     *
     * @param idList
     * @return
     */
    Integer deleteSituationPlotPixel(List<String> idList);

    /**
     * 获取列表分页
     *
     * @param situationPlotPixelPageReq
     * @return
     */
    ListRsp<SituationPlotPixel> getSituationPlotPixelPage(SituationPlotPixelPageReq situationPlotPixelPageReq);
}
