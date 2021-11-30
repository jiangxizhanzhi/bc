package com.cyberpeace.cr.singletrain.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("单兵训练详情")
public class SingleRoomBo extends SingleTemplateBo implements Serializable {

    private String resourceId;

    @ApiModelProperty("用户使用训练")
    private SingleRoomInfoBo roomInfo;

    @ApiModelProperty("场景id")
    private String sceneManageId;

}
