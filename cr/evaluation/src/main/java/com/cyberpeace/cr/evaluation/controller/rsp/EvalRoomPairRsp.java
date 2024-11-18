package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 评估房间双响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalRoomPairRsp implements Serializable {
    /**
     * id
     *
     * @see String
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 标签
     *
     * @see String
     */
    @ApiModelProperty("名称")
    private String label;
}
