package com.cyberpeace.cs.service.context.scene;

import lombok.Data;

import java.util.Map;

@Data
public class CpflowImpl implements Cpflow {

    private Map<String, ElementFlowList> elements;

}
