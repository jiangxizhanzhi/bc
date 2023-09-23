package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalVariableGraphRsp implements Serializable {
    @ApiModelProperty("时间节点")
    private List<Date> time;

    @ApiModelProperty("格式化算法生成值")
    private List<Integer> value;

    @ApiModelProperty("是否通过模型检测算法")
    private List<Boolean> isMatched;
}
