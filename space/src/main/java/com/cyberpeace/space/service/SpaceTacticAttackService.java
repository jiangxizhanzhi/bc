/**
 * 这段代码定义了一个名为 `SpaceTacticAttackService` 的接口，它继承自 `IService<SpaceTacticAttack>`。这个接口主要用于处理与空间战法攻击相关的业务逻辑。以下是对代码的主要分析： 
 * 1. **继承关系**： 
 * - `SpaceTacticAttackService` 继承自 `IService<SpaceTacticAttack>`，表明它是一个服务接口，用于操作 `SpaceTacticAttack` 实体。 
 * 2. **方法概述**： 
 * - `pageAttack(SpaceTacticPageReq req)`：根据请求分页获取攻击列表。 
 * - `addAttack(AttackAddReq req)`：新增一条攻击记录。 
 * - `updateAttack(AttackModifyReq req)`：更新指定的攻击记录。 
 * - `deleteBatch(List<String> ids)`：批量删除攻击记录。 
 * - `getDetail(String resourceId)`：获取特定资源的攻击详情。 
 * - `pageCategory(PageReqBase reqBase)`：分页获取攻击类别列表。 
 * - `addAttackCategory(TacticAttackCategoryAddReq req)`：新增攻击类别。 
 * - `updateAttackCategory(TacticAttackCategoryModifyReq req)`：更新攻击类别。 
 * - `deleteCategory(List<String> ids)`：批量删除攻击类别。 
 * - `getTacticAttackChart()`：获取战法攻击的知识点图表。 
 * - `listByCategoryids(List<String> ids)`：根据类别ID列表列出相关记录。 
 * 3. **注释说明**： 
 * - 每个方法都有详细的注释，描述了其功能、参数和返回值类型，便于理解和使用。 
 * 4. **依赖的类和接口**： 
 * - 接口依赖于多个类如 `AttackAddReq`, `AttackModifyReq`, `AttackDetail` 等，这些类可能定义了具体的数据结构或请求参数。 
 * - 还引用了一些自定义的响应类如 `ListRsp`, `PageReqBase` 等，用于封装返回结果。 
 * 总体来说，这个接口提供了一套完整的API来管理空间战法攻击及其相关类别，并且通过良好的注释使得接口易于理解和扩展。 
**/
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.space.dao.entity.SpaceTacticAttack;
import com.cyberpeace.space.service.context.index.KnowledgeChart;
import com.cyberpeace.space.service.context.tactic.*;

import java.util.List;

/**
 * 空间战法攻击Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
public interface SpaceTacticAttackService extends IService<SpaceTacticAttack> {
    /**
     * 分页攻击
     *
     * @param req 请求体
     * @return list rsp< 攻击列表 bo>
     * @throws
     * @see SpaceTacticPageReq
     * @see ListRsp<AttackListBo>
     */
    ListRsp<AttackListBo> pageAttack(SpaceTacticPageReq req);

    /**
     * 新增攻击
     *
     * @param req 请求体
     * @throws
     * @see AttackAddReq
     */
    void addAttack(AttackAddReq req);

    /**
     * 更新攻击
     *
     * @param req 请求体
     * @throws
     * @see AttackModifyReq
     */
    void updateAttack(AttackModifyReq req);

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
     * @return 攻击详情
     * @throws
     * @see String
     * @see AttackDetail
     */
    AttackDetail getDetail(String resourceId);

    /**
     * 分页类别
     *
     * @param reqBase 请求体基础
     * @return 列表RSP<战法攻击类别博>
     * @throws
     * @see PageReqBase
     * @see ListRsp<TacticAttackCategoryBo>
     */
    ListRsp<TacticAttackCategoryBo> pageCategory(PageReqBase reqBase);

    /**
     * 新增攻击类别
     *
     * @param req 请求体
     * @throws
     * @see TacticAttackCategoryAddReq
     */
    void addAttackCategory(TacticAttackCategoryAddReq req);

    /**
     * 更新攻击类别
     *
     * @param req 请求体
     * @throws
     * @see TacticAttackCategoryModifyReq
     */
    void updateAttackCategory(TacticAttackCategoryModifyReq req);

    /**
     * 删除类别
     *
     * @param ids id列表
     * @throws
     * @see List<String>
     */
    void deleteCategory(List<String> ids);

    /**
     * 获取战法攻击图表
     *
     * @return 知识点图表
     * @throws
     * @see KnowledgeChart
     */
    KnowledgeChart getTacticAttackChart();

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
