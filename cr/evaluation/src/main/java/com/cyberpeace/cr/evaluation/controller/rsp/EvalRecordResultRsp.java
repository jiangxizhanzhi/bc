package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估记录结果响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class EvalRecordResultRsp extends EvalRecordRsp {
    /**
     * 得分
     */
    @ApiModelProperty("评估得分")
    private Integer score;

    /**
     * 消息
     */
    @ApiModelProperty("运行错误信息")
    private String message;
}
