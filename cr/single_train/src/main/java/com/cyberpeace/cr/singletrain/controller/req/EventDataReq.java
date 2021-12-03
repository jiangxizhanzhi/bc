package com.cyberpeace.cr.singletrain.controller.req;

import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("基础数据")
public class EventDataReq {
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "描述", required = true)
    @NotNull
    private String description;

    @ApiModelProperty(value = "训练类型", required = true)
    @NotNull
    private Integer category;

    @ApiModelProperty(value = "训练难度", required = true)
    @NotNull
    private Integer difficult;

    @ApiModelProperty(value = "建议时间, 单位:h", required = true)
    @NotNull
    private Integer durationTime;

    @ApiModelProperty(value = "前置技能", required = true)
    @NotNull
    private List<String> preSkill;

    @ApiModelProperty(value = "训练技能", required = true)
    @NotNull
    private List<String> trainSkill;

    @ApiModelProperty(value = "场景拓扑", required = true)
    @NotNull
    private SceneConfigData sceneConfigData;
}
