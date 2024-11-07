package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * eval record 变量上下文
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class EvalRecordVariableContext implements Serializable {

    /**
     * 资源id
     */
    @ApiModelProperty(value = "变量项ID", required = true)
    private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty("变量项名称")
    private String name;

    /**
     * 装置id
     */
    @ApiModelProperty(value = "场景主机节点ID", required = true)
    private String deviceId;

    /**
     * 探针id
     */
    @ApiModelProperty(value = "选择主机可用探针ID", required = true)
    private String probeId;
}
