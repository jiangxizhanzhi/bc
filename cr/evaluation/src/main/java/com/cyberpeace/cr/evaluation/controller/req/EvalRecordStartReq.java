package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 评估记录开始请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class EvalRecordStartReq implements Serializable {
    /**
     * 资源id
     */
    @ApiModelProperty("评估id")
    private String resourceId;
}
