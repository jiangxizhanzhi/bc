package com.cyberpeace.bs.service.bo.monitor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

@Data
public class TaskSessionBo implements Serializable {

    @ApiModelProperty(value = "横轴时间，间隔半小时")
    private List<String> xHalfHour = Lists.newArrayList();

    @ApiModelProperty(value = "纵轴会话数")
    private List<Integer> ySession = Lists.newArrayList();
}
