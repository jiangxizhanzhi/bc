package com.cyberpeace.cr.teamtrain.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamTrainTemplateAddReq extends TeamTrainTemplateReq {
    @ApiModelProperty("红蓝队训练, 1=红;2=蓝;3=日常")
    private Integer trainCategory;
}
