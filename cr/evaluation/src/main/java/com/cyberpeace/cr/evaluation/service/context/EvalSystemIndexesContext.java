package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测评系统指标上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvalSystemIndexesContext implements Serializable {
	/**
	 * 资源id
	 */
    @ApiModelProperty("资源id")
    private String resourceId;

	/**
	 * 名称
	 */
    @ApiModelProperty("名称")
    private String name;

	/**
	 * 得分
	 */
    @ApiModelProperty("得分")
    private Integer score;

	/**
	 * 下一级
	 */
    @ApiModelProperty("下一级")
    private List<EvalSystemIndexesContext> children;

	/**
	 * 评分方式
	 */
    @ApiModelProperty("评分方式")
    private Integer type;

	/**
	 * 主机节点ID
	 */
    @ApiModelProperty("主机节点ID")
    private String deviceId;

	/**
	 * 探针ID
	 */
    @ApiModelProperty("探针ID")
    private String probeId;

	/**
	 * 指标项专家评分配置
	 */
    @ApiModelProperty("指标项专家评分配置")
    private EvalRecordSpecialistContext specialist;
}
