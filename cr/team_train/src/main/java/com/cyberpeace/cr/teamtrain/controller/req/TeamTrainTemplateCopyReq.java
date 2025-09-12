package com.cyberpeace.cr.teamtrain.controller.req;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TeamTrainTemplateCopyReq {

    @ApiModelProperty(value = "团队训练模板id", required = true)
    @NotNull
    private String resourceId;
}
