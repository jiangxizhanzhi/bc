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
