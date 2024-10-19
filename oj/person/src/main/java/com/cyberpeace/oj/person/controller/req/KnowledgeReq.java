package com.cyberpeace.oj.person.controller.req;

import com.cyberpeace.oj.person.service.bo.StatisticsBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class KnowledgeReq implements Serializable {

    @ApiModelProperty("课程")
    private StatisticsBo course;

    @ApiModelProperty("理论")
    private StatisticsBo theory;

    @ApiModelProperty("实操")
    private StatisticsBo operate;
}
