package com.cyberpeace.cr.singletrain.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单房间训练提交请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class SingleRoomTrainSubmitReq implements Serializable {
    /**
     * 单兵训练业务模板id
     */
    @ApiModelProperty(value = "单兵训练业务模板id", required = true)
    @NotBlank
    private String resourceId;
}
