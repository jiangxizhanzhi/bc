package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务阶段
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class TaskStage extends CrTask {

    /**
     * 阶段
     */
    private String stage;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty(value = "任务描述")
    private String description;

    /**
     * 写入向上
     */
    @ApiModelProperty(value = "题解")
    private String writeUp;

    /**
     * 显示 Writeup
     */
    @ApiModelProperty(value = "是否显示题解")
    private Boolean showWriteup;
}
