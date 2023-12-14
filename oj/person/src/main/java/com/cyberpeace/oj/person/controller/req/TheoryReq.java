package com.cyberpeace.oj.person.controller.req;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TheoryReq implements Serializable {


    @ApiModelProperty("总理论题数")
    private Integer totalTheories;

    @ApiModelProperty("完成理论题数")
    private Integer finishedTheories;

    @ApiModelProperty("总实操题数")
    private Integer totalOperates;

    @ApiModelProperty("完成实操题数")
    private Integer finishedOperates;

}
