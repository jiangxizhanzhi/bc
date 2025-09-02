package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.space.controller.res.SystemTreeRes;
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
/**
 * 这段代码定义了一个名为 `SpaceTacticDefenseService` 的接口，它继承自 `IService<SpaceTacticDefense>`。该接口主要用于处理与“空间战法防御”相关的业务逻辑。以下是对代码的简要分析： 
 * 1. **继承关系**： 
 * - 接口继承了 `IService<SpaceTacticDefense>`，表明它扩展了 MyBatis-Plus 提供的服务功能。 
 * 2. **方法概述**： 
 * - **分页操作**：`page(SpaceTacticPageReq req)` 返回分页后的防御列表。 
 * - **新增操作**：`add(DefenseAddReq req)` 用于新增防御记录。 
 * - **更新操作**：`updateDefense(DefenseModifyReq req)` 更新指定的防御记录。 
 * - **删除操作**：`deleteBatch(List<String> ids)` 批量删除防御记录。 
 * - **详情获取**：`getDetail(String resourceId)` 根据资源 ID 获取详细的防御信息。 
 * - **分类分页**：`pageCategory(PageReqBase reqBase)` 按类别分页返回防御列表。 
 * - **新增分类**：`addDefenseCategory(TacticDefenseCategoryAddReq req)` 添加新的防御类别。 
 * - **更新分类**：`updateDefenseCategory(TacticDefenseCategoryModifyReq req)` 更新防御类别。 
 * - **删除分类**：`deleteCategory(List<String> ids)` 批量删除防御类别。 
 * - **分类详情**：`getCategoryDetail(String resourceId)` 获取特定分类的详细信息。 
 * - **分类列表**：`listCategory()` 返回所有防御分类列表。 
 * - **按 ID 列表查询**：`listByIds(List<String> defenseIds)` 根据一组 ID 查询多个防御对象。 
 * - **单个对象查询**：`getById(String resourceId)` 根据单一 ID 查询防御对象。 
 * - **按分类 ID 查询**：`listByCategoryId(String categoryId)` 根据分类 ID 查询所属的所有防御细节。 
 * - **获取框架**：`getFrame()` 返回框架信息。 
 * - **获取图表**：`getTacticDefenseChart()` 返回知识图表。 
 * - **按类别 ID 列表查询**：`listByCategoryids(List<String> ids)` 根据多个类别 ID 查询对应的防御对象。 
 * 3. **依赖类**： 
 * - 接口中使用了多个自定义的请求参数类（如 `SpaceTacticPageReq`, `DefenseAddReq` 等）以及响应结果类（如 `ListRsp<DefenseListBo>`）来确保数据传输的安全性和类型安全性。 
 * 4. **设计目的**： 
 * - 该接口的设计目的是为了提供一套完整的、面向服务的 API，支持对“空间战法防御”的全面管理，包括但不限于创建、修改、删除、查询等基本 CRUD 操作，并且还涵盖了更复杂的业务场景，如分页、分类管理和图表展示等。 
 * 总结来说，这个接口是一个典型的基于领域驱动设计（DDD）的服务层接口，旨在封装与“空间战法防御”相关的复杂业务逻辑，便于上层应用调用。 
**/
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
    void add(DefenseAddReq req, boolean tmp);
    void tmpAdd(DefenseTmpAddReq req);

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

    List<SystemTreeRes> tree(String systemId);
}

