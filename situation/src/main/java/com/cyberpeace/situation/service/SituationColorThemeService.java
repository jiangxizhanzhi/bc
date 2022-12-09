package com.cyberpeace.situation.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.ColorThemePageReq;
import com.cyberpeace.situation.controller.req.ColorThemeReq;
import com.cyberpeace.situation.dao.entity.ColorTheme;

import java.util.List;

/**
 * @Author xuxz
 * @Date 8/12/2022 上午9:03
 */
public interface SituationColorThemeService {
    /**
     * 新增主题样式数据
     *
     * @param colorThemeReq
     * @return
     */
    ColorThemeReq createColorTheme(ColorThemeReq colorThemeReq);


    /**
     * 更新主题样式数据
     *
     * @param colorThemeReq
     * @return
     */
    String updateColorTheme(ColorThemeReq colorThemeReq);

    /**
     * 获取主题样式数据详情
     *
     * @param resourceId
     * @return
     */
    ColorTheme getColorThemeInfo(String resourceId);

    /**
     * 批量删除主题样式数据
     *
     * @param idList
     * @return
     */
    Integer deleteColorTheme(List<String> idList);

    /**
     * 获取列表分页
     * @param colorThemePageReq
     * @return
     */
    ListRsp<ColorTheme> getColorThemePage(ColorThemePageReq colorThemePageReq);
}
