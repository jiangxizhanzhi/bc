package com.cyberpeace.space.service.context.tactic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TacticDefenseCategoryModifyReq extends TacticDefenseCategoryAddReq{
    private String resourceId;
}
