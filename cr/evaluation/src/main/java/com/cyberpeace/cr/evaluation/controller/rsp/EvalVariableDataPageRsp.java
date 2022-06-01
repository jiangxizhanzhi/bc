package com.cyberpeace.cr.evaluation.controller.rsp;

import lombok.Data;

import java.io.Serializable;

@Data
public class EvalVariableDataPageRsp implements Serializable {
    private String nodeIp;

    private String collectionTime;

    private String content;
}
