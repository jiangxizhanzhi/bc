package com.cyberpeace.cr.redvsblue.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JSONField(name = "task_name")
    private String taskName;
    /**
     * 任务代号
     */
    @ApiModelProperty(value = "任务代号", name = "taskNo")
    @JSONField(name = "task_no")
    private String taskNo;
    /**
     * 任务目的
     */
    @ApiModelProperty(value = "任务目的", name = "taskPurpose")
    @JSONField(name = "task_purpose")
    private String taskPurpose;
    /**
     * 任务内容
     */
    @ApiModelProperty(value = "任务内容", name = "taskContent")
    @JSONField(name = "task_content")
    private String taskContent;
    /**
     * 任务责任人ID
     */
    @ApiModelProperty(value = "任务责任人ID", name = "taskUserId")
    @JSONField(name = "task_user_id")
    private String taskUserId;
    /**
     * 任务团队人员姓名
     */
    @ApiModelProperty(value = "任务责任人姓名", name = "taskUserName")
    @JSONField(name = "task_user_name")
    private String taskUserName;

    /**
     * 任务团队人员ID
     */
    @ApiModelProperty(value = "任务团队人员ID", name = "teamUserId")
    @JSONField(name = "team_user_id")
    private String teamUserId;
    /**
     * 任务团队人员姓名
     */
    @ApiModelProperty(value = "任务团队人员姓名", name = "teamUserName")
    @JSONField(name = "team_user_name")
    private String teamUserName;
    /**
     * 席位人员ID
     */
    @ApiModelProperty(value = "席位人员ID", name = "seatUserId")
    @JSONField(name = "seat_user_id")
    private String seatUserId;
    /**
     * 席位人员姓名
     */
    @ApiModelProperty(value = "席位人员姓名", name = "seatUserName")
    @JSONField(name = "seat_user_name")
    private String seatUserName;
    /**
     * 任务单位
     */
    @ApiModelProperty(value = "任务单位", name = "taskUnit")
    @JSONField(name = "task_unit")
    private String taskUnit;
    /**
     * 任务地域
     */
    @ApiModelProperty(value = "任务地域", name = "taskLocale")
    @JSONField(name = "task_locale")
    private String taskLocale;
    /**
     * 任务时间
     */
    @ApiModelProperty(value = "任务时间", name = "taskTime")
    @JSONField(name = "task_time")
    private Date taskTime;
    /**
     * 任务进度[1：筹备中、2：执行中、3：已完成]
     */
    @ApiModelProperty(value = "任务进度[1：筹备中、2：执行中、3：已完成]", name = "taskProgressState")
    @JSONField(name = "task_progress__state")
    private String taskProgressState;
    /**
     * 任务标识[1：已处理、0：未处理]
     */
    @ApiModelProperty(value = "任务标识[1：已处理、0：未处理]", name = "taskFlag")
    @JSONField(name = "task_flag")
    private String taskFlag;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remark")
    @JSONField(name = "remark")
    private String remark;

    /**
     * 附件名称
     */
    @JsonProperty("attachmentName")
    @JSONField(name = "attachment_name")
    private String attachmentName;

    /**
     * 附件流
     */
    @JsonProperty("attachmentStream")
    @JSONField(name = "attachment_stream")
    private byte[] attachmentStream;

}
