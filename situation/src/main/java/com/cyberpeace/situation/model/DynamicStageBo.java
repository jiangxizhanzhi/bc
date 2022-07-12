package com.cyberpeace.situation.model;

import com.cyberpeace.situation.controller.req.AttackMessageReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DynamicStageBo implements Serializable {

    @ApiModelProperty("阶段名称")
    private String name;

    @ApiModelProperty("动效id")
    private List<String> attackMessageIds;
}
