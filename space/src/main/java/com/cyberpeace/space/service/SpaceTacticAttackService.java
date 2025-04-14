package com.cyberpeace.space.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.space.service.context.tactic.*;

import java.util.List;

public interface SpaceTacticAttackService {
    ListRsp<AttackListBo> pageAttack(SpaceTacticPageReq req);

    void addAttack(AttackAddReq req);

    void updateAttack(AttackModifyReq req);

    void deleteBatch(List<String> ids);

    AttackDetail getDetail(String resourceId);

    ListRsp<TacticAttackCategoryBo> pageCategory(PageReqBase reqBase);

    void addAttackCategory(TacticAttackCategoryAddReq req);

    void updateAttackCategory(TacticAttackCategoryModifyReq req);

    void deleteCategory(List<String> ids);
}
