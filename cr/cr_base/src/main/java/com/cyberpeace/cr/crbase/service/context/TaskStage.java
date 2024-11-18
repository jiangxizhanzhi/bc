package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务阶段
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see CrTask
 * @since 1.0.0
 */
@Data
public class TaskStage extends CrTask {

    /**
     * 阶段
     *
     * @see String
     */
    private String stage;

    /**
     * 名称
     *
     * @see String
     */
    private String name;

    /**
     * 描述
     *
     * @see String
     */
    @ApiModelProperty(value = "任务描述")
    private String description;

    /**
     * 写入向上
     *
     * @see String
     */
    @ApiModelProperty(value = "题解")
    private String writeUp;

    /**
     * 显示 Writeup
     *
     * @see Boolean
     */
    @ApiModelProperty(value = "是否显示题解")
    private Boolean showWriteup;
}
