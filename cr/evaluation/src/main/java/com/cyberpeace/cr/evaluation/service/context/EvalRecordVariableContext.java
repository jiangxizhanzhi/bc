package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测评记录变量上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalRecordVariableContext implements Serializable {

	/**
	 * 变量项ID
	 */
    @ApiModelProperty(value = "变量项ID", required = true)
    private String resourceId;

	/**
	 * 变量项名称
	 */
    @ApiModelProperty("变量项名称")
    private String name;

	/**
	 * 场景主机节点ID
	 */
    @ApiModelProperty(value = "场景主机节点ID", required = true)
    private String deviceId;

	/**
	 * 选择主机可用探针ID
	 */
    @ApiModelProperty(value = "选择主机可用探针ID", required = true)
    private String probeId;
}
