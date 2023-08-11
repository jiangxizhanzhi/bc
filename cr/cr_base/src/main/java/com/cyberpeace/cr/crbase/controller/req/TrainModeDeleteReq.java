package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TrainModeDeleteReq implements Serializable {

    @ApiModelProperty("TrainModeReq列表")
   private List<TrainModeReq> trainModeReqList;

}
