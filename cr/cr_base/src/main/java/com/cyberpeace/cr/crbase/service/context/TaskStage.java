package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskStage extends CrTask {

    private String stage;

    private String name;

    @ApiModelProperty(value = "任务描述")
    private String description;

    @ApiModelProperty(value = "题解")
    private String writeUp;

    @ApiModelProperty(value = "是否显示题解")
    private Boolean showWriteup;
}
