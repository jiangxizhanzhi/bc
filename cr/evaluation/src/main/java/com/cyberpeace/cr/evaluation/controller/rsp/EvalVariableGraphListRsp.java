package com.cyberpeace.cr.evaluation.controller.rsp;

import lombok.Data;

/**
 * 评估变量图列表响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see EvalVariableGraphRsp
 * @since 1.0.0
 */
@Data
public class EvalVariableGraphListRsp extends EvalVariableGraphRsp{

    /**
     * 变量名称
     *
     * @see String
     */
    private String variableName;

    /**
     * 变量id
     *
     * @see String
     */
    private String variableId;
}
