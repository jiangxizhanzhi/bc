package com.cyberpeace.space.service.context.tactic;

import lombok.Data;

import java.util.List;

@Data
public class DefenseTmpAddReq extends DefenseAddReq{

    private List<String> nodes;

    private String rvbId;
}
