package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评估变量图响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalVariableGraphRsp implements Serializable {
    /**
     * 时间
     */
    @ApiModelProperty("时间节点")
    private List<Date> time;

    /**
     * 值
     */
    @ApiModelProperty("格式化算法生成值")
    private List<Integer> value;

    /**
     * 匹配
     */
    @ApiModelProperty("是否通过模型检测算法")
    private List<Boolean> isMatched;
}
