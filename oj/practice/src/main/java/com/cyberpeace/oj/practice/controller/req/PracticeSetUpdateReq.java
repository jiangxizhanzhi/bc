package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class PracticeSetUpdateReq extends PracticeSetAddReq {

    @ApiModelProperty(value = "资源编号", required = true)
    @NotBlank(groups = {Update.class})
    private String resourceId;
}
