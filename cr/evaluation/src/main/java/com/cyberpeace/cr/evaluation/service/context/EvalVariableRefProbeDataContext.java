package com.cyberpeace.cr.evaluation.service.context;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * EVAL 变量 Ref 探针数据上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalVariableRefProbeDataContext implements Serializable {
    /**
     * 格式 化值
     *
     * @see String
     */
    private String formattedValue;

    /**
     * 状态
     *
     * @see Integer
     */
    private Integer status;

    /**
     * 时间
     *
     * @see Date
     */
    private Date time;
}
