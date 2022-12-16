package com.cyberpeace.situation.service;

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
}
