package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评估变量图响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalVariableGraphRsp implements Serializable {
    /**
     * 时间
     *
     * @see List<Date>
     */
    @ApiModelProperty("时间节点")
    private List<Date> time;

    /**
     * 值
     *
     * @see List<Integer>
     */
    @ApiModelProperty("格式化算法生成值")
    private List<Integer> value;

    /**
     * 匹配
     *
     * @see List<Boolean>
     */
    @ApiModelProperty("是否通过模型检测算法")
    private List<Boolean> isMatched;
}
