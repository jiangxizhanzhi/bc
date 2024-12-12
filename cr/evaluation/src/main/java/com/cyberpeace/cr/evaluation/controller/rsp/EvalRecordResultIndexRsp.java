package com.cyberpeace.cr.evaluation.controller.rsp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 评估记录结果指数响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalRecordResultIndexRsp implements Serializable {
    /**
     * 资源id
     */
    @ApiModelProperty("ID")
    private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty("指标/变量项名称")
    private String name;

    /**
     * 得分
     */
    @ApiModelProperty("评估分值")
    private Integer score;

    /**
     * 算法id
     */
    @ApiModelProperty("算法ID")
    private String algorithmId;

    /**
     * 算法名称
     */
    @ApiModelProperty("算法名称")
    private String algorithmName;

    /**
     * 子级
     */
    @ApiModelProperty("下一级指标/变量项信息")
    private List<EvalRecordResultIndexRsp> children;

    /**
     * 水平
     */
    @JsonIgnore
    private Integer level;

    /**
     * 下一个id列表
     */
    @JsonIgnore
    private String nextIds;

    /**
     * 父母id
     */
    @JsonIgnore
    private String parentId;

    /**
     * 模式
     */
    @ApiModelProperty("算法是否为层次分析法")
    private Integer mode;
}
