package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估记录结果响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see EvalRecordRsp
 * @since 1.0.0
 */
@Data
public class EvalRecordResultRsp extends EvalRecordRsp {
    /**
     * 得分
     *
     * @see Integer
     */
    @ApiModelProperty("评估得分")
    private Integer score;

    /**
     * 消息
     *
     * @see String
     */
    @ApiModelProperty("运行错误信息")
    private String message;
}
