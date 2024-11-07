package com.cyberpeace.cr.singletrain.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 单房间训练提交请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class SingleRoomTrainSubmitReq implements Serializable {
    /**
     * 资源id
     */
    @ApiModelProperty(value = "单兵训练业务模板id", required = true)
    @NotBlank
    private String resourceId;
}
