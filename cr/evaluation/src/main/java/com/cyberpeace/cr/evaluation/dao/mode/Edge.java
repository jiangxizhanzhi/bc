package com.cyberpeace.cr.evaluation.dao.mode;

import lombok.Data;

// 定义边类
@Data
public class Edge {
    Node source;
    Node target;

    public Edge(Node source, Node target) {
        this.source = source;
        this.target = target;
    }
}