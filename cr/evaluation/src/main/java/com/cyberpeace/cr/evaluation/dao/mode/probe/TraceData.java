package com.cyberpeace.cr.evaluation.dao.mode.probe;

import lombok.Data;

import java.util.List;

@Data
public class TraceData {
    private String network;
    private String target;
    private List<Hop> hops;

    // 路由节点没有全timeout，才有处理的意义
    public boolean notAllTimeout() {
        return hops.stream().anyMatch(hop -> !hop.isTimeout());
    }
}
