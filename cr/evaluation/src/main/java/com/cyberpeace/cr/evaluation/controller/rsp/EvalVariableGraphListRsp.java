package com.cyberpeace.cr.evaluation.controller.rsp;

import lombok.Data;

/**
 * 评估变量图列表响应
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class EvalVariableGraphListRsp extends EvalVariableGraphRsp{

    /**
     * 变量名称
     */
    private String variableName;

    /**
     * 变量id
     */
    private String variableId;
}
