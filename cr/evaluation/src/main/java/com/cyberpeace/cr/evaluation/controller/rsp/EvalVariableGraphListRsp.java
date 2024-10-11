package com.cyberpeace.cr.evaluation.controller.rsp;

import lombok.Data;

/**
 * @Author xuxz
 * @Date 22/7/2022 下午6:29
 */
@Data
public class EvalVariableGraphListRsp extends EvalVariableGraphRsp{

    private String variableName;

    private String variableId;
}
