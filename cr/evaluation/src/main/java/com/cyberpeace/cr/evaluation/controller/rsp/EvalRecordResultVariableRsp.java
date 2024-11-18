package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 评估记录结果变量响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalRecordResultVariableRsp implements Serializable {
    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("ID")
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("变量项名称")
    private String name;

    /**
     * 格式算法
     *
     * @see EvalRecordResultAlgorithmRsp
     */
    @ApiModelProperty("格式化算法信息")
    private EvalRecordResultAlgorithmRsp formatAlgorithm;

    /**
     * 匹配算法
     *
     * @see EvalRecordResultAlgorithmRsp
     */
    @ApiModelProperty("匹配算法信息")
    private EvalRecordResultAlgorithmRsp matchAlgorithm;

    /**
     * 标记算法
     *
     * @see EvalRecordResultAlgorithmRsp
     */
    @ApiModelProperty("评分算法信息")
    private EvalRecordResultAlgorithmRsp markAlgorithm;
}
