package com.cyberpeace.cr.teamtrain.controller.req;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class DailyTrainRoomReq {

    @ApiModelProperty(value = "模板id", required = true)
    @NotNull
    private String template;

    @ApiModelProperty(value = "类别", required = true)
    @NotNull
    private Integer category;

    @ApiModelProperty(value = "名称", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "难度", required = true)
    @NotNull
    private Integer difficult;

    @ApiModelProperty(value = "开始时间", required = true)
    @NotNull
    private Date startTime;

    @ApiModelProperty(value = "结束时间", required = true)
    @NotNull
    private Date endTime;

    @ApiModelProperty(value = "描述", required = true)
    @NotNull
    private String description;

    @ApiModelProperty(value = "角色配置", required = true)
    @NotNull
    private List<RoleConfig> roleConfig;

    @ApiModelProperty(value = "录屏开关")
    private Boolean enableRecording;

    @ApiModelProperty(value = "允许部分启动,日常训练功能")
    private Boolean supportPartialStart;

    @ApiModelProperty(value = "LOGO")
    private String logo;

    public void validate() {
        CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
        CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
    }
}