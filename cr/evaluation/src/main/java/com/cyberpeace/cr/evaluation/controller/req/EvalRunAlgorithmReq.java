package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 评估跑算法请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class EvalRunAlgorithmReq implements Serializable {

    /**
     * 算法id
     */
    @ApiModelProperty("算法id")
    private String algorithmId;

    /**
     * 数据
     */
    @ApiModelProperty("数据")
    private Object data;

    /**
     * 列出数据
     */
    @ApiModelProperty("列表数据")
    private Object listData;

    /**
     * 其他参数
     */
    @ApiModelProperty("其他参数")
    private Object otherArgs;
}
