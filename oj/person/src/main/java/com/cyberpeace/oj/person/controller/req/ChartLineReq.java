package com.cyberpeace.oj.person.controller.req;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ChartLineReq implements Serializable {

    @ApiModelProperty("总理论题数")
    private List<String> date = new ArrayList<>();

    @ApiModelProperty("完成理论题数")
    private List<Integer> finishedTheories = new ArrayList<>();

    @ApiModelProperty("完成实操题数")
    private List<Integer> finishedOperates = new ArrayList<>();

}
