package com.cyberpeace.cr.singletrain.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class SingleRoomTrainSubmitReq implements Serializable {
    @ApiModelProperty(value = "单兵训练业务模板id", required = true)
    @NotBlank
    private String resourceId;
}
