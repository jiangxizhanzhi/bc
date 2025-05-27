package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.space.dao.entity.SpaceTacticDefense;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import com.cyberpeace.space.service.context.index.KnowledgeChart;
import com.cyberpeace.space.service.context.tactic.*;

import java.util.List;

public interface SpaceTacticDefenseService extends IService<SpaceTacticDefense> {
    ListRsp<DefenseListBo> page(SpaceTacticPageReq req);

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

    List<SpaceTacticDefense> listByIds(List<String> defenseIds);

    SpaceTacticDefense getById(String resourceId);

    /**
     * 列表由类别id
     *
     * @param categoryId 类别id
     * @return {@code List<DefenseDetail> }
     */
    List<DefenseDetail> listByCategoryId(String categoryId);

    /**
     * 获取框架
     *
     * @return {@code SpaceFrameLevel }
     */
    SpaceFrameLevel getFrame();

    KnowledgeChart getTacticDefenseChart();

    List<String> listByCategoryids(List<String> ids);
}
