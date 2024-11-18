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
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvalSystemIndexesContext implements Serializable {
    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("资源id")
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 得分
     *
     * @see Integer
     */
    @ApiModelProperty("得分")
    private Integer score;

    /**
     * 子级
     *
     * @see List<EvalSystemIndexesContext>
     */
    @ApiModelProperty("下一级")
    private List<EvalSystemIndexesContext> children;

    /**
     * 类型
     *
     * @see Integer
     */
    @ApiModelProperty("评分方式")
    private Integer type;

    /**
     * 装置id
     *
     * @see String
     */
    @ApiModelProperty("主机节点ID")
    private String deviceId;

    /**
     * 探针id
     *
     * @see String
     */
    @ApiModelProperty("探针ID")
    private String probeId;

    /**
     * 专家
     *
     * @see EvalRecordSpecialistContext
     */
    @ApiModelProperty("指标项专家评分配置")
    private EvalRecordSpecialistContext specialist;
}
