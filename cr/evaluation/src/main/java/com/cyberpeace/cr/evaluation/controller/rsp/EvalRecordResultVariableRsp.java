package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 评估记录结果变量响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class EvalRecordResultVariableRsp implements Serializable {
    /**
     * 资源id
     */
    @ApiModelProperty("ID")
    private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty("变量项名称")
    private String name;

    /**
     * 格式算法
     */
    @ApiModelProperty("格式化算法信息")
    private EvalRecordResultAlgorithmRsp formatAlgorithm;

    /**
     * 匹配算法
     */
    @ApiModelProperty("匹配算法信息")
    private EvalRecordResultAlgorithmRsp matchAlgorithm;

    /**
     * 标记算法
     */
    @ApiModelProperty("评分算法信息")
    private EvalRecordResultAlgorithmRsp markAlgorithm;
}
