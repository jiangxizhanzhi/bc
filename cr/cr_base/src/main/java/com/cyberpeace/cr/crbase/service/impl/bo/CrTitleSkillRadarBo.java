package com.cyberpeace.cr.crbase.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CrTitleSkillRadarBo {
    @ApiModelProperty("维度方向名称")
    private List<String> label;

    @ApiModelProperty("分值")
    private List<Integer> value;
}
