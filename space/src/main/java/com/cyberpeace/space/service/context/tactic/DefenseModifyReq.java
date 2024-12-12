package com.cyberpeace.space.service.context.tactic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefenseModifyReq extends DefenseAddReq{
    private String resourceId;
}
