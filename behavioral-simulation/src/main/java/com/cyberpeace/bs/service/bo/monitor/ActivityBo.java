package com.cyberpeace.bs.service.bo.monitor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

@Data
public class ActivityBo implements Serializable {

    @ApiModelProperty(value = "横轴小时")
    private List<String> xHour = Lists.newArrayList();

    @ApiModelProperty(value = "纵轴步骤数")
    private List<Integer> yStep = Lists.newArrayList();
}
