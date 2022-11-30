package com.cyberpeace.bs.service.bo.demo;

import com.cyberpeace.bs.service.bo.simulator.VariableBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

@Data
public class SingleBehaviorReq implements Serializable {

    @ApiModelProperty(value = "仪表id")
    private String simulatorId;

    @ApiModelProperty(value = "行为id")
    private String behaviorId;

    @ApiModelProperty(value = "行为变量")
    private List<VariableBo> variables = Lists.newArrayList();
}
