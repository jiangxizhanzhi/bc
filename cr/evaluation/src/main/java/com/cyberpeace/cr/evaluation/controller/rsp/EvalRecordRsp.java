package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.service.context.EvalSystemIndexesContext;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评估记录响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvalRecordRsp implements Serializable {
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
    @ApiModelProperty("名称")
    private String name;

    /**
     * 描述
     *
     * @see String
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 房间id
     *
     * @see String
     */
    @ApiModelProperty("靶场测评实例ID")
    private String roomId;

    /**
     * 系统id
     *
     * @see String
     */
    @ApiModelProperty("使用指标体系ID")
    private String systemId;

    /**
     * 开始时间
     *
     * @see Date
     */
    @ApiModelProperty("开始时间（评估采集数据时间范围）")
    private Date startTime;

    /**
     * 结束时间
     *
     * @see Date
     */
    @ApiModelProperty("结束时间（评估采集数据时间范围）")
    private Date endTime;

    /**
     * 评估时间
     *
     * @see Date
     */
    @ApiModelProperty("评估开始时间")
    private Date evalTime;

    /**
     * 跑状态
     *
     * @see Integer
     */
    @ApiModelProperty("启动状态 1-未评估 2-计算中 3-再次启动")
    private Integer runStatus;

    /**
     * 指标
     *
     * @see List<EvalSystemIndexesContext>
     */
    @ApiModelProperty("指标体系结构")
    private List<EvalSystemIndexesContext> indexes;
}
