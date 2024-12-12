package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 评估记录列表响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel("评估列表数据")
public class EvalRecordListRsp implements Serializable {
    /**
     * 资源id
     */
    @ApiModelProperty("ID")
    private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 状态
     */
    @ApiModelProperty("评估状态")
    private Integer status;

    /**
     * 得分
     */
    @ApiModelProperty("评分结果")
    private Integer score;

    /**
     * 系统id
     */
    @ApiModelProperty("关联指标体系ID")
    private String systemId;

    /**
     * 系统名称
     */
    @ApiModelProperty("指标体系名称")
    private String systemName;

    /**
     * 任务id
     */
    @ApiModelProperty("关联测评任务ID")
    private String taskId;

    /**
     * 消息
     */
    @ApiModelProperty("错误信息")
    private String message;

    /**
     * 开始时间
     */
    @ApiModelProperty("评估检索探针开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("评估检索探针结束时间")
    private Date endTime;

    /**
     * 评估时间
     */
    @ApiModelProperty("评估开始时间")
    private Date evalTime;
}
