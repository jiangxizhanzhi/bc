package com.cyberpeace.cr.redvsblue.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 网络攻防任务信息VO
 *
 * @author xiaomaiyun
 * @version 1.2.3
 * @date 2025/05/05 11:13:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "网络攻防任务信息VO")
public class NetworkTaskVO implements Serializable {

    private static final Long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id")
    private String id;
    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称", name = "taskName")
    private String taskName;
    /**
     * 任务代号
     */
    @ApiModelProperty(value = "任务代号", name = "taskNo")
    private String taskNo;
    /**
     * 任务目的
     */
    @ApiModelProperty(value = "任务目的", name = "taskPurpose")
    private String taskPurpose;
    /**
     * 任务内容
     */
    @ApiModelProperty(value = "任务内容", name = "taskContent")
    private String taskContent;
    /**
     * 任务责任人ID
     */
    @ApiModelProperty(value = "任务责任人ID", name = "taskUserId")
    private String taskUserId;
    /**
     * 任务团队人员姓名
     */
    @ApiModelProperty(value = "任务责任人姓名", name = "taskUserName")
    private String taskUserName;

    /**
     * 任务团队人员ID
     */
    @ApiModelProperty(value = "任务团队人员ID", name = "teamUserId")
    private String teamUserId;
    /**
     * 任务团队人员姓名
     */
    @ApiModelProperty(value = "任务团队人员姓名", name = "teamUserName")
    private String teamUserName;
    /**
     * 席位人员ID
     */
    @ApiModelProperty(value = "席位人员ID", name = "seatUserId")
    private String seatUserId;
    /**
     * 席位人员姓名
     */
    @ApiModelProperty(value = "席位人员姓名", name = "seatUserName")
    private String seatUserName;
    /**
     * 任务单位
     */
    @ApiModelProperty(value = "任务单位", name = "taskUnit")
    private String taskUnit;
    /**
     * 任务地域
     */
    @ApiModelProperty(value = "任务地域", name = "taskLocale")
    private String taskLocale;
    /**
     * 任务时间
     */
    @ApiModelProperty(value = "任务开始时间", name = "startDt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startDt;
    /**
     * 任务时间
     */
    @ApiModelProperty(value = "任务结束时间", name = "endDt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endDt;

    private String taskType;
    /**
     * 任务进度[1：筹备中、2：执行中、3：已完成]
     */
    @ApiModelProperty(value = "任务进度[1：筹备中、2：执行中、3：已完成]", name = "taskProgressState")
    private String taskProgressState;
    /**
     * 任务标识[1：已处理、0：未处理]
     */
    @ApiModelProperty(value = "任务标识[1：已处理、0：未处理]", name = "taskFlag")
    private String taskFlag;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remark")
    private String remark;

    /**
     * 附件名称
     */
    @JsonProperty("attachmentName")
    private String attachmentName;

    /**
     * 附件流
     */
    @JsonProperty("attachmentStream")
    private byte[] attachmentStream;

}
