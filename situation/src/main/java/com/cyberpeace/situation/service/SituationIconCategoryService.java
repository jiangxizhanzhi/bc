package com.cyberpeace.situation.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.SituationIconCategoryReq;
import com.cyberpeace.situation.controller.req.SituationIconPageReq;
import com.cyberpeace.situation.controller.req.SituationIconReq;
import com.cyberpeace.situation.controller.res.SituationIconRes;
import com.cyberpeace.situation.dao.entity.SituationIconCategory;

import java.util.List;

/**
 * @Author xuxz
 * @Date 15/12/2022 上午11:55
 */
public interface SituationIconCategoryService {

    List<SituationIconCategory> getSituationCategoryList();

    /**
     * 获取图标类型
     * @param resourceId
     * @return
     */
    SituationIconCategory getSituationIconCategory(String resourceId);

    /**
     * 更新图标类型
     * @param situationIconCategoryReq
     * @return
     */
    String updateSituationIconCategory(SituationIconCategoryReq situationIconCategoryReq);

    /**
     * 批量删除图标类型
     * @param idList
     * @return
     */
    Integer deleteSituationIconCategory(List<String> idList);

    /**
     * 新增图标类型
     * @param situationIconCategoryReq
     * @return
     */
    String createSituationIcon(SituationIconCategoryReq situationIconCategoryReq);
}
