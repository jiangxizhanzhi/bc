package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.space.dao.entity.SpaceTacticDefense;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import com.cyberpeace.space.service.context.index.KnowledgeChart;
import com.cyberpeace.space.service.context.tactic.*;

import java.util.List;

/**
 * 空间战法防御Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
public interface SpaceTacticDefenseService extends IService<SpaceTacticDefense> {
    /**
     * 分页
     *
     * @param req 请求体
     * @return list rsp< Defense list bo>
     * @throws
     * @see SpaceTacticPageReq
     * @see ListRsp<DefenseListBo>
     */
    ListRsp<DefenseListBo> page(SpaceTacticPageReq req);

    /**
     * 新增
     *
     * @param req 请求体
     * @throws
     * @see DefenseAddReq
     */
    void add(DefenseAddReq req);

    /**
     * 更新防御
     *
     * @param req 请求体
     * @throws
     * @see DefenseModifyReq
     */
    void updateDefense(DefenseModifyReq req);

    /**
     * 删除批量
     *
     * @param ids id列表
     * @throws
     * @see List<String>
     */
    void deleteBatch(List<String> ids);

    /**
     * 获取详情
     *
     * @param resourceId 资源id
     * @return 防御详情
     * @throws
     * @see String
     * @see DefenseDetail
     */
    DefenseDetail getDetail(String resourceId);

    /**
     * 分页类别
     *
     * @param reqBase 请求体基础
     * @return 列表RSP<战法防御类别博>
     * @throws
     * @see PageReqBase
     * @see ListRsp<TacticDefenseCategoryBo>
     */
    ListRsp<TacticDefenseCategoryBo> pageCategory(PageReqBase reqBase);

    /**
     * 新增防御类别
     *
     * @param req 请求体
     * @throws
     * @see TacticDefenseCategoryAddReq
     */
    void addDefenseCategory(TacticDefenseCategoryAddReq req);

    /**
     * 更新防御类别
     *
     * @param req 请求体
     * @throws
     * @see TacticDefenseCategoryModifyReq
     */
    void updateDefenseCategory(TacticDefenseCategoryModifyReq req);

    /**
     * 删除类别
     *
     * @param ids id列表
     * @throws
     * @see List<String>
     */
    void deleteCategory(List<String> ids);

    /**
     * 获取类别详情
     *
     * @param resourceId 资源id
     * @return 国防类bo
     * @throws
     * @see String
     * @see DefenseCategoryBo
     */
    DefenseCategoryBo getCategoryDetail(String resourceId);

    /**
     * 列表类别
     *
     * @return 列表<战法防御类别博>
     * @throws
     * @see List<TacticDefenseCategoryBo>
     */
    List<TacticDefenseCategoryBo> listCategory();

    /**
     * 列表由id列表
     *
     * @param defenseIds 防御id列表
     * @return 列表<空间战法防御>
     * @throws
     * @see List<String>
     * @see List<SpaceTacticDefense>
     */
    List<SpaceTacticDefense> listByIds(List<String> defenseIds);

    /**
     * 获取由id
     *
     * @param resourceId 资源id
     * @return 空间战法防御
     * @throws
     * @see String
     * @see SpaceTacticDefense
     */
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

    /**
     * 获取战法防御图表
     *
     * @return 知识点图表
     * @throws
     * @see KnowledgeChart
     */
    KnowledgeChart getTacticDefenseChart();

    /**
     * 按 categoryID 列出
     *
     * @param ids id列表
     * @return list<字符串>
     * @throws
     * @see List<String>
     * @see List<String>
     */
    List<String> listByCategoryids(List<String> ids);
}
