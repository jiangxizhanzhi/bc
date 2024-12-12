package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 评估房间双响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalRoomPairRsp implements Serializable {
    /**
     * id
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 标签
     */
    @ApiModelProperty("名称")
    private String label;
}
