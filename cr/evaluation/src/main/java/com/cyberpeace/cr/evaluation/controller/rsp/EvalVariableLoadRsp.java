package com.cyberpeace.cr.evaluation.controller.rsp;

import lombok.Data;

@Data
public class EvalVariableLoadRsp {
    private String variableName;

//    载荷执行记录拼接
    private String loadExecutionName;

//    默认分值
    private Integer value;
}
