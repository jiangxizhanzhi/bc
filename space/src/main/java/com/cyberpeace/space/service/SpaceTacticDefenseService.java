package com.cyberpeace.space.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.space.service.context.tactic.*;

import java.util.List;

public interface SpaceTacticDefenseService {
    ListRsp<DefenseListBo> page(PageReqBase reqBase);

    void add(DefenseAddReq req);

    void updateDefense(DefenseModifyReq req);

    void deleteBatch(List<String> ids);

    DefenseDetail getDetail(String resourceId);

    ListRsp<TacticDefenseCategoryBo> pageCategory(PageReqBase reqBase);

    void addDefenseCategory(TacticDefenseCategoryAddReq req);

    void updateDefenseCategory(TacticDefenseCategoryModifyReq req);

    void deleteCategory(List<String> ids);

    DefenseCategoryBo getCategoryDetail(String resourceId);

    List<TacticDefenseCategoryBo> listCategory();
}
