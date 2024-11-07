package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 训练任务释放信息上下文
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TrainTaskReleaseInfoContext {
    /**
     * 名称
     */
    @ApiModelProperty(value = "角色组,固定 red:红，blue:蓝")
    private String name;
    /**
     * 任务发布信息
     */
    @ApiModelProperty(value = "角色组中的角色任务列表")
    private List<TrainTaskReleaseInfoRsp>  taskReleaseInfos;

    public TrainTaskReleaseInfoContext(String name, List<TrainTaskReleaseInfoRsp> taskReleaseInfos) {
        this.name = name;
        this.taskReleaseInfos = taskReleaseInfos;
    }
}
