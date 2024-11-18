package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 评估记录开始请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalRecordStartReq implements Serializable {
    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("评估id")
    private String resourceId;
}
