package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.model.DynamicStage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
public class DynamicSchemeRes implements Serializable {

    @ApiModelProperty("场景id")
    private String sceneId;

    @ApiModelProperty("动效方案名称")
    @NotBlank
    private String name;

    @ApiModelProperty("动效方案数据")
    private List<DynamicStageRes> schemeData;
}
