package com.cyberpeace.cr.evaluation.controller.rsp;

import lombok.Data;

@Data
public class EvalNodeLogRsp {
    private String createUserId;

    private String updateTime;

    private String operate;

    private String content;
}
