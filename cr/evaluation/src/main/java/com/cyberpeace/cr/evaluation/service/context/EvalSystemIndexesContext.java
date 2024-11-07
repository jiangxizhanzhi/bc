package com.cyberpeace.cr.evaluation.service.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * eval 系统索引上下文
 *
 * @author SN
 * @date 2024/11/07
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
     * 子级
     */
    @ApiModelProperty("下一级")
    private List<EvalSystemIndexesContext> children;

    /**
     * 类型
     */
    @ApiModelProperty("评分方式")
    private Integer type;

    /**
     * 装置id
     */
    @ApiModelProperty("主机节点ID")
    private String deviceId;

    /**
     * 探针id
     */
    @ApiModelProperty("探针ID")
    private String probeId;

    /**
     * 专家
     */
    @ApiModelProperty("指标项专家评分配置")
    private EvalRecordSpecialistContext specialist;
}
