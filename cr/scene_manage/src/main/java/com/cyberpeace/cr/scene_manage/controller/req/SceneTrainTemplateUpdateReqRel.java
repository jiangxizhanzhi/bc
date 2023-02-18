package com.cyberpeace.cr.scene_manage.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("场景管理模板更新")
public class SceneTrainTemplateUpdateReqRel extends SceneTrainTemplateReqRel implements Serializable {

    @ApiModelProperty("模板业务id")
    private String resourceId;

}
