package com.cyberpeace.cr.evaluation.controller.rsp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 评估记录结果指数响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalRecordResultIndexRsp implements Serializable {
    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("ID")
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("指标/变量项名称")
    private String name;

    /**
     * 得分
     *
     * @see Integer
     */
    @ApiModelProperty("评估分值")
    private Integer score;

    /**
     * 算法id
     *
     * @see String
     */
    @ApiModelProperty("算法ID")
    private String algorithmId;

    /**
     * 算法名称
     *
     * @see String
     */
    @ApiModelProperty("算法名称")
    private String algorithmName;

    /**
     * 子级
     *
     * @see List<EvalRecordResultIndexRsp>
     */
    @ApiModelProperty("下一级指标/变量项信息")
    private List<EvalRecordResultIndexRsp> children;

    /**
     * 水平
     *
     * @see Integer
     */
    @JsonIgnore
    private Integer level;

    /**
     * 下一个id列表
     *
     * @see String
     */
    @JsonIgnore
    private String nextIds;

    /**
     * 父母id
     *
     * @see String
     */
    @JsonIgnore
    private String parentId;

    /**
     * 模式
     *
     * @see Integer
     */
    @ApiModelProperty("算法是否为层次分析法")
    private Integer mode;
}
