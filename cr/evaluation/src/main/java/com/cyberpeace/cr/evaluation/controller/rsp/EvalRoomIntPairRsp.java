package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估房间int双响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalRoomIntPairRsp {
    /**
     * id
     *
     * @see Integer
     */
    @ApiModelProperty("ID")
    private Integer id;

    /**
     * 标签
     *
     * @see String
     */
    @ApiModelProperty("名称")
    private String label;
}
