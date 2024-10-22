package com.cyberpeace.space.service.context.tactic;

import lombok.Data;

@Data
public class TacticAttackCategoryModifyReq extends TacticAttackCategoryAddReq {
    private String resourceId;
}
