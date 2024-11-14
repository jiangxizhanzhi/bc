package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 测评变量裁判探针数据上下文
 *
 * @author SN
 * @date 2024/11/14
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
