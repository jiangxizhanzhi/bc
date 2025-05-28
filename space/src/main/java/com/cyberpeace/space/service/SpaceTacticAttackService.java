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
    ListRsp<AttackListBo> pageAttack(SpaceTacticPageReq req);

    void addAttack(AttackAddReq req);

    void updateAttack(AttackModifyReq req);

    void deleteBatch(List<String> ids);

    AttackDetail getDetail(String resourceId);

    ListRsp<TacticAttackCategoryBo> pageCategory(PageReqBase reqBase);

    void addAttackCategory(TacticAttackCategoryAddReq req);

    void updateAttackCategory(TacticAttackCategoryModifyReq req);

    void deleteCategory(List<String> ids);

    KnowledgeChart getTacticAttackChart();

    List<String> listByCategoryids(List<String> ids);
}
