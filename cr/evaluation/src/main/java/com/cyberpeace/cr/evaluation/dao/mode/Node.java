package com.cyberpeace.cr.evaluation.dao.mode;

import lombok.Data;

// 定义节点类
@Data
public class Node {
    String id;
    int degree;

    public Node(String id) {
        this.id = id;
        this.degree = 0;
    }
}