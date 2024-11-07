package com.cyberpeace.cr.evaluation.service.context;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * EVAL 变量 Ref 探针数据上下文
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class EvalVariableRefProbeDataContext implements Serializable {
    /**
     * 格式 化值
     */
    private String formattedValue;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 时间
     */
    private Date time;
}
