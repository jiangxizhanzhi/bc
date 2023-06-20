package com.cyberpeace.situation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.situation.controller.req.BusinessComponentCategoryReq;
import com.cyberpeace.situation.dao.entity.SituationBusinessComponentCategory;

import java.util.List;

/**
 * 业务组件类别服务
 *
 * @author dell
 * @date 2023/06/20
 */
public interface SituationBusinessComponentCategoryService extends IService<SituationBusinessComponentCategory> {

    /**
     * 业务组件类别列表
     *
     * @param name 名字
     * @return {@link List}<{@link SituationBusinessComponentCategory}>
     */
    List<SituationBusinessComponentCategory> getBusinessComponentCategoryList(String name);

    String updateBusinessComponentCategoryCategory(BusinessComponentCategoryReq businessComponentCategoryReq);

    String createBusinessComponentCategoryCategory(BusinessComponentCategoryReq businessComponentCategoryReq);

    SituationBusinessComponentCategory getBusinessComponentCategory(String resourceId);

    Integer deleteBusinessComponentCategoryCategory(List<String> idList);
}
